package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.collection.BaseCollectionValue.CollectionSequenceOperations;

import java.util.Collection;

public abstract class BaseCollectionValue<
        T,
        C extends Collection<T>,
    O extends CollectionSequenceOperations<T, C>,
        V extends BaseCollectionValue<T, C, O, V>
    >
        extends BaseIterableValue<T, C, O, V> {

    protected BaseCollectionValue(final C subject, final String name, final O ops) {
        super(subject, name, ops);
    }

    protected interface CollectionSequenceOperations<T, I extends Collection<T>>
        extends IterableSequenceOperations<T, I> {
    }
}
