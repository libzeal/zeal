package io.github.libzeal.zeal.values;

import io.github.libzeal.zeal.values.collection.*;
import io.github.libzeal.zeal.values.core.SimpleEnumValue;
import io.github.libzeal.zeal.values.core.SimpleObjectValue;
import io.github.libzeal.zeal.values.core.StringValue;
import io.github.libzeal.zeal.values.core.boxed.*;
import io.github.libzeal.zeal.values.primitive.*;
import org.jspecify.annotations.Nullable;

import java.util.*;

/**
 * A class containing static methods that wrap common values as unary
 * expressions.
 *
 * @author Justin Albano
 */
public final class Values {

    private Values() {
    }

    /**
     * Wraps an object as a value. This method is used when a more-specific
     * value is not available.
     *
     * @param value
     *         The value to wrap.
     * @param <T>
     *         The type of the object being wrapped.
     *
     * @return A value that wraps the supplied value.
     */
    public static <T> SimpleObjectValue<T> value(final @Nullable T value) {
        return new SimpleObjectValue<>(value);
    }

    /**
     * Wraps an {@link Enum} as a value.
     *
     * @param value
     *         The {@link Enum} to wrap.
     * @param <T>
     *         The type of the enum.
     *
     * @return A value that wraps the supplied {@link Enum}.
     */
    public static <T extends Enum<T>> SimpleEnumValue<T> value(final @Nullable T value) {
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
    public static StringValue value(final @Nullable String value) {
        return new StringValue(value);
    }

    /**
     * Wraps a {@code long} as a value.
     *
     * @param value
     *         The {@code long} to wrap.
     *
     * @return A value that wraps the supplied {@code long}.
     */
    public static BoxedLongValue value(final @Nullable Long value) {
        return new BoxedLongValue(value);
    }

    /**
     * Wraps an {@link Integer} as a value.
     *
     * @param value
     *         The {@link Integer} to wrap.
     *
     * @return A value that wraps the supplied {@link Integer}.
     */
    public static BoxedIntegerValue value(final @Nullable Integer value) {
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
    public static BoxedDoubleValue value(final @Nullable Double value) {
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
    public static BoxedFloatValue value(final @Nullable Float value) {
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
    public static BoxedBooleanValue value(final @Nullable Boolean value) {
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
    public static BoxedByteValue value(final @Nullable Byte value) {
        return new BoxedByteValue(value);
    }

    /**
     * Wraps a {@link Short} as a value.
     *
     * @param value
     *         The {@link Short} to wrap.
     *
     * @return A value that wraps the supplied {@link Short}.
     */
    public static BoxedShortValue value(final @Nullable Short value) {
        return new BoxedShortValue(value);
    }

    /**
     * Wraps a {@link Character} as a value.
     *
     * @param value
     *         The {@link Character} to wrap.
     *
     * @return A value that wraps the supplied {@link Character}.
     */
    public static BoxedCharacterValue value(final @Nullable Character value) {
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
    public static <T extends @Nullable Object> ArrayValue<T> value(final T @Nullable [] value) {
        return new ArrayValue<>(value);
    }

    /**
     * Wraps a {@code boolean[]} as a value.
     *
     * @param value
     *         The {@code boolean[]} to wrap.
     *
     * @return A value that wraps the supplied {@code boolean[]}.
     */
    public static BooleanArrayValue value(final boolean @Nullable [] value) {
        return new BooleanArrayValue(value);
    }

    /**
     * Wraps a {@code char[]} as a value.
     *
     * @param value
     *         The {@code char[]} to wrap.
     *
     * @return A value that wraps the supplied {@code char[]}.
     */
    public static CharArrayValue value(final char @Nullable [] value) {
        return new CharArrayValue(value);
    }

    /**
     * Wraps a {@code byte[]} as a value.
     *
     * @param value
     *         The {@code byte[]} to wrap.
     *
     * @return A value that wraps the supplied {@code byte[]}.
     */
    public static ByteArrayValue value(final byte @Nullable [] value) {
        return new ByteArrayValue(value);
    }

    /**
     * Wraps a {@code short[]} as a value.
     *
     * @param value
     *         The {@code short[]} to wrap.
     *
     * @return A value that wraps the supplied {@code short[]}.
     */
    public static ShortArrayValue value(final short @Nullable [] value) {
        return new ShortArrayValue(value);
    }

    /**
     * Wraps an {@code int[]} as a value.
     *
     * @param value
     *         The {@code int[]} to wrap.
     *
     * @return A value that wraps the supplied {@code int[]}.
     */
    public static IntArrayValue value(final int @Nullable [] value) {
        return new IntArrayValue(value);
    }

    /**
     * Wraps a {@code long[]} as a value.
     *
     * @param value
     *         The {@code long[]} to wrap.
     *
     * @return A value that wraps the supplied {@code long[]}.
     */
    public static LongArrayValue value(final long @Nullable [] value) {
        return new LongArrayValue(value);
    }

    /**
     * Wraps a {@code float[]} as a value.
     *
     * @param value
     *         The {@code float[]} to wrap.
     *
     * @return A value that wraps the supplied {@code float[]}.
     */
    public static FloatArrayValue value(final float @Nullable [] value) {
        return new FloatArrayValue(value);
    }

    /**
     * Wraps a {@code double[]} as a value.
     *
     * @param value
     *         The {@code double[]} to wrap.
     *
     * @return A value that wraps the supplied {@code double[]}.
     */
    public static DoubleArrayValue value(final double @Nullable [] value) {
        return new DoubleArrayValue(value);
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
    public static <T extends @Nullable Object> IteratorValue<T> value(final @Nullable Iterator<T> value) {
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
    public static <T extends @Nullable Object> IterableValue<T> value(final @Nullable Iterable<T> value) {
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
    public static <T extends @Nullable Object> SimpleCollectionValue<T> value(final @Nullable Collection<T> value) {
        return new SimpleCollectionValue<>(value);
    }

    /**
     * Wraps a {@link List} as a value.
     *
     * @param value
     *         The {@link List} to wrap.
     * @param <T>
     *         The type of the elements in the {@link List}.
     *
     * @return A value that wraps the supplied {@link List}.
     */
    public static <T extends @Nullable Object> ListValue<T> value(final @Nullable List<T> value) {
        return new ListValue<>(value);
    }

    /**
     * Wraps a {@link Set} as a value.
     *
     * @param value
     *         The {@link Set} to wrap.
     * @param <T>
     *         The type of the elements in the {@link Set}.
     *
     * @return A value that wraps the supplied {@link Set}.
     */
    public static <T extends @Nullable Object> SetValue<T> value(final @Nullable Set<T> value) {
        return new SetValue<>(value);
    }

    /**
     * Wraps a {@link Map} as a value.
     *
     * @param value
     *         The {@link Map} to wrap.
     * @param <K>
     *         The type of the key in the {@link Map}.
     * @param <V>
     *         The type of the value in the {@link Map}.
     *
     * @return A value that wraps the supplied {@link Map}.
     */
    public static <K extends @Nullable Object, V extends @Nullable Object> MapValue<K, V> value(final @Nullable Map<K, V> value) {
        return new MapValue<>(value);
    }
}
