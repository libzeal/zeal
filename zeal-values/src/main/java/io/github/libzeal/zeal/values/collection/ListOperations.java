package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.sequence.Element;
import io.github.libzeal.zeal.values.sdk.sequence.OrderedSequenceExpressions.OrderedSequenceOperations;

import java.util.List;

final class ListOperations<T> extends CollectionOperations<T, List<T>>
    implements OrderedSequenceOperations<T, List<T>> {

    @Override
    public Element<T> lastElement(final List<T> haystack, final T desired) {

        if (haystack.isEmpty()) {
            return Element.missing();
        }
        else {
            return Element.found(haystack.getLast());
        }
    }

    @Override
    public Element<T> atIndex(final List<T> haystack, final int index, final T desired) {

        if (index >= 0 && index < haystack.size()) {
            return Element.found(haystack.get(index));
        }
        else {
            return Element.missing();
        }
    }

    @Override
    public int indexOf(final List<T> haystack, final T needle) {
        return haystack.indexOf(needle);
    }

    @Override
    public int lastIndexOf(final List<T> haystack, final T needle) {
        return haystack.lastIndexOf(needle);
    }
}
