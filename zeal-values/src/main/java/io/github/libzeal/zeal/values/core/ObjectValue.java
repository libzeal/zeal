package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.values.api.BaseObjectValue;

/**
 * An expression used to evaluate {@link Object} instances.
 *
 * @param <T>
 *     The type of the object being evaluated.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public final class ObjectValue<T> extends BaseObjectValue<T, ObjectValue<T>> {

    /**
     * Creates a new expression for the supplied subject.
     *
     * @param subject
     *     The subject of the expression.
     */
    public ObjectValue(final T subject) {
        super(subject);
    }
}