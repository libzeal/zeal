package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.CompoundEvaluator.Tally;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * An expression whose children are ORed together.
 *
 * @param name
 *         The name of the expression.
 * @param children
 *         The sub-expressions that are ORed together. If no children are
 *         provided, the OR expression evaluates to false.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
public record OrExpression(
        String name,
        List<Expression> children
) implements CompoundExpression {

    static final String DEFAULT_NAME = "OR";
    static final String EXPECTED = "true >= 1";

    /**
     * Creates a new OR expression.
     *
     * @param name
     *         The name of the expression.
     * @param children
     *         The sub-expressions to be ORed together.
     *
     * @throws NullPointerException
     *         The supplied name or children is {@code null} or the children
     *         contains any {@code null}s.
     */
    public OrExpression {
        requireNonNull(name);
        children = List.copyOf(children);
    }

    /**
     * Creates a OR expression with a default name.
     *
     * @param children
     *         The sub-expressions to be ORed together.
     *
     * @return An OR expression with a default name.
     *
     * @throws NullPointerException
     *         The supplied children list is {@code null} or the list contains
     *         any {@code null}s.
     */
    public static OrExpression unnamed(final List<Expression> children) {
        return new OrExpression(DEFAULT_NAME, children);
    }

    /**
     * Creates a OR expression with a default name.
     *
     * @param children
     *         The sub-expressions to be ORed together.
     *
     * @return An OR expression with a default name.
     *
     * @throws NullPointerException
     *         The supplied children list is {@code null} or the list contains
     *         any {@code null}s.
     */
    public static OrExpression unnamed(final Expression... children) {
        return new OrExpression(DEFAULT_NAME, List.of(children));
    }

    @Override
    public CompoundEvaluation evaluate() {
        return CompoundEvaluator.evaluate(
                this,
                EXPECTED,
                Evaluation::isTrue,
                Tally::hasAtLeastOneTrue
        );
    }
}
