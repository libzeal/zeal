package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.CompoundExpression;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;
import io.github.libzeal.zeal.logic.rationale.Rationale;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public final class CompoundEvaluation implements Evaluation {

    private final boolean result;
    private final String name;
    private final Rationale rationale;
    private final Duration elapsedTime;
    private final Cause cause;
    private final List<SkippableEvaluation> children;

    private CompoundEvaluation(final boolean result, final String name, final Rationale rationale,
                               final Duration elapsedTime, final Cause cause,
                               final List<SkippableEvaluation> children) {
        this.result = result;
        this.name = requireNonNull(name);
        this.rationale = requireNonNull(rationale);
        this.elapsedTime = requireNonNull(elapsedTime);
        this.cause = requireNonNull(cause);
        this.children = List.copyOf(children);
    }

    public static CompoundEvaluation of(final CompoundExpression expression, final boolean result,
                                        final Rationale rationale, final Duration elapsedTime, final Cause cause,
                                        final List<SkippableEvaluation> children) {

        final CompoundEvaluation evaluation = new CompoundEvaluation(result, expression.name(), rationale,
            elapsedTime, cause, children);

        evaluation.requireChildPerExpression(expression);
        evaluation.requireSkippedCausedBySiblings();

        return evaluation;
    }

    private void requireChildPerExpression(final CompoundExpression expression) {

        final int expected = expression.children().size();

        if (children.size() != expected) {
            throw new IllegalArgumentException("Expected " + expected + " children for expression '" + name +
                "' but found " + children.size());
        }
    }

    private void requireSkippedCausedBySiblings() {

        final List<Evaluation> evaluated = evaluated();

        for (final SkippableEvaluation child : children) {
            if (child instanceof final Skipped skipped) {
                for (final Evaluation cause : skipped.cause().evaluations()) {
                    if (evaluated.stream().noneMatch(sibling -> sibling == cause)) {
                        throw new IllegalArgumentException("Skipped child '" + skipped.name() +
                            "' must be caused by an evaluated sibling");
                    }
                }
            }
        }
    }

    @Override
    public boolean result() {
        return result;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Rationale rationale() {
        return rationale;
    }

    @Override
    public Duration elapsedTime() {
        return elapsedTime;
    }

    @Override
    public Cause cause() {
        return cause;
    }

    public List<SkippableEvaluation> children() {
        return children;
    }

    public List<Evaluation> evaluated() {
        return children.stream()
            .filter(Evaluated.class::isInstance)
            .map(Evaluated.class::cast)
            .map(Evaluated::evaluation)
            .toList();
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) return true;
        if (!(o instanceof final CompoundEvaluation that)) return false;

        return result == that.result &&
            name.equals(that.name) &&
            rationale.equals(that.rationale) &&
            elapsedTime.equals(that.elapsedTime) &&
            cause.equals(that.cause) &&
            children.equals(that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, name, rationale, elapsedTime, cause, children);
    }

    @Override
    public String toString() {
        return "CompoundEvaluation[" +
            "result=" + result +
            ", name=" + name +
            ", rationale=" + rationale +
            ", elapsedTime=" + elapsedTime +
            ", cause=" + cause +
            ", children=" + children +
            "]";
    }
}
