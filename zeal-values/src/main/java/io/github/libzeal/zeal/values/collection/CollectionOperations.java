package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.sequence.SequenceOperations;

import java.util.Collection;
import java.util.Collections;

class CollectionOperations<T, C extends Collection<T>> implements SequenceOperations<T, C> {

    @Override
    public int size(final C haystack) {
        return haystack.size();
    }

    @Override
    public boolean includes(final C haystack, final T needle) {
        return haystack.contains(needle);
    }

    @Override
    public int occurrences(final C haystack, final T needle) {
        return Collections.frequency(haystack, needle);
    }
}
