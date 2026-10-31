package io.github.libzeal.zeal.values.sdk.sequence;

public interface OrderedSequenceOperations<T, S> extends SequenceOperations<T, S> {

    Element<T> lastElement(S haystack, T desired);

    Element<T> atIndex(S haystack, int index, T desired);

    int indexOf(S haystack, T needle);

    int lastIndexOf(S haystack, T needle);

    default Element<T> firstElement(final S haystack, final T desired) {
        return atIndex(haystack, 0, desired);
    }
}
