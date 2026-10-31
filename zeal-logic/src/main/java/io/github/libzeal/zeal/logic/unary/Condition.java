package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.Expressions;

/**
 * An object that creates an expression based on a subject.
 *
 * @param <T>
 *         The type of the subject.
 *
 * @author Justin Albano
 */
@FunctionalInterface
public interface Condition<T> {

    /**
     * The default name for unnamed conditions.
     */
    String DEFAULT_NAME = "unnamed";

    /**
     * Binds a subject to the condition to create an expression.
     *
     * @param subject
     *         The subject to bind.
     *
     * @return An expression resulting from binding the supplied subject with
     * the condition.
     */
    Expression bind(T subject);

    /**
     * A type-safe tautological condition that always results in a true
     * expression.
     *
     * @param <T>
     *         The type of the subject.
     *
     * @return A tautology.
     */
    static <T> Condition<T> tautology() {
        return subject -> Expressions.tautology();
    }

    /**
     * A type-safe contradictory condition that always results in a false
     * expression.
     *
     * @param <T>
     *         The type of the subject.
     *
     * @return A contradiction.
     */
    static <T> Condition<T> contradiction() {
        return subject -> Expressions.contradiction();
    }
}
