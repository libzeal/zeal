package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.Expression;

/**
 * An object that creates an expression once a subject is known.
 *
 * @param <T>
 *     The type of the subject.
 */
@FunctionalInterface
public interface ComputableExpression<T> {
    Expression compute(T subject);
}
