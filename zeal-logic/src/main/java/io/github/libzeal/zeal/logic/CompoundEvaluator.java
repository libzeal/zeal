package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * A common evaluator used to evaluate {@link CompoundExpression}s.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
final class CompoundEvaluator {

    private static final String TRUE_PREFIX = "True: ";
    private static final String FALSE_PREFIX = "False: ";
    private static final String SKIPPED_PREFIX = "Skipped: ";
    private static final String SEPARATOR = ", ";

    private CompoundEvaluator() {
    }

    static CompoundEvaluation evaluate(
            final CompoundExpression expression, final String expected,
            final Predicate<Evaluation> stopWhen, final Predicate<Tally> isTrue
    ) {

        final StopWatch stopWatch = StopWatch.started();
        final List<SkippableEvaluation> children = new ArrayList<>();
        final List<Evaluation> evaluated = new ArrayList<>();
        Cause.Delegated decidedBy = null;

        for (final Expression child : expression.children()) {

            if (decidedBy != null) {
                children.add(new Skipped(child, decidedBy));
                continue;
            }

            final Evaluation evaluation = child.evaluate();
            children.add(new Evaluated(evaluation));
            evaluated.add(evaluation);

            if (stopWhen.test(evaluation)) {
                decidedBy = Cause.of(evaluation);
            }
        }

        final Duration elapsedTime = stopWatch.stop();
        final Tally tally = Tally.of(children);
        final Rationale rationale = new SimpleRationale(expected, format(tally));

        return CompoundEvaluation.of(
                expression, isTrue.test(tally), rationale, elapsedTime,
                cause(decidedBy, evaluated), children
        );
    }

    private static Cause cause(final Cause.Delegated decidedBy, final List<Evaluation> evaluated) {

        if (decidedBy != null) {
            return decidedBy;
        }
        else if (evaluated.isEmpty()) {
            return Cause.self();
        }
        else {
            return Cause.of(evaluated);
        }
    }

    private static String format(final Tally tally) {
        return format(tally.trueCount(), tally.falseCount(), tally.skippedCount());
    }

    static String format(final int trueCount, final int falseCount, final int skippedCount) {

        final String formattedTrue = TRUE_PREFIX + trueCount;
        final String formattedFalse = FALSE_PREFIX + falseCount;
        final String formattedSkipped = SKIPPED_PREFIX + skippedCount;

        return String.join(SEPARATOR, formattedTrue, formattedFalse, formattedSkipped);
    }

    record Tally(int trueCount, int falseCount, int skippedCount) {

        public static Tally of(final List<SkippableEvaluation> children) {

            int trueCount = 0;
            int falseCount = 0;
            int skippedCount = 0;

            for (final SkippableEvaluation child : children) {
                switch (child) {
                    case Evaluated(Evaluation evaluation)
                            when evaluation.isTrue() -> trueCount++;
                    case Evaluated ignored -> falseCount++;
                    case Skipped ignored -> skippedCount++;
                }
            }

            return new Tally(trueCount, falseCount, skippedCount);
        }

        public boolean hasAtLeastOneTrue() {
            return trueCount > 0;
        }

        public boolean hasAtLeastOneFalse() {
            return falseCount > 0;
        }

        public boolean hasNoTrue() {
            return trueCount == 0;
        }

        public boolean hasNoFalse() {
            return falseCount == 0;
        }
    }
}
