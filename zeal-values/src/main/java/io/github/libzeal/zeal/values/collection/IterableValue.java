package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.collection.BaseIterableValue.IterableSequenceOperations;

public final class IterableValue<T> extends
        BaseIterableValue<T, Iterable<T>, IterableSequenceOperations<T, Iterable<T>>, IterableValue<T>> {

    public IterableValue(final Iterable<T> subject) {
        super(subject, "Iterable value", new IterableSequenceOperations<>());
    }
}
