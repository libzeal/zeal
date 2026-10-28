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

/**
 * A common evaluator used to evaluate {@link CompoundExpression}s.
 *
 * @author Justin Albano
 */
enum CompoundEvaluator {

    AND(false, false),
    OR(true, true),
    NAND(false, true),
    NOR(true, false);

    private static final String PASSED_PREFIX = "Passed: ";
    private static final String FAILED_PREFIX = "Failed: ";
    private static final String SKIPPED_PREFIX = "Skipped: ";
    private static final String SEPARATOR = ", ";
    static final String EXPECTED_ODD_PASSED = PASSED_PREFIX + "odd";
    private final boolean shortCircuitResult;
    private final boolean decidedResult;

    CompoundEvaluator(final boolean shortCircuitResult, final boolean decidedResult) {
        this.shortCircuitResult = shortCircuitResult;
        this.decidedResult = decidedResult;
    }

    CompoundEvaluation evaluate(final CompoundExpression expression) {

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

            if (evaluation.result() == shortCircuitResult) {
                decidedBy = Cause.of(evaluation);
            }
        }

        final Duration elapsedTime = stopWatch.stop();
        final boolean result = (decidedBy != null) == decidedResult;
        final Tally tally = Tally.of(children);
        final Rationale rationale = new SimpleRationale(
            expected(children.size()),
            format(tally)
        );

        return CompoundEvaluation.of(expression, result, rationale, elapsedTime, cause(decidedBy, evaluated),
            children);
    }

    private String expected(final int total) {
        return switch (this) {
            case AND -> formatPassed(total);
            case OR -> formatPassed(1);
            case NAND -> formatFailed(1);
            case NOR -> formatFailed(total);
        };
    }

    static CompoundEvaluation evaluateXor(final CompoundExpression expression) {

        final StopWatch stopWatch = StopWatch.started();
        final List<SkippableEvaluation> children = new ArrayList<>();
        final List<Evaluation> evaluated = new ArrayList<>();

        for (final Expression child : expression.children()) {

            final Evaluation evaluation = child.evaluate();
            children.add(new Evaluated(evaluation));
            evaluated.add(evaluation);
        }

        final Duration elapsedTime = stopWatch.stop();
        final Tally tally = Tally.of(children);
        final boolean result = tally.passed() % 2 == 1;
        final Rationale rationale = new SimpleRationale(EXPECTED_ODD_PASSED, format(tally));

        return CompoundEvaluation.of(expression, result, rationale, elapsedTime, cause(null, evaluated), children);
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

    static String formatPassed(final int passed) {
        return PASSED_PREFIX + passed;
    }

    static String formatFailed(final int failed) {
        return FAILED_PREFIX + failed;
    }

    private static String format(final Tally tally) {
        return format(tally.passed(), tally.failed(), tally.skipped());
    }

    static String format(final int passed, final int failed, final int skipped) {

        final String formattedPassed = PASSED_PREFIX + passed;
        final String formattedFailed = FAILED_PREFIX + failed;
        final String formattedSkipped = SKIPPED_PREFIX + skipped;

        return String.join(SEPARATOR, formattedPassed, formattedFailed, formattedSkipped);
    }

    private record Tally(int passed, int failed, int skipped) {

        static Tally of(final List<SkippableEvaluation> children) {

            int passed = 0;
            int failed = 0;
            int skipped = 0;

            for (final SkippableEvaluation child : children) {
                switch (child) {
                    case Evaluated(Evaluation evaluation) when evaluation.result() -> passed++;
                    case Evaluated ignored -> failed++;
                    case Skipped ignored -> skipped++;
                }
            }

            return new Tally(passed, failed, skipped);
        }
    }
}
