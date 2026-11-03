package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.sequence.BaseOrderedSequenceValue;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * A value used to evaluate {@link List}s.
 *
 * @param <T>
 *         The type of the elements in the {@link List}.
 *
 * @author Justin Albano
 */
public class ListValue<T extends @Nullable Object> extends BaseOrderedSequenceValue<List<T>, T, ListValue<T>> {

    /**
     * Creates a new list value with the supplied subject.
     *
     * @param subject
     *         The desired subject.
     */
    public ListValue(final @Nullable List<T> subject) {
        super(subject, "List value", new ListOperations<>());
    }
}
