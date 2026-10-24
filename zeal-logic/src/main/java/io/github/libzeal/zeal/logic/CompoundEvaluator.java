package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.*;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * A common evaluator used to evaluate {@link CompoundExpression}s.
 *
 * @author Justin Albano
 */
class CompoundEvaluator {

    private static final SkippingEvaluator SKIPPING_EVALUATOR = new SkippingEvaluator();
    private final String name;
    private final Result shortCircuitResult;
    private final Predicate<Tally> passCondition;
    private final CompoundRationaleBuilder rationaleBuilder;

    public CompoundEvaluator(final String name, final Result shortCircuitResult, final Predicate<Tally> passCondition,
                             final CompoundRationaleBuilder rationaleBuilder) {
        this.name = requireNonNull(name);
        this.shortCircuitResult = requireNonNull(shortCircuitResult);
        this.passCondition = requireNonNull(passCondition);
        this.rationaleBuilder = requireNonNull(rationaleBuilder);
    }

    public CompoundEvaluation evaluate(final List<Expression> expressions) {

        final StopWatch stopWatch = StopWatch.started();
        final int total = expressions.size();
        final List<Evaluation> evaluated = new ArrayList<>(total);
        final Tally tally = new Tally(total);
        Evaluation decider = null;

        for (final Expression expression : expressions) {

            final Evaluation evaluation = decider == null
                ? expression.evaluate()
                : SKIPPING_EVALUATOR.skip(expression, decider);

            tally.tally(evaluation.result());

            if (decider == null && evaluation.result() == shortCircuitResult) {
                decider = evaluation;
            }

            evaluated.add(evaluation);
        }

        final Cause cause = cause(decider, evaluated);
        final Duration elapsedTime = stopWatch.stop();

        return compoundEvaluation(tally, cause, evaluated, elapsedTime);
    }

    private static Cause cause(final Evaluation decider, final List<Evaluation> evaluated) {

        if (decider != null) {
            return Cause.of(decider);
        }
        else if (evaluated.isEmpty()) {
            return Cause.self();
        }
        else {
            return Cause.of(evaluated);
        }
    }

    private CompoundEvaluation compoundEvaluation(final Tally tally, final Cause cause,
                                                  final List<Evaluation> evaluated, final Duration elapsedTime) {

        final Rationale rationale = rationaleBuilder.build(tally);

        if (tally.total() == 0 || passCondition.test(tally)) {
            return new CompoundEvaluation(Result.TRUE, name, rationale, elapsedTime, cause, evaluated);
        }
        else if (tally.skipped() == tally.total()) {
            return new CompoundEvaluation(Result.SKIPPED, name, SimpleRationale.skipped(), Duration.ZERO, cause,
                evaluated);
        }
        else {
            return new CompoundEvaluation(Result.FALSE, name, rationale, elapsedTime, cause, evaluated);
        }
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
            return passed() > 0;
        }

        public boolean allPassed() {
            return passed() == total();
        }
    }

    static class CompoundRationaleBuilder {

        private static final String PASSED_PREFIX = "Passed: ";
        private static final String FAILED_PREFIX = "Failed: ";
        private static final String SKIPPED_PREFIX = "Skipped: ";
        private static final String SEPARATOR = ", ";
        private final String expected;

        private CompoundRationaleBuilder(final String expected) {
            this.expected = requireNonNull(expected);
        }

        public static CompoundRationaleBuilder withExpectedFailed(final int failed) {
            return new CompoundRationaleBuilder(formatFailed(failed));
        }

        static String formatFailed(final int failed) {
            return FAILED_PREFIX + failed;
        }

        public static CompoundRationaleBuilder withExpectedPassed(final int passed) {
            return new CompoundRationaleBuilder(formatPassed(passed));
        }

        static String formatPassed(final int passed) {
            return PASSED_PREFIX + passed;
        }

        public Rationale build(final CompoundEvaluator.Tally tally) {

            final String actual = format(tally.passed, tally.failed(), tally.skipped());

            return new SimpleRationale(expected, actual);
        }

        static String format(final int passed, final int failed, final int skipped) {
            return PASSED_PREFIX + passed +
                SEPARATOR +
                FAILED_PREFIX + failed +
                SEPARATOR +
                SKIPPED_PREFIX + skipped;
        }
    }
}
