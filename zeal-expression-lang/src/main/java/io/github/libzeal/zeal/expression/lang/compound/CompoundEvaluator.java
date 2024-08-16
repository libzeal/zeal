package io.github.libzeal.zeal.expression.lang.compound;

import io.github.libzeal.zeal.expression.lang.Expression;
import io.github.libzeal.zeal.expression.lang.evaluation.*;
import io.github.libzeal.zeal.expression.lang.rationale.Rationale;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

class CompoundEvaluator {

    private final Predicate<Tally> passCondition;
    private final Predicate<Tally> failCondition;
    private final RationaleBuilder rationaleBuilder;

    public CompoundEvaluator(final Predicate<Tally> passCondition, final Predicate<Tally> failCondition,
                             final RationaleBuilder rationaleBuilder) {
        this.passCondition = requireNonNull(passCondition);
        this.failCondition = requireNonNull(failCondition);
        this.rationaleBuilder = requireNonNull(rationaleBuilder);
    }

    public Evaluation evaluate(final String name, final List<Expression> expressions) {

        final int total = expressions.size();
        final List<Evaluation> evaluated = new ArrayList<>(total);
        final Tally tally = new Tally(total);
        State state = State.UNKNOWN;
        RootCause rootCause = null;

        for (Expression expression : expressions) {

            if (!state.equals(State.UNKNOWN)) {
                evaluated.add(expression.skip());
            }
            else {
                final Evaluation evaluation = expression.evaluate();
                final Result result = evaluation.result();

                tally.tally(result);

                if (failCondition.test(tally)) {
                    state = State.FAILED;
                    rootCause = new RootCause(evaluation);
                }
                else if (passCondition.test(tally)) {
                    state = State.PASSED;
                }

                evaluated.add(evaluation);
            }
        }

        final Rationale rationale = rationaleBuilder.build(tally);

        if (tally.total() == 0 || state.equals(State.PASSED)) {
            return new CompoundTrueEvaluation(name, rationale, evaluated);
        }
        else if (tally.skipped() == tally.total()) {
            return new CompoundSkippedEvaluation(name, evaluated);
        }
        else {
            if (rootCause != null) {
                return new CompoundFalseEvaluation(name, rationale, rootCause, evaluated);
            }
            else {
                return CompoundFalseEvaluation.selfRootCause(name, rationale, evaluated);
            }
        }
    }

    private enum State {
        PASSED,
        FAILED,
        UNKNOWN;
    }

    public static final class Tally {

        private final int total;
        private int passed;
        private int failed;
        private int skipped;

        public Tally(final int total) {
            this.total = total;
        }

        public void tally(final Result result) {

            switch (result) {
                case TRUE:
                    incrementPassed();
                    break;
                case FALSE:
                    incrementFailed();
                    break;
                case SKIPPED:
                    incrementSkipped();
                    break;
            }
        }

        public void incrementPassed() {
            passed++;
        }

        public void incrementFailed() {
            failed++;
        }

        public void incrementSkipped() {
            skipped++;
        }

        public int passed() {
            return passed;
        }

        public int failed() {
            return failed;
        }

        public int skipped() {
            return skipped;
        }

        public int total() {
            return total;
        }

        public boolean anyPassed() {
            return passed > 0;
        }

        public boolean anyFailed() {
            return failed > 0;
        }

        public boolean allFailed() {
            return failed == total;
        }

        public boolean allPassed() {
            return passed == total;
        }
    }
}
