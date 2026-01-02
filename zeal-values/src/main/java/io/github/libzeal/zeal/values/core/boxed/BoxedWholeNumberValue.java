package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.values.api.BaseObjectValue;

/**
 * An expression used to evaluate whole number {@link Number} instances (such as {@link Long}).
 *
 * @param <T>
 *     The type of the subject.
 * @param <E>
 *     The type of the subclass.
 *
 * @author Justin Albano
 * @see BaseObjectValue
 * @since 0.2.0
 */
public abstract class BoxedWholeNumberValue<T extends Number, E extends BaseObjectValue<T, E>>
    extends BoxedNumberValue<T, E> {

    /**
     * Creates a new expression.
     *
     * @param subject
     *     The subject of the expression.
     * @param name
     *     The name of the expression.
     */
    protected BoxedWholeNumberValue(final T subject, final String name) {
        super(subject, name);
    }

    /**
     * Adds a predicate to the expression that checks if the subject is even.
     *
     * @return This expression (fluent interface).
     */
    public E isEven() {
        return append(
            expression(l -> l.longValue() % 2 == 0)
                .name("isEven")
                .expected("% 2 := 0")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is odd.
     *
     * @return This expression (fluent interface).
     */
    public E isOdd() {
        return append(
            expression(l -> l.longValue() % 2 != 0)
                .name("isOdd")
                .expected("% 2 != 0")
        );
    }
}
