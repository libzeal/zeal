package io.github.libzeal.zeal.logic.compound;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * A common evaluator used to evaluated {@link CompoundExpression}.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
class CompoundEvaluator {

    private final String name;
    private final Predicate<Tally> passCondition;
    private final Predicate<Tally> failCondition;
    private final CompoundRationaleBuilder rationaleBuilder;

    public CompoundEvaluator(final String name, final Predicate<Tally> passCondition, final Predicate<Tally> failCondition,
                             final CompoundRationaleBuilder rationaleBuilder) {
        this.name = requireNonNull(name);
        this.passCondition = requireNonNull(passCondition);
        this.failCondition = requireNonNull(failCondition);
        this.rationaleBuilder = requireNonNull(rationaleBuilder);
    }

    public Evaluation evaluate(final List<Expression> expressions) {

        final StopWatch stopWatch = StopWatch.started();
        final int total = expressions.size();
        final List<Evaluation> evaluated = new ArrayList<>(total);
        final Tally tally = new Tally(total);
        Evaluation decider = null;

        for (Expression expression : expressions) {

            final Evaluation evaluation = evaluate(expression, tally, decider);

            tally.tally(evaluation.result());

            if (passes(tally) || fails(tally)) {
                decider = evaluation;
            }

            evaluated.add(evaluation);
        }

        final CauseGenerator cause = findCause(decider);
        final Duration elapsedTime = stopWatch.stop();

        return compoundEvaluation(tally, cause, evaluated, elapsedTime);
    }

    private boolean fails(final Tally tally) {
        return failCondition.test(tally);
    }

    private boolean passes(final Tally tally) {
        return passCondition.test(tally) && tally.tallySoFar() != tally.total();
    }

    private Evaluation evaluate(final Expression expression, final Tally tally, final Evaluation decider) {

        if (passCondition.test(tally) || fails(tally)) {
            return expression.skip(new Cause(decider));
        }
        else {
            return expression.evaluate();
        }
    }

    private static CauseGenerator findCause(final Evaluation decider) {

        if (decider == null) {
            return CauseGenerator.self();
        }
        else {
            return CauseGenerator.withUnderlyingCause(decider.cause());
        }
    }

    private CompoundEvaluation compoundEvaluation(final Tally tally, final CauseGenerator causeGenerator,
                                                  final List<Evaluation> evaluated, final Duration elapsedTime) {

        final Rationale rationale = rationaleBuilder.build(tally);

        if (tally.total() == 0 || passCondition.test(tally)) {
            return CompoundEvaluation.ofTrue(name, rationale, elapsedTime, causeGenerator, evaluated);
        }
        else if (tally.skipped() == tally.total()) {
            return CompoundEvaluation.ofSkipped(name, elapsedTime, causeGenerator, evaluated);
        }
        else {
            return CompoundEvaluation.ofFalse(name, rationale, elapsedTime, causeGenerator, evaluated);
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

        public int tallySoFar() {
            return passed() + failed() + skipped();
        }

        public boolean anyPassed() {
            return passed() > 0;
        }

        public boolean anyFailed() {
            return failed() > 0;
        }

        public boolean allFailed() {
            return failed() == total();
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
