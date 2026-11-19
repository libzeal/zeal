package io.github.libzeal.zeal.values.primitive;

import org.jspecify.annotations.Nullable;

/**
 * A value used to evaluate {@code short[]} values.
 *
 * @author Justin Albano
 */
public final class ShortArrayValue
        extends BaseWholeNumberArrayValue<short[], Short, ShortArrayValue> {

    private static final PrimitiveArrayOperations<Short, short[]> OPERATIONS =
            new PrimitiveArrayOperations<>(array -> array.length, (array, index) -> array[index]);

    /**
     * Creates a new {@code short[]} value.
     *
     * @param subject
     *         The desired subject.
     */
    public ShortArrayValue(final short @Nullable [] subject) {
        super(subject, "Short array value", OPERATIONS);
    }
}
