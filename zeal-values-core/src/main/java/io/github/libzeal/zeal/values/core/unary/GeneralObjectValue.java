package io.github.libzeal.zeal.values.core.unary;

/**
 * An expression used to evaluate {@link Object} instances.
 *
 * @param <T>
 *     The type of the object being evaluated.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public final class GeneralObjectValue<T> extends ObjectValue<T, GeneralObjectValue<T>> {

    /**
     * Creates a new expression for the supplied subject.
     *
     * @param subject
     *     The subject of the expression.
     */
    public GeneralObjectValue(T subject) {
        super(subject);
    }
}