package io.github.libzeal.zeal.values.primitive;

import org.jspecify.annotations.Nullable;

/**
 * A value used to evaluate {@code byte[]} values.
 *
 * @author Justin Albano
 */
public final class ByteArrayValue extends BaseWholeNumberArrayValue<byte[], Byte, ByteArrayValue> {

    private static final PrimitiveArrayOperations<Byte, byte[]> OPERATIONS =
            new PrimitiveArrayOperations<>(array -> array.length, (array, index) -> array[index]);

    /**
     * Creates a new {@code byte[]} value.
     *
     * @param subject
     *         The desired subject.
     */
    public ByteArrayValue(final byte @Nullable [] subject) {
        super(subject, "Byte array value", OPERATIONS);
    }
}
