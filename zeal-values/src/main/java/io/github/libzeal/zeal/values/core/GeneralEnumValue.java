package io.github.libzeal.zeal.values.core;

/**
 * An expression used to evaluate {@link Enum} instances.
 *
 * @param <T>
 *     The type of the enum being evaluated.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public final class GeneralEnumValue<T extends Enum<T>> extends EnumValue<T, GeneralEnumValue<T>> {

    /**
     * Creates a new expression for the supplied subject.
     *
     * @param subject
     *     The subject of the expression.
     */
    public GeneralEnumValue(final T subject) {
        super(subject);
    }
}