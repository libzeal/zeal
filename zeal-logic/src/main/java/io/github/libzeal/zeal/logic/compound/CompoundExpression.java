package io.github.libzeal.zeal.logic.compound;

import io.github.libzeal.zeal.logic.Expression;

/**
 * An expression composed of sub-expressions.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public interface CompoundExpression extends Expression {

    /**
     * Appends the supplied expression to this compound expression.
     *
     * @param expression
     *     The expression to append.
     */
    void append(Expression expression);

    /**
     * Prepends the supplied expression to this compound expression.
     *
     * @param expression
     *      The expression to prepend.
     */
    void prepend(Expression expression);
}
