package io.github.libzeal.zeal.logic.condition;

import io.github.libzeal.zeal.logic.Expression;

/**
 * An object that creates an expression based on a subject.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.4.0
 */
@FunctionalInterface
public interface Condition<T> {

    /**
     * The default name for unnamed conditions.
     */
    String DEFAULT_NAME = "unnamed";

    /**
     * Creates an expression based on the supplied subject.
     *
     * @param subject
     *     The subject to transform into an expression.
     *
     * @return An expression based on the supplied subject.
     */
    Expression create(T subject);

    /**
     * A type-safe tautological condition that always results in a true expression.
     *
     * @param <T>
     *     The type of the subject.
     *
     * @return A tautology.
     */
    static <T> Condition<T> tautology() {
        return subject -> Expression.tautology();
    }


    /**
     * A type-safe contradictory condition that always results in a false expression.
     *
     * @param <T>
     *     The type of the subject.
     *
     * @return A contradiction.
     */
    static <T> Condition<T> contradiction() {
        return subject -> Expression.contradiction();
    }
}
