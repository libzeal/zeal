package io.github.libzeal.zeal.values.core.boxed;

import org.jspecify.annotations.Nullable;

/**
 * A value used to evaluate {@link Float}s.
 *
 * @author Justin Albano
 */
public final class BoxedFloatValue extends BaseBoxedFloatingPointValue<Float, BoxedFloatValue> {

    /**
     * Creates a new Float value.
     *
     * @param subject
     *         The desired subject.
     */
    public BoxedFloatValue(final @Nullable Float subject) {
        super(
                subject,
                "Float value",
                new FloatingPointOperations<>(
                        0.0f,
                        -Float.MAX_VALUE,
                        Float.MAX_VALUE
                )
        );
    }

    /**
     * Checks if the subject is equal within the supplied delta.
     * <p>
     * Note: The formula used to evaluate the predicate is:
     * <pre>{@code abs(subject - value) <= delta}</pre>
     *
     * @param value
     *         The value to compare to the subject.
     * @param delta
     *         The delta used to compare the expected value to the subject.
     *
     * @return This value (fluent interface).
     */
    public BoxedFloatValue isEqualTo(final Float value, final Float delta) {
        return append(
                expression(d -> Math.abs(d - value) <= delta)
                        .name("isEqualTo[" + value + " +/- " + delta + "]")
                        .expected(value + " +/- " + delta)
                        .build()
        );
    }
}
