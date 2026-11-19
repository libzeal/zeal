package io.github.libzeal.zeal.values.primitive;

import org.jspecify.annotations.Nullable;

/**
 * A value used to evaluate {@code double[]} values.
 *
 * @author Justin Albano
 */
public final class DoubleArrayValue extends BaseFloatingPointArrayValue<double[], Double, DoubleArrayValue> {

    private static final PrimitiveArrayOperations<Double, double[]> OPERATIONS =
            new PrimitiveArrayOperations<>(array -> array.length, (array, index) -> array[index]);

    /**
     * Creates a new {@code double[]} value.
     *
     * @param subject
     *         The desired subject.
     */
    public DoubleArrayValue(final double @Nullable [] subject) {
        super(subject, "Double array value", OPERATIONS);
    }

    /**
     * Checks that the minimum value is the supplied value. {@code NaN} is a
     * valid expected value and {@code -0.0} is distinct from {@code 0.0} and
     * will not match with one another.
     *
     * @param expected
     *         The expected minimum value.
     *
     * @return This value (fluent interface).
     */
    public DoubleArrayValue minimumIs(final double expected) {
        return minimumIs(String.valueOf(expected), element -> Double.compare(element, expected) == 0);
    }

    /**
     * Checks that the maximum value is the supplied value. {@code NaN} is a
     * valid expected value and {@code -0.0} is distinct from {@code 0.0} and
     * will not match with one another.
     *
     * @param expected
     *         The expected maximum value.
     *
     * @return This value (fluent interface).
     */
    public DoubleArrayValue maximumIs(final double expected) {
        return maximumIs(String.valueOf(expected), element -> Double.compare(element, expected) == 0);
    }
}
