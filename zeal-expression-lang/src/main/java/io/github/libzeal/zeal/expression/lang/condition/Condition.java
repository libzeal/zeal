package io.github.libzeal.zeal.expression.lang.condition;

import java.util.function.Predicate;

/**
 * A named predicate that represents a desired condition that an expression should satisfy. These conditions are usually
 * provided as arguments to a {@code satisfies} method.
 *
 * @param <T>
 *     The type of the subject being tested.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public interface Condition<T> extends Predicate<T> {

    /**
     * Obtains the name of the condition.
     *
     * @return The name of the condition.
     */
    String name();
}
