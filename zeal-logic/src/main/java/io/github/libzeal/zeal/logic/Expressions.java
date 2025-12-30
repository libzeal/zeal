package io.github.libzeal.zeal.logic;

import java.util.Arrays;
import java.util.function.BooleanSupplier;

/**
 * A set of common logical expressions.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public final class Expressions {

    private Expressions() {
    }

    /**
     * Creates a tautology.
     *
     * @return A tautology.
     *
     * @since 0.2.1
     */
    public static Expression tautology() {
        return new Tautology();
    }

    /**
     * Creates a contradiction.
     *
     * @return A contradiction.
     *
     * @since 0.2.1
     */
    public static Expression contradiction() {
        return new Contradiction();
    }

    /**
     * Creates an expression using the supplied boolean expression.
     *
     * @param expression
     *     The desired boolean expression.
     *
     * @return An expression matching the supplied boolean expression.
     *
     * @throws NullPointerException
     *     The supplied expression is {@code null}.
     */
    public static BooleanExpression of(final BooleanSupplier expression) {
        return new BooleanExpression(Expression.DEFAULT_NAME,  expression);
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
    public static NotExpression not(final Expression expression) {
        return new NotExpression(expression);
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
    public static AndExpression and(final Expression... expressions) {
        return AndExpression.withDefaultName(Arrays.asList(expressions));
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
    public static OrExpression or(final Expression... expressions) {
        return OrExpression.withDefaultName(Arrays.asList(expressions));
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
    public static NotExpression nand(final Expression... expressions) {
        return new NotExpression(AndExpression.withDefaultName(Arrays.asList(expressions)));
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
    public static NotExpression nor(final Expression... expressions) {
        return new NotExpression(OrExpression.withDefaultName(Arrays.asList(expressions)));
    }
}
