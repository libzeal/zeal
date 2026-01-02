package io.github.libzeal.zeal.values.collection;

import java.util.Collection;

public class CollectionValue<T> extends BaseCollectionValue<T, Collection<T>, CollectionValue<T>> {

    public CollectionValue(final Collection<T> subject) {
        super(subject, "Collection value");
    }
}
