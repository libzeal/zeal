package io.github.libzeal.zeal.values.primitive;

import org.jspecify.annotations.Nullable;

/**
 * A value used to evaluate {@code float[]} values.
 *
 * @author Justin Albano
 */
public final class FloatArrayValue extends BaseFloatingPointArrayValue<float[], Float, FloatArrayValue> {

    private static final PrimitiveArrayOperations<Float, float[]> OPERATIONS =
            new PrimitiveArrayOperations<>(array -> array.length, (array, index) -> array[index]);

    /**
     * Creates a new {@code float[]} value.
     *
     * @param subject
     *         The desired subject.
     */
    public FloatArrayValue(final float @Nullable [] subject) {
        super(subject, "Float array value", OPERATIONS);
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
    public FloatArrayValue minimumIs(final float expected) {
        return minimumIs(String.valueOf(expected), element -> Float.compare(element, expected) == 0);
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
    public FloatArrayValue maximumIs(final float expected) {
        return maximumIs(String.valueOf(expected), element -> Float.compare(element, expected) == 0);
    }
}
