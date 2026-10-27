package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A compound expression where all sub-expression must be pass for the compound
 * expression to pass.
 *
 * @param name
 *     The name of the expression.
 * @param children
 *     The expressions used to initialize the compound expression.
 *
 * @author Justin Albano
 */
public record AndExpression(String name,
                            List<Expression> children) implements CompoundExpression {

    static final String DEFAULT_NAME = "AND";

    /**
     * Creates a new conjunctive expression.
     *
     * @param name
     *     The name of the expression.
     * @param children
     *     The expressions used to initialize the compound expression.
     *
     * @throws NullPointerException
     *     The supplied name is {@code null} or the children contains a
     *     {@code null} value.
     */
    public AndExpression {
        requireNonNull(name);
        children = List.copyOf(children);
    }

    /**
     * Creates a new conjunctive expression using a default name.
     *
     * @param children
     *     The expressions used to initialize the compound expression.
     *
     * @return A conjunctive expression with a default name.
     *
     * @throws NullPointerException
     *     The supplied name is {@code null} or the children contains a
     *     {@code null} value.
     */
    public static AndExpression unnamed(final List<Expression> children) {
        return new AndExpression(DEFAULT_NAME, children);
    }

    /**
     * Creates a new conjunctive expression using a default name.
     *
     * @param children
     *     The expressions used to initialize the compound expression.
     *
     * @return A conjunctive expression with a default name.
     *
     * @throws NullPointerException
     *     The supplied name is {@code null} or the children contains a
     *     {@code null} value.
     */
    public static AndExpression unnamed(final Expression... children) {
        return new AndExpression(DEFAULT_NAME, List.of(children));
    }

    @Override
    public CompoundEvaluation evaluate() {
        return CompoundEvaluator.AND.evaluate(this);
    }
}
