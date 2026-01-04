package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.collection.BaseCollectionValue.CollectionSequenceOperations;

import java.util.Set;

public class SetValue<T> extends BaseCollectionValue<T, Set<T>, CollectionSequenceOperations<T, Set<T>>, SetValue<T>> {

    public SetValue(final Set<T> subject) {
        super(subject, "Set value", new CollectionSequenceOperations<>());
    }
}
