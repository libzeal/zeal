package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.CompoundExpression;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;
import io.github.libzeal.zeal.logic.rationale.Rationale;

import java.time.Duration;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * An evaluation that results from evaluating a {@link CompoundExpression}. This
 * evaluation contains child evaluations that make up the evaluation.
 *
 * @param isTrue
 *         The result (true or false) of the evaluation.
 * @param name
 *         The name of the evaluation (often the name of the original
 *         expression).
 * @param rationale
 *         The rationale of the evaluation.
 * @param duration
 *         The duration that the evaluation took to complete.
 * @param directCause
 *         The direct cause of the result of the evaluation.
 * @param children
 *         The sub-evaluations that this evaluation is composed of.
 */
public record CompoundEvaluation(
        boolean isTrue,
        String name,
        Rationale rationale,
        Duration duration,
        DirectCause directCause,
        List<SkippableEvaluation> children
) implements Evaluation {

    /**
     * Creates a new compound evaluation.
     *
     * @param isTrue
     *         The result (true or false) of the evaluation.
     * @param name
     *         The name of the evaluation (often the name of the original
     *         expression).
     * @param rationale
     *         The rationale of the evaluation.
     * @param duration
     *         The duration that the evaluation took to complete.
     * @param directCause
     *         The direct cause of the result of the evaluation.
     * @param children
     *         The sub-evaluations that this evaluation is composed of.
     *
     * @throws NullPointerException
     *         Any of the supplied arguments are {@code null} or any of the
     *         children in the supplied list of children are {@code null}.
     * @throws IllegalArgumentException
     *         Any of the supplied children are skipped evaluations
     *         ({@link SkippableEvaluation}) but do not point to a sibling
     *         evaluation (another evaluation in the supplied children) as a
     *         direct cause.
     */
    public CompoundEvaluation {
        requireNonNull(name);
        requireNonNull(rationale);
        requireNonNull(duration);
        requireNonNull(directCause);
        children = List.copyOf(children);

        requireSkippedCausedBySiblings(children);
    }

    private static void requireSkippedCausedBySiblings(
            final List<SkippableEvaluation> children
    ) {

        final List<Evaluation> evaluated = filterEvaluated(children);

        for (final SkippableEvaluation child : children) {
            if (child instanceof final Skipped skipped) {
                for (final Evaluation cause : skipped.directCause().evaluations()) {
                    if (evaluated.stream().noneMatch(sibling -> sibling == cause)) {
                        throw new IllegalArgumentException("Skipped child '" + skipped.name() +
                                "' must be caused by an evaluated sibling");
                    }
                }
            }
        }
    }

    /**
     * Creates a new compound evaluation based on the supplied expression.
     *
     * @param expression
     *         The expression that was evaluated.
     * @param isTrue
     *         The result (true or false) of the evaluation.
     * @param rationale
     *         The rationale of the evaluation.
     * @param duration
     *         The duration that the evaluation took to complete.
     * @param directCause
     *         The direct cause of the result of the evaluation.
     * @param children
     *         The sub-evaluations that this evaluation is composed of.
     *
     * @return A compound evaluation based on the supplied expression.
     *
     * @throws NullPointerException
     *         Any of the supplied arguments are {@code null} or any of the
     *         children in the supplied list of children are {@code null}.
     * @throws IllegalArgumentException
     *         Any of the supplied children are skipped evaluations
     *         ({@link SkippableEvaluation}) but do not point to a sibling
     *         evaluation (another evaluation in the supplied children) as a
     *         direct cause; or if the number of supplied children does not
     *         match the number of children in the supplied expression.
     */
    public static CompoundEvaluation of(
            final CompoundExpression expression,
            final boolean isTrue,
            final Rationale rationale,
            final Duration duration,
            final DirectCause directCause,
            final List<SkippableEvaluation> children
    ) {
        requireChildPerExpression(expression, children);

        return new CompoundEvaluation(
                isTrue,
                expression.name(),
                rationale,
                duration,
                directCause,
                children
        );
    }

    private static void requireChildPerExpression(
            final CompoundExpression expression,
            final List<SkippableEvaluation> children
    ) {

        final int expected = expression.children().size();

        if (children.size() != expected) {
            throw new IllegalArgumentException("Expected " + expected
                    + " children from supplied expression"
                    + " but found " + children.size());
        }
    }

    private static List<Evaluation> filterEvaluated(final List<SkippableEvaluation> children) {
        return children.stream()
                .filter(Evaluated.class::isInstance)
                .map(Evaluated.class::cast)
                .map(Evaluated::evaluation)
                .toList();
    }

    @Override
    public Cause cause() {
        return new Cause(this, directCause);
    }

    /**
     * Obtains the unskipped child evaluations. This list presents the child
     * evaluation list with all skipped evaluations removed.
     *
     * @return The unskipped child evaluations.
     */
    public List<Evaluation> evaluated() {
        return filterEvaluated(children);
    }
}
