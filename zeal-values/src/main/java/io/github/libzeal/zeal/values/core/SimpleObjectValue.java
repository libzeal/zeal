package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.values.sdk.BaseValue;
import org.jspecify.annotations.Nullable;

/**
 * A value used to evaluate arrays.
 *
 * @param <T>
 *         The type of the elements in the array.
 *
 * @author Justin Albano
 */
public final class SimpleObjectValue<T> extends BaseValue<T, SimpleObjectValue<T>> {

    /**
     * Creates a new expression for the supplied subject.
     *
     * @param subject
     *         The subject of the expression.
     */
    public SimpleObjectValue(final @Nullable T subject) {
        super(subject);
    }
}