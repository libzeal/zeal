package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.sequence.BaseSequenceValue;

import java.util.Collection;

public class SimpleCollectionValue<T> extends BaseSequenceValue<Collection<T>, T, SimpleCollectionValue<T>> {

    public SimpleCollectionValue(final Collection<T> subject) {
        super(subject, "Collection value", new CollectionOperations<>());
    }
}
