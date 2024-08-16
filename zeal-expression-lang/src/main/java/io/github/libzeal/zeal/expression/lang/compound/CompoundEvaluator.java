package io.github.libzeal.zeal.expression.lang.compound;

import io.github.libzeal.zeal.expression.lang.Expression;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

class CompoundEvaluator {

    private final Predicate<Tally> skipPredicate;

    private CompoundEvaluator(final Predicate<Tally> skipPredicate) {
        this.skipPredicate = requireNonNull(skipPredicate);
    }

    public static CompoundEvaluator skipAfter(final Predicate<Tally> predicate) {
        return new CompoundEvaluator(predicate);
    }

    public CompoundEvaluation evaluate(final List<Expression> expressions) {

        final int total = expressions.size();
        final List<Evaluation> evaluated = new ArrayList<>(total);
        final Tally tally = new Tally();

        for (Expression expression : expressions) {

            if (skipPredicate.test(tally)) {
                evaluated.add(expression.skip());
            }
            else {
                final Evaluation evaluation = expression.evaluate();
                final Result result = evaluation.result();

                switch (result) {
                    case TRUE:
                        tally.incrementPassed();
                        break;
                    case FALSE:
                        tally.incrementFailed();
                        break;
                    case SKIPPED:
                        tally.incrementSkipped();
                        break;
                }

                evaluated.add(evaluation);
            }
        }

        return new CompoundEvaluation(tally, evaluated);
    }

    public static final class CompoundEvaluation {

        private final Tally tally;
        private final List<Evaluation> evaluations;

        public CompoundEvaluation(final Tally tally, final List<Evaluation> evaluations) {
            this.tally = requireNonNull(tally);
            this.evaluations = requireNonNull(evaluations);
        }

        public Tally tally() {
            return tally;
        }

        public List<Evaluation> evaluations() {
            return evaluations;
        }
    }

    public static final class Tally {

        private int passed;
        private int failed;
        private int skipped;

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
            return passed + failed + skipped;
        }

        public boolean atLeastOnePassed() {
            return passed > 0;
        }

        public boolean atLeastOneFailed() {
            return failed > 0;
        }
    }
}
