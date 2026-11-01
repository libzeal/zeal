package io.github.libzeal.zeal.values.sdk.sequence;

public interface OrderedSequenceOperations<N, H> extends SequenceOperations<N, H> {

    Element<N> lastElement(H haystack, N desired);

    Element<N> atIndex(H haystack, int index, N desired);

    int indexOf(H haystack, N needle);

    int lastIndexOf(H haystack, N needle);

    default Element<N> firstElement(final H haystack, final N desired) {
        return atIndex(haystack, 0, desired);
    }
}
