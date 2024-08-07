package io.github.libzeal.zeal.expression;

import io.github.libzeal.zeal.expression.types.boxed.*;
import io.github.libzeal.zeal.expression.types.GeneralEnumExpression;
import io.github.libzeal.zeal.expression.types.GeneralObjectExpression;
import io.github.libzeal.zeal.expression.types.StringExpression;

/**
 * A class containing static methods that wrap common values as expressions.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class Expressions {

    private Expressions() {
    }

    /**Integer
     * Wraps an object as an expression. This method is used when a more-specific expression is not available.
     *
     * @param value
     *     The value to wrap.
     * @param <T>
     *     The type of the object being wrapped.
     *
     * @return An expression that wraps the supplied value.
     */
    public static <T> GeneralObjectExpression<T> that(final T value) {
        return new GeneralObjectExpression<>(value);
    }

    /**
     * Wraps a {@link Enum<T>} as an expression.
     *
     * @param value
     *     The {@link Enum<T>} to wrap.
     * @param <T>
     *     The type of the enum.
     *
     * @return An expression that wraps the supplied {@link Enum<T>}.
     */
    public static <T extends Enum<T>> GeneralEnumExpression<T> that(final T value) {
        return new GeneralEnumExpression<>(value);
    }

    /**
     * Wraps a {@link String} as an expression.
     *
     * @param value
     *     The {@link String} to wrap.
     *
     * @return An expression that wraps the supplied {@link String}.
     */
    public static StringExpression that(final String value) {
        return new StringExpression(value);
    }

    /**
     * Wraps a {@link Long} as an expression.
     *
     * @param value
     *     The {@link Long} to wrap.
     *
     * @return An expression that wraps the supplied {@link Long}.
     */
    public static BoxedLongExpression that(final Long value) {
        return new BoxedLongExpression(value);
    }

    /**
     * Wraps a {@link Integer} as an expression.
     *
     * @param value
     *     The {@link Integer} to wrap.
     *
     * @return An expression that wraps the supplied {@link Integer}.
     */
    public static BoxedIntegerExpression that(final Integer value) {
        return new BoxedIntegerExpression(value);
    }

    /**
     * Wraps a {@link Double} as an expression.
     *
     * @param value
     *     The {@link Double} to wrap.
     *
     * @return An expression that wraps the supplied {@link Double}.
     */
    public static BoxedDoubleExpression that(final Double value) {
        return new BoxedDoubleExpression(value);
    }

    /**
     * Wraps a {@link Float} as an expression.
     *
     * @param value
     *     The {@link Float} to wrap.
     *
     * @return An expression that wraps the supplied {@link Float}.
     */
    public static BoxedFloatExpression that(final Float value) {
        return new BoxedFloatExpression(value);
    }

    /**
     * Wraps a {@link Boolean} as an expression.
     *
     * @param value
     *     The {@link Boolean} to wrap.
     *
     * @return An expression that wraps the supplied {@link Boolean}.
     */
    public static BoxedBooleanExpression that(final Boolean value) {
        return new BoxedBooleanExpression(value);
    }
}
