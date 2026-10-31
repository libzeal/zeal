package io.github.libzeal.zeal.values;

import io.github.libzeal.zeal.values.collection.*;
import io.github.libzeal.zeal.values.core.SimpleEnumValue;
import io.github.libzeal.zeal.values.core.SimpleObjectValue;
import io.github.libzeal.zeal.values.core.StringValue;
import io.github.libzeal.zeal.values.core.boxed.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A class containing static methods that wrap common values as unary
 * expressions.
 *
 * @author Justin Albano
 */
public class Values {

    private Values() {
    }

    /**
     * Integer Wraps an object as a value. This method is used when a
     * more-specific expression is not available.
     *
     * @param value
     *         The value to wrap.
     * @param <T>
     *         The type of the object being wrapped.
     *
     * @return A value that wraps the supplied value.
     */
    public static <T> SimpleObjectValue<T> value(final T value) {
        return new SimpleObjectValue<>(value);
    }

    /**
     * Wraps a {@link Enum} as a value.
     *
     * @param value
     *         The {@link Enum} to wrap.
     * @param <T>
     *         The type of the enum.
     *
     * @return A value that wraps the supplied {@link Enum}.
     */
    public static <T extends Enum<T>> SimpleEnumValue<T> value(final T value) {
        return new SimpleEnumValue<>(value);
    }

    /**
     * Wraps a {@link String} as a value.
     *
     * @param value
     *         The {@link String} to wrap.
     *
     * @return A value that wraps the supplied {@link String}.
     */
    public static StringValue value(final String value) {
        return new StringValue(value);
    }

    /**
     * Wraps a {@link Long} as a value.
     *
     * @param value
     *         The {@link Long} to wrap.
     *
     * @return A value that wraps the supplied {@link Long}.
     */
    public static BoxedLongValue value(final Long value) {
        return new BoxedLongValue(value);
    }

    /**
     * Wraps a {@link Integer} as a value.
     *
     * @param value
     *         The {@link Integer} to wrap.
     *
     * @return A value that wraps the supplied {@link Integer}.
     */
    public static BoxedIntegerValue value(final Integer value) {
        return new BoxedIntegerValue(value);
    }

    /**
     * Wraps a {@link Double} as a value.
     *
     * @param value
     *         The {@link Double} to wrap.
     *
     * @return A value that wraps the supplied {@link Double}.
     */
    public static BoxedDoubleValue value(final Double value) {
        return new BoxedDoubleValue(value);
    }

    /**
     * Wraps a {@link Float} as a value.
     *
     * @param value
     *         The {@link Float} to wrap.
     *
     * @return A value that wraps the supplied {@link Float}.
     */
    public static BoxedFloatValue value(final Float value) {
        return new BoxedFloatValue(value);
    }

    /**
     * Wraps a {@link Boolean} as a value.
     *
     * @param value
     *         The {@link Boolean} to wrap.
     *
     * @return A value that wraps the supplied {@link Boolean}.
     */
    public static BoxedBooleanValue value(final Boolean value) {
        return new BoxedBooleanValue(value);
    }

    /**
     * Wraps a {@link Byte} as a value.
     *
     * @param value
     *         The {@link Byte} to wrap.
     *
     * @return A value that wraps the supplied {@link Byte}.
     */
    public static BoxedByteValue value(final Byte value) {
        return new BoxedByteValue(value);
    }

    /**
     * Wraps a {@link Character} as a value.
     *
     * @param value
     *         The {@link Character} to wrap.
     *
     * @return A value that wraps the supplied {@link Character}.
     */
    public static BoxedCharacterValue value(final Character value) {
        return new BoxedCharacterValue(value);
    }

    // -------------------------------------------------------------------------
    // Arrays & Collections
    // -------------------------------------------------------------------------

    /**
     * Wraps an object array as a value.
     *
     * @param value
     *         The object array to wrap.
     * @param <T>
     *         The type of the elements in the array.
     *
     * @return A value that wraps the supplied object array.
     */
    public static <T> ArrayValue<T> value(final T[] value) {
        return new ArrayValue<>(value);
    }

    /**
     * Wraps an {@link Iterator} as a value.
     *
     * @param value
     *         The {@link Iterator} to wrap.
     * @param <T>
     *         The type of the elements in the {@link Iterator}.
     *
     * @return A value that wraps the supplied {@link Iterator}.
     */
    public static <T> IteratorValue<T> value(final Iterator<T> value) {
        return new IteratorValue<>(value);
    }

    /**
     * Wraps an {@link Iterable} as a value.
     *
     * @param value
     *         The {@link Iterable} to wrap.
     * @param <T>
     *         The type of the elements in the {@link Iterable}.
     *
     * @return A value that wraps the supplied {@link Iterable}.
     */
    public static <T> IterableValue<T> value(final Iterable<T> value) {
        return new IterableValue<>(value);
    }

    /**
     * Wraps a {@link Collection} as a value.
     *
     * @param value
     *         The {@link Collection} to wrap.
     * @param <T>
     *         The type of the elements in the {@link Collection}.
     *
     * @return A value that wraps the supplied {@link Collection}.
     */
    public static <T> SimpleCollectionValue<T> value(final Collection<T> value) {
        return new SimpleCollectionValue<>(value);
    }

    /**
     * Wraps an {@link List} as a value.
     *
     * @param value
     *         The {@link List} to wrap.
     * @param <T>
     *         The type of the elements in the {@link List}.
     *
     * @return A value that wraps the supplied {@link List}.
     */
    public static <T> ListValue<T> value(final List<T> value) {
        return new ListValue<>(value);
    }

    /**
     * Wraps an {@link Set} as a value.
     *
     * @param value
     *         The {@link Set} to wrap.
     * @param <T>
     *         The type of the elements in the {@link Set}.
     *
     * @return A value that wraps the supplied {@link Set}.
     */
    public static <T> SetValue<T> value(final Set<T> value) {
        return new SetValue<>(value);
    }
}
