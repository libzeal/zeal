package io.github.libzeal.zeal.logic.condition;

import io.github.libzeal.zeal.logic.Expression;

/**
 * An object that creates an expression based on a subject.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
@FunctionalInterface
public interface Condition<T> {

    /**
     * Creates an expression based on the supplied subject.
     *
     * @param subject
     *     The subject to transform into an expression.
     *
     * @return An expression based on the supplied subject.
     */
    Expression create(T subject);
}
