package io.github.libzeal.zeal.logic;

import java.util.Arrays;

/**
 * A set of logical operations that can be performed on one or more expressions.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public final class Operations {

    private Operations() {
    }

    /**
     * Creates a new expression that is the logical not of the supplied expression.
     *
     * @param expression
     *     The expression to negate.
     *
     * @return The logical negation of the supplied expression.
     *
     * @throws NullPointerException
     *     The supplied expression is {@code null}.
     */
    public static NegatedExpression not(final Expression expression) {
        return new NegatedExpression(expression);
    }

    /**
     * Creates a new expression that is the logical and of the supplied expressions.
     *
     * @param expressions
     *     The expressions to and.
     *
     * @return The logical and of the supplied expressions.
     *
     * @throws NullPointerException
     *     Any of the supplied expressions is {@code null}.
     */
    public static ConjunctiveExpression and(final Expression... expressions) {
        return ConjunctiveExpression.withDefaultName(Arrays.asList(expressions));
    }

    /**
     * Creates a new expression that is the logical or of the supplied expressions.
     *
     * @param expressions
     *     The expressions to or.
     *
     * @return The logical or of the supplied expressions.
     *
     * @throws NullPointerException
     *     Any of the supplied expressions is {@code null}.
     */
    public static DisjunctiveExpression or(final Expression... expressions) {
        return DisjunctiveExpression.withDefaultName(Arrays.asList(expressions));
    }

    /**
     * Creates a new expression that is the logical nand of the supplied expressions.
     *
     * @param expressions
     *     The expressions to nand.
     *
     * @return The logical nand of the supplied expressions.
     *
     * @throws NullPointerException
     *     Any of the supplied expressions is {@code null}.
     */
    public static NonConjunctiveExpression nand(final Expression... expressions) {
        return NonConjunctiveExpression.withDefaultName(Arrays.asList(expressions));
    }

    /**
     * Creates a new expression that is the logical nor of the supplied expressions.
     *
     * @param expressions
     *     The expressions to nor.
     *
     * @return The logical nor of the supplied expressions.
     *
     * @throws NullPointerException
     *     Any of the supplied expressions is {@code null}.
     */
    public static NonDisjunctiveExpression nor(final Expression... expressions) {
        return NonDisjunctiveExpression.withDefaultName(Arrays.asList(expressions));
    }
}
