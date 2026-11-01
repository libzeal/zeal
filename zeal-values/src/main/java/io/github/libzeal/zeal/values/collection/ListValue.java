package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.sequence.BaseOrderedSequenceValue;

import java.util.List;

/**
 * A value used to evaluate {@link List}s.
 *
 * @param <T>
 *         The type of the elements in the {@link List}.
 *
 * @author Justin Albano
 */
public class ListValue<T> extends BaseOrderedSequenceValue<List<T>, T, ListValue<T>> {

    /**
     * Creates a new list value with the supplied subject.
     *
     * @param subject
     *         The desired subject.
     */
    public ListValue(final List<T> subject) {
        super(subject, "List value", new ListOperations<>());
    }
}
