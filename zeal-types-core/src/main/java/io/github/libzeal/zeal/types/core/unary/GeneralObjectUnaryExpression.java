package io.github.libzeal.zeal.types.core.unary;

/**
 * An expression used to evaluate {@link Object} instances.
 *
 * @param <T>
 *     The type of the object being evaluated.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public final class GeneralObjectUnaryExpression<T> extends ObjectUnaryExpression<T, GeneralObjectUnaryExpression<T>> {

    /**
     * Creates a new expression for the supplied subject.
     *
     * @param subject
     *     The subject of the expression.
     */
    public GeneralObjectUnaryExpression(T subject) {
        super(subject);
    }
}