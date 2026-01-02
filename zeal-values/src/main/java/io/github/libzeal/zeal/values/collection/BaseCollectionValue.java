package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.api.CommonRationale;
import io.github.libzeal.zeal.values.api.cache.SequenceCaches;
import io.github.libzeal.zeal.values.api.cache.SimpleCacheResult;

import java.util.Collection;

public abstract class BaseCollectionValue<T, C extends Collection<T>, E extends BaseCollectionValue<T, C, E>>
        extends BaseIterableValue<T, C, E> {

    protected BaseCollectionValue(final C subject, final String name) {
        super(subject, name);
    }

    @Override
    protected long findSize(final C subject) {
        return subject.size();
    }

    @Override
    protected boolean findIsEmpty(final C subject) {
        return subject.isEmpty();
    }
}
