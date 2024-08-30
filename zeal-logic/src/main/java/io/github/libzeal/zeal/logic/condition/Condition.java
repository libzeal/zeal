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
     * The default name for unnamed conditions.
     */
    String DEFAULT_NAME = "unnamed";

    /**
     * A tautological condition that always results in a true expression.
     */
    Condition<Object> TRUE = subject -> Expression.TRUE;

    /**
     * A contradictory condition that always results in a false expression.
     */
    Condition<Object> FALSE = subject -> Expression.FALSE;

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
