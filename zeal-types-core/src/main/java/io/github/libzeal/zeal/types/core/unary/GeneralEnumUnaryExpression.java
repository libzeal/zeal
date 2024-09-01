package io.github.libzeal.zeal.types.core.unary;

/**
 * An expression used to evaluate {@link Enum} instances.
 *
 * @param <T>
 *     The type of the enum being evaluated.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public final class GeneralEnumUnaryExpression<T extends Enum<T>> extends EnumUnaryExpression<T, GeneralEnumUnaryExpression<T>> {

    /**
     * Creates a new expression for the supplied subject.
     *
     * @param subject
     *     The subject of the expression.
     */
    public GeneralEnumUnaryExpression(T subject) {
        super(subject);
    }
}