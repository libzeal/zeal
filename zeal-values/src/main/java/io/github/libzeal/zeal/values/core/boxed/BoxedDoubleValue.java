package io.github.libzeal.zeal.values.core.boxed;

import org.jspecify.annotations.Nullable;

/**
 * A value used to evaluate {@link Double}s.
 *
 * @author Justin Albano
 */
public class BoxedDoubleValue extends BaseBoxedFloatingPointValue<Double, BoxedDoubleValue> {

    /**
     * Creates a new Double value.
     *
     * @param subject
     *         The desired subject.
     */
    public BoxedDoubleValue(final @Nullable Double subject) {
        super(
                subject,
                "Double value",
                new FloatingPointOperations<>(
                        0.0,
                        -Double.MAX_VALUE,
                        Double.MAX_VALUE
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
    public BoxedDoubleValue isEqualTo(final Double value, final Double delta) {
        return append(
                expression(d -> Math.abs(d - value) <= delta)
                        .name("isEqualTo[" + value + " +/- " + delta + "]")
                        .expected(value + " +/- " + delta)
                        .build()
        );
    }
}
