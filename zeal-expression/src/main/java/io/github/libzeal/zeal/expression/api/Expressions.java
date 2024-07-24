package io.github.libzeal.zeal.expression.api;

import io.github.libzeal.zeal.expression.types.EnumExpression;
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
     * Wraps a string as an expression.
     *
     * @param value
     *     The string to wrap.
     *
     * @return An expression that wraps the supplied string.
     */
    public static StringExpression that(String value) {
        return new StringExpression(value);
    }

    public static <T extends Enum<T>> GeneralEnumExpression<T> that(T value) {
        return new GeneralEnumExpression<>(value);
    }
}
