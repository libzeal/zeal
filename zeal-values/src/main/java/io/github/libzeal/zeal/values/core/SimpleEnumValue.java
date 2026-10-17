package io.github.libzeal.zeal.values.core;

/**
 * An expression used to evaluate {@link Enum} instances.
 *
 * @param <T>
 *     The type of the enum being evaluated.
 *
 * @author Justin Albano
 */
public final class SimpleEnumValue<T extends Enum<T>> extends EnumValue<T, SimpleEnumValue<T>> {

    /**
     * Creates a new expression for the supplied subject.
     *
     * @param subject
     *     The subject of the expression.
     */
    public SimpleEnumValue(final T subject) {
        super(subject);
    }
}