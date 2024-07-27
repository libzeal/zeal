package io.github.libzeal.zeal.expression.types;

/**
 * An expression used to evaluate {@link Object} instances.
 *
 * @param <T>
 *     The type of the object being evaluated.
 */
public final class GeneralObjectExpression<T> extends ObjectExpression<T, GeneralObjectExpression<T>> {

    /**
     * Creates a new expression for the supplied subject.
     *
     * @param subject
     *     The subject of the expression.
     */
    public GeneralObjectExpression(T subject) {
        super(subject);
    }
}
