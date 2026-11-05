package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.CompoundExpression;

/**
 * An expression that has a single subject and is composed of child
 * expressions.
 *
 * @param <S>
 *         The type of the subject.
 *
 * @author Justin Albano
 */
public non-sealed interface CompoundUnaryExpression<S>
        extends UnaryExpression<S>, CompoundExpression {
}
