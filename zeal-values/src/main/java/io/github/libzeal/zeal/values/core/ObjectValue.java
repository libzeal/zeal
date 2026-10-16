package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.values.api.BaseValue;

/**
 * An expression used to evaluate {@link Object} instances.
 *
 * @param <T>
 *     The type of the object being evaluated.
 *
 * @author Justin Albano
 */
public final class ObjectValue<T> extends BaseValue<T, ObjectValue<T>> {

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