package io.github.libzeal.zeal.values.primitive;

import org.jspecify.annotations.Nullable;

/**
 * A value used to evaluate {@code long[]} values.
 *
 * @author Justin Albano
 */
public final class LongArrayValue extends BaseWholeNumberArrayValue<long[], Long, LongArrayValue> {

    private static final PrimitiveArrayOperations<Long, long[]> OPERATIONS =
            new PrimitiveArrayOperations<>(array -> array.length, (array, index) -> array[index]);

    /**
     * Creates a new {@code long[]} value.
     *
     * @param subject
     *         The desired subject.
     */
    public LongArrayValue(final long @Nullable [] subject) {
        super(subject, "Long array value", OPERATIONS);
    }
}
