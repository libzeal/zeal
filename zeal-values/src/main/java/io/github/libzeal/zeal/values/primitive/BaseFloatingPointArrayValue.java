package io.github.libzeal.zeal.values.primitive;

import org.jspecify.annotations.Nullable;

import java.util.Comparator;
import java.util.function.ToDoubleFunction;

/**
 * Value containing common floating-point array checks.
 * <p>
 * Comparison is done using the
 * {@link Comparator#comparingDouble(ToDoubleFunction)}, with {@code NaN}
 * ordered after all other numbers and {@code -0.0} ordered before {@code 0.0}.
 * Note that widening a float is an exact operation, so the same rules apply to
 * both {@code float} and {@code double}.
 *
 * @param <A>
 *         The array type, such as {@code float[]} or {@code double[]}.
 * @param <T>
 *         The element type, such as {@link Float} or {@link Double}.
 * @param <V>
 *         The implementation type (the value type) used for fluent interface
 *         return values.
 *
 * @author Justin Albano
 */
abstract class BaseFloatingPointArrayValue<A, T extends Number, V extends BaseFloatingPointArrayValue<A, T, V>>
        extends BaseNumericArrayValue<A, T, V> {

    BaseFloatingPointArrayValue(final @Nullable A subject, final String name, final PrimitiveArrayOperations<T, A> operations) {
        super(subject, name, operations, Comparator.comparingDouble(Number::doubleValue));
    }

    /**
     * Checks if the sum of all elements in the array is equal to the supplied
     * expected value within the supplied tolerance.
     * <p>
     * Floating point values are rarely exact and the supplied tolerance should
     * be used to define the allowable deviation for the expected value. A
     * {@code NaN} or infinite sum never passes.
     * <p>
     * Summation is performed using a standard {@code for} loop, which may be
     * slow for very large floating point arrays. The <a
     * href="https://docs.oracle.com/javase/specs/jls/se21/html/jls-15.html#jls-15.18.2">Java
     * Language Specification (JLS) 15.18.2</a> specifies that floating point
     * additions are not associative (and cannot be reordered); thus summations
     * are not auto-vectorized and lack the benefits of Single Instruction,
     * Multiple Data (SIMD). Therefore, there may be some performance loss when
     * checking very large arrays.
     *
     * @param expected
     *         The expected sum.
     * @param tolerance
     *         The allowable tolerance to account for the imprecision of
     *         floating point numbers.
     *
     * @return This value (fluent interface).
     *
     * @throws IllegalArgumentException
     *         The supplied tolerance is negative or {@code NaN}.
     */
    public V sumIs(final double expected, final double tolerance) {

        if (!(tolerance >= 0)) {
            throw new IllegalArgumentException("Tolerance must be zero or greater, but was " + tolerance);
        }

        final String expectedDisplay = "sum within " + tolerance + " of " + expected;

        return append(
                observing(this::sum)
                        .passesIf(sum -> Math.abs(sum - expected) <= tolerance)
                        .name("sumIs[expected=" + expected + ", tolerance=" + tolerance + "]")
                        .expected(expectedDisplay)
                        .actual(context -> "sum = " + context.observed())
                        .hint(context -> context.passed() ?
                                "The sum of the elements is within " + tolerance + " of " + expected :
                                "The sum of the elements differs from " + expected + " by more than " + tolerance
                        )
                        .build()
        );
    }

    private double sum(final A array) {

        double sum = 0;

        for (int i = 0; i < operations().size(array); i++) {
            sum += operations().elementAt(array, i).doubleValue();
        }

        return sum;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Note: {@code NaN} counts as the largest value.
     *
     * @return This value (fluent interface).
     */
    @Override
    public V isAscending() {
        return super.isAscending();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Note: {@code NaN} counts as the largest value.
     *
     * @return This value (fluent interface).
     */
    @Override
    public V isDescending() {
        return super.isDescending();
    }
}
