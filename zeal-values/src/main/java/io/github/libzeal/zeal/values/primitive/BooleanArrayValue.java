package io.github.libzeal.zeal.values.primitive;

import io.github.libzeal.zeal.values.sdk.sequence.BaseOrderedSequenceValue;
import org.jspecify.annotations.Nullable;

/**
 * A value used to evaluate {@code boolean[]} values.
 *
 * @author Justin Albano
 */
public final class BooleanArrayValue
        extends BaseOrderedSequenceValue<boolean[], Boolean, BooleanArrayValue> {

    private static final PrimitiveArrayOperations<Boolean, boolean[]> OPERATIONS =
            new PrimitiveArrayOperations<>(array -> array.length, (array, index) -> array[index]);

    /**
     * Creates a new {@code boolean[]} value.
     *
     * @param subject
     *         The desired subject.
     */
    public BooleanArrayValue(final boolean @Nullable [] subject) {
        super(subject, "Boolean array value", OPERATIONS);
    }
}
