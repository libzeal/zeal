package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.sequence.BaseOrderedSequenceValue;

import java.util.List;

public class ListValue<T> extends BaseOrderedSequenceValue<List<T>, T, ListValue<T>> {

    public ListValue(final List<T> subject) {
        super(subject, "List value", new ListOperations<>());
    }
}
