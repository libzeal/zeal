package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.TerminalExpression;

/**
 * An expression that has a single subject is not composed of any
 * sub-expressions.
 *
 * @param <S>
 *         The type of the subject.
 *
 * @author Justin Albano
 */
public non-sealed interface TerminalUnaryExpression<S>
        extends UnaryExpression<S>, TerminalExpression {
}
