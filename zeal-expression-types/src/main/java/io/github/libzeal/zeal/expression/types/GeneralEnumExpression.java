package io.github.libzeal.zeal.expression.types;

/**
 * An expression used to evaluate {@link Enum} instances.
 *
 * @param <T>
 *     The type of the enum being evaluated.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public final class GeneralEnumExpression<T extends Enum<T>> extends EnumExpression<T, GeneralEnumExpression<T>> {

    /**
     * Creates a new expression for the supplied subject.
     *
     * @param subject
     *     The subject of the expression.
     */
    public GeneralEnumExpression(T subject) {
        super(subject);
    }
}