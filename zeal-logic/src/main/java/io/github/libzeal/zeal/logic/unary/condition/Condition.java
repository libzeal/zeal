package io.github.libzeal.zeal.logic.unary.condition;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.Expressions;
import io.github.libzeal.zeal.logic.unary.future.ComputableExpression;

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
public interface Condition<T> extends ComputableExpression<T> {

    /**
     * The default name for unnamed conditions.
     */
    String DEFAULT_NAME = "unnamed";

    /**
     * A type-safe tautological condition that always results in a true expression.
     *
     * @param <T>
     *     The type of the subject.
     *
     * @return A tautology.
     */
    static <T> Condition<T> tautology() {
        return subject -> Expressions.tautology();
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
        return subject -> Expressions.contradiction();
    }
}
