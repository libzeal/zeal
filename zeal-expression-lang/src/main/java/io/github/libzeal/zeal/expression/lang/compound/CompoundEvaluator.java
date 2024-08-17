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
        Evaluation decider = null;

        for (Expression expression : expressions) {

            final Evaluation evaluation = evaluate(expression, state, decider);

            tally.tally(evaluation.result());

            if (state.equals(State.UNKNOWN)) {

                if (failCondition.test(tally)) {
                    state = State.FAILED;
                    decider = evaluation;
                }
                else if (passCondition.test(tally)) {

                    state = State.PASSED;

                    if (tally.tallySoFar() != tally.total()) {
                        decider = evaluation;
                    }
                }
            }

            evaluated.add(evaluation);
        }

        final Cause cause = findRootCause(decider);
        final Rationale rationale = rationaleBuilder.build(tally);

        if (tally.total() == 0 || state.equals(State.PASSED)) {
            return CompoundEvaluation.ofTrue(name, rationale, cause, evaluated);
        }
        else if (tally.skipped() == tally.total()) {
            return CompoundEvaluation.ofSkipped(name, cause, evaluated);
        }
        else {
            return CompoundEvaluation.ofFalse(name, rationale, cause, evaluated);
        }
    }

    private static Evaluation evaluate(final Expression expression, final State state, final Evaluation decider) {

        if (!state.equals(State.UNKNOWN)) {
            return expression.skip(new Cause(decider));
        }
        else {
            return expression.evaluate();
        }
    }

    private static Cause findRootCause(final Evaluation decider) {

        if (decider == null) {
            return null;
        }
        else {
            final Cause cause = decider.rootCause();

            if (cause == null) {
                return new Cause(decider);
            }
            else {
                return cause;
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

        public int tallySoFar() {
            return passed + failed + skipped;
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
