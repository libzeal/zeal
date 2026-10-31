package io.github.libzeal.zeal.logic;

import java.util.Arrays;
import java.util.function.BooleanSupplier;

/**
 * A set of common logical expressions.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
public final class Expressions {

    private Expressions() {
    }

    /**
     * Creates a tautology.
     *
     * @return A tautology.
     */
    public static Tautology tautology() {
        return new Tautology();
    }

    /**
     * Creates a contradiction.
     *
     * @return A contradiction.
     */
    public static Contradiction contradiction() {
        return new Contradiction();
    }

    /**
     * Creates an expression using the supplied boolean expression.
     *
     * @param expression
     *         The desired boolean expression.
     *
     * @return An expression matching the supplied boolean expression.
     *
     * @throws NullPointerException
     *         The supplied expression is {@code null}.
     */
    public static BooleanExpression of(final BooleanSupplier expression) {
        return new BooleanExpression(Expression.DEFAULT_NAME, expression);
    }

    /**
     * Creates a new expression that is the logical NOT of the supplied
     * expression.
     *
     * @param expression
     *         The expression to negate.
     *
     * @return The logical NOT of the supplied expression.
     *
     * @throws NullPointerException
     *         The supplied expression is {@code null}.
     */
    public static NotExpression not(final Expression expression) {
        return NotExpression.unnamed(expression);
    }

    /**
     * Creates a new expression that is the logical AND of the supplied
     * expressions.
     *
     * @param expressions
     *         The expressions to AND.
     *
     * @return The logical AND of the supplied expressions.
     *
     * @throws NullPointerException
     *         Any of the supplied expressions is {@code null}.
     */
    public static AndExpression and(final Expression... expressions) {
        return AndExpression.unnamed(Arrays.asList(expressions));
    }

    /**
     * Creates a new expression that is the logical OR of the supplied
     * expressions.
     *
     * @param expressions
     *         The expressions to OR.
     *
     * @return The logical OR of the supplied expressions.
     *
     * @throws NullPointerException
     *         Any of the supplied expressions is {@code null}.
     */
    public static OrExpression or(final Expression... expressions) {
        return OrExpression.unnamed(Arrays.asList(expressions));
    }

    /**
     * Creates a new expression that is the logical NAND of the supplied
     * expressions.
     *
     * @param expressions
     *         The expressions to NAND.
     *
     * @return The logical NAND of the supplied expressions.
     *
     * @throws NullPointerException
     *         Any of the supplied expressions is {@code null}.
     */
    public static NandExpression nand(final Expression... expressions) {
        return NandExpression.unnamed(expressions);
    }

    /**
     * Creates a new expression that is the logical NOR of the supplied
     * expressions.
     *
     * @param expressions
     *         The expressions to NOR.
     *
     * @return The logical NOR of the supplied expressions.
     *
     * @throws NullPointerException
     *         Any of the supplied expressions is {@code null}.
     */
    public static NorExpression nor(final Expression... expressions) {
        return NorExpression.unnamed(expressions);
    }

    /**
     * Creates a new expression that is the logical XOR of the supplied
     * expressions.
     *
     * @param expressions
     *         The expressions to XOR.
     *
     * @return The logical XOR of the supplied expressions.
     *
     * @throws NullPointerException
     *         Any of the supplied expressions is {@code null}.
     */
    public static XorExpression xor(final Expression... expressions) {
        return XorExpression.unnamed(expressions);
    }
}
