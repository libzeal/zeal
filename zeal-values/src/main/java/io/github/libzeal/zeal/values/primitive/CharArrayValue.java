package io.github.libzeal.zeal.values.primitive;

import io.github.libzeal.zeal.values.sdk.sequence.BaseOrderedSequenceValue;
import org.jspecify.annotations.Nullable;

/**
 * A value used to evaluate {@code char[]} values.
 *
 * @author Justin Albano
 */
public final class CharArrayValue extends BaseOrderedSequenceValue<char[], Character, CharArrayValue> {

    private static final PrimitiveArrayOperations<Character, char[]> OPERATIONS =
            new PrimitiveArrayOperations<>(array -> array.length, (array, index) -> array[index]);

    /**
     * Creates a new {@code char[]} value.
     *
     * @param subject
     *         The desired subject.
     */
    public CharArrayValue(final char @Nullable [] subject) {
        super(subject, "Char array value", OPERATIONS);
    }
}
