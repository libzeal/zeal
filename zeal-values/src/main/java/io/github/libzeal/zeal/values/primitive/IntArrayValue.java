package io.github.libzeal.zeal.values.primitive;

import org.jspecify.annotations.Nullable;

/**
 * A value used to evaluate {@code int[]} values.
 *
 * @author Justin Albano
 */
public final class IntArrayValue extends BaseWholeNumberArrayValue<int[], Integer, IntArrayValue> {

    private static final PrimitiveArrayOperations<Integer, int[]> OPERATIONS =
            new PrimitiveArrayOperations<>(array -> array.length, (array, index) -> array[index]);

    /**
     * Creates a new {@code int[]} value.
     *
     * @param subject
     *         The desired subject.
     */
    public IntArrayValue(final int @Nullable [] subject) {
        super(subject, "Int array value", OPERATIONS);
    }
}
