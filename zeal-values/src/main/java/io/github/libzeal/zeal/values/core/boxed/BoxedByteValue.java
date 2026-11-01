package io.github.libzeal.zeal.values.core.boxed;

/**
 * A value used to evaluate {@link Byte}s.
 *
 * @author Justin Albano
 */
public class BoxedByteValue
        extends BaseBoxedWholeNumberValue<Byte, BoxedByteValue> {

    /**
     * Creates a new Byte value.
     *
     * @param subject
     *         The desired subject.
     */
    public BoxedByteValue(final Byte subject) {
        super(
                subject,
                "Byte value",
                new WholeNumberOperations<>(
                        (byte) 0,
                        Byte.MIN_VALUE,
                        Byte.MAX_VALUE
                )
        );
    }
}
