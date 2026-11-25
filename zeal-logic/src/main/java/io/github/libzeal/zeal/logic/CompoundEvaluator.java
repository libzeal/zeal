package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.*;
import io.github.libzeal.zeal.logic.evaluation.DirectCause.Delegated;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;
import io.github.libzeal.zeal.logic.util.StopWatch;
import org.jspecify.annotations.Nullable;

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

    private static final String PASSED_SUFFIX = " passed";
    private static final String FAILED_SUFFIX = " failed";
    private static final String SKIPPED_SUFFIX = " skipped";
    private static final String SEPARATOR = ", ";

    private CompoundEvaluator() {
    }

    static CompoundEvaluation evaluate(
            final CompoundExpression expression,
            final String expected,
            final String negatedExpected,
            final Predicate<Evaluation> stopWhen,
            final Predicate<Tally> isTrue
    ) {

        final StopWatch stopWatch = StopWatch.started();
        final List<SkippableEvaluation> children = new ArrayList<>();
        final List<Evaluation> evaluated = new ArrayList<>();
        Delegated decidingChild = null;

        for (final Expression child : expression.children()) {

            if (decidingChild != null) {
                children.add(new Skipped(child, decidingChild));
                continue;
            }

            final Evaluation evaluation = child.evaluate();
            children.add(new Evaluated(evaluation));
            evaluated.add(evaluation);

            if (stopWhen.test(evaluation)) {
                decidingChild = DirectCause.of(evaluation);
            }
        }

        final Duration elapsedTime = stopWatch.stop();
        final Tally tally = Tally.of(children);
        final Rationale rationale = new SimpleRationale(
                expected,
                negatedExpected,
                formatActual(tally),
                null,
                null
        );
        final DirectCauseFinder directCauseFinder = new DirectCauseFinder(decidingChild, evaluated);

        return CompoundEvaluation.of(
                expression,
                isTrue.test(tally),
                rationale,
                elapsedTime,
                directCauseFinder.find(),
                children
        );
    }

    static String formatActual(final Tally tally) {

        final String actual = tally.trueCount() + PASSED_SUFFIX + SEPARATOR + tally.falseCount() + FAILED_SUFFIX;

        if (tally.skippedCount() > 0) {
            return actual + SEPARATOR + tally.skippedCount() + SKIPPED_SUFFIX;
        }

        return actual;
    }

    private record DirectCauseFinder(
            @Nullable Delegated decidingChild,
            List<Evaluation> evaluated
    ) {

        /**
         * Finds the direct cause of a compound evaluation. The direct cause is
         * found using the following decision tree:
         * <ol>
         *     <li>If a single child ended the evaluation early, that child is
         *     the direct cause.</li>
         *     <li>If a compound evaluation contains only one child, that child
         *     is implicitly the direct cause.</li>
         *     <li>Otherwise, the compound evaluation itself is the direct
         *     cause.</li>
         * </ol>
         *
         * @return The direct cause of the compound evaluation.
         */
        DirectCause find() {

            if (decidingChild != null) {
                // A non-null child ended the evaluation early (such as the
                // first false child in an AND evaluation). That child alone
                // decided the result of the evaluation.
                return decidingChild;
            }
            else if (evaluated.size() == 1) {
                // The compound evaluation only has one child, so that child
                // decided the result.
                return DirectCause.of(evaluated);
            }
            else {
                // The children of the compound evaluation decided the
                // evaluation together (such as all the false children of an OR
                // evaluation) or the compound evaluation has no children.
                // Therefore, the compound evaluation itself is the direct cause
                // of its result.
                return DirectCause.self();
            }
        }

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
