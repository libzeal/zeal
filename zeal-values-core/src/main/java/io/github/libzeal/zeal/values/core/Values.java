package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.values.core.basic.GeneralEnumValue;
import io.github.libzeal.zeal.values.core.basic.GeneralObjectValue;
import io.github.libzeal.zeal.values.core.basic.StringValue;
import io.github.libzeal.zeal.values.core.boxed.*;
import io.github.libzeal.zeal.values.core.unary.boxed.*;

/**
 * A class containing static methods that wrap common values as unary expressions.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class Values {

    private Values() {
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
    public static <T> GeneralObjectValue<T> value(final T value) {
        return new GeneralObjectValue<>(value);
    }

    /**
     * Wraps a {@link Enum} as an expression.
     *
     * @param value
     *     The {@link Enum} to wrap.
     * @param <T>
     *     The type of the enum.
     *
     * @return An expression that wraps the supplied {@link Enum}.
     */
    public static <T extends Enum<T>> GeneralEnumValue<T> value(final T value) {
        return new GeneralEnumValue<>(value);
    }

    /**
     * Wraps a {@link String} as an expression.
     *
     * @param value
     *     The {@link String} to wrap.
     *
     * @return An expression that wraps the supplied {@link String}.
     */
    public static StringValue value(final String value) {
        return new StringValue(value);
    }

    /**
     * Wraps a {@link Long} as an expression.
     *
     * @param value
     *     The {@link Long} to wrap.
     *
     * @return An expression that wraps the supplied {@link Long}.
     */
    public static BoxedLongValue value(final Long value) {
        return new BoxedLongValue(value);
    }

    /**
     * Wraps a {@link Integer} as an expression.
     *
     * @param value
     *     The {@link Integer} to wrap.
     *
     * @return An expression that wraps the supplied {@link Integer}.
     */
    public static BoxedIntegerValue value(final Integer value) {
        return new BoxedIntegerValue(value);
    }

    /**
     * Wraps a {@link Double} as an expression.
     *
     * @param value
     *     The {@link Double} to wrap.
     *
     * @return An expression that wraps the supplied {@link Double}.
     */
    public static BoxedDoubleValue value(final Double value) {
        return new BoxedDoubleValue(value);
    }

    /**
     * Wraps a {@link Float} as an expression.
     *
     * @param value
     *     The {@link Float} to wrap.
     *
     * @return An expression that wraps the supplied {@link Float}.
     */
    public static BoxedFloatValue value(final Float value) {
        return new BoxedFloatValue(value);
    }

    /**
     * Wraps a {@link Boolean} as an expression.
     *
     * @param value
     *     The {@link Boolean} to wrap.
     *
     * @return An expression that wraps the supplied {@link Boolean}.
     */
    public static BoxedBooleanValue value(final Boolean value) {
        return new BoxedBooleanValue(value);
    }

    /**
     * Wraps a {@link Byte} as an expression.
     *
     * @param value
     *     The {@link Byte} to wrap.
     *
     * @return An expression that wraps the supplied {@link Byte}.
     */
    public static BoxedByteValue value(final Byte value) {
        return new BoxedByteValue(value);
    }

    /**
     * Wraps a {@link Character} as an expression.
     *
     * @param value
     *     The {@link Character} to wrap.
     *
     * @return An expression that wraps the supplied {@link Character}.
     */
    public static BoxedCharacterValue value(final Character value) {
        return new BoxedCharacterValue(value);
    }
}
