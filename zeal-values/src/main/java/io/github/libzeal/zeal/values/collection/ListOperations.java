package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.sequence.Element;
import io.github.libzeal.zeal.values.sdk.sequence.OrderedSequenceOperations;

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

        try {
            return haystack.indexOf(needle);
        }
        catch (final NullPointerException e) {
            return handleNullNeedle(needle, e);
        }
    }

    private static <T> int handleNullNeedle(final T needle, final NullPointerException e) {
        // Some collections do not permit null elements and may throw when a
        // null needle is used. This section catches this exception and if
        // the needle is null (the cause of the NPE), the needle is
        // considered "not found."

        if (needle == null) {
            return -1;
        }

        throw e;
    }

    @Override
    public int lastIndexOf(final List<T> haystack, final T needle) {

        try {
            return haystack.lastIndexOf(needle);
        }
        catch (final NullPointerException e) {
            return handleNullNeedle(needle, e);
        }
    }
}
