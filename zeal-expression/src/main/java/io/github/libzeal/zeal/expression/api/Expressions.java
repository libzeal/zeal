package io.github.libzeal.zeal.expression.api;

import io.github.libzeal.zeal.expression.types.boxed.BoxedIntegerExpression;
import io.github.libzeal.zeal.expression.types.boxed.BoxedLongExpression;
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

    /**
     * Wraps an object as an expression. This method is used when a more-specific expression is not available.
     *
     * @param value
     *     The value to wrap.
     * @param <T>
     *     The type of the object being wrapped.
     *
     * @return An expression that wraps the supplied value.
     */
    public static <T> GeneralObjectExpression<T> that(T value) {
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
    public static <T extends Enum<T>> GeneralEnumExpression<T> that(T value) {
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
    public static StringExpression that(String value) {
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
    public static BoxedLongExpression that(Long value) {
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
    public static BoxedIntegerExpression that(Integer value) {
        return new BoxedIntegerExpression(value);
    }
}
