package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.CompoundEvaluator.Tally;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * An expression whose children are ANDed together.
 *
 * @param name
 *         The name of the expression.
 * @param children
 *         The sub-expressions that are ANDed together. If no children are
 *         provided, the ANDed expression evaluates to true.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
public record AndExpression(
        String name,
        List<Expression> children
) implements CompoundExpression {

    static final String DEFAULT_NAME = "AND";
    static final String EXPECTED = "false = 0";

    /**
     * Creates a new AND expression.
     *
     * @param name
     *         The name of the expression.
     * @param children
     *         The sub-expressions to be ANDed together.
     *
     * @throws NullPointerException
     *         The supplied name or children is {@code null} or the children
     *         contains any {@code null}s.
     */
    public AndExpression {
        requireNonNull(name);
        children = List.copyOf(children);
    }

    /**
     * Creates an AND expression with a default name.
     *
     * @param children
     *         The sub-expressions to be ANDed together.
     *
     * @return An AND expression with a default name.
     *
     * @throws NullPointerException
     *         The supplied children list is {@code null} or the list contains
     *         any {@code null}s.
     */
    public static AndExpression unnamed(final List<Expression> children) {
        return new AndExpression(DEFAULT_NAME, children);
    }

    /**
     * Creates an AND expression with a default name.
     *
     * @param children
     *         The sub-expressions to be ANDed together.
     *
     * @return An AND expression with a default name.
     *
     * @throws NullPointerException
     *         The supplied children list is {@code null} or the list contains
     *         any {@code null}s.
     */
    public static AndExpression unnamed(final Expression... children) {
        return new AndExpression(DEFAULT_NAME, List.of(children));
    }

    @Override
    public CompoundEvaluation evaluate() {
        return CompoundEvaluator.evaluate(
                this, EXPECTED,
                Evaluation::isFalse,
                Tally::hasNoFalse
        );
    }
}
