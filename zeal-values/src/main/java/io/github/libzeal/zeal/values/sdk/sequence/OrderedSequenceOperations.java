package io.github.libzeal.zeal.values.sdk.sequence;

import io.github.libzeal.zeal.logic.unary.Observed;

/**
 * A common set of operations for ordered sequences. These operations are used
 * by {@link BaseOrderedSequenceValue} or third party implementations to perform
 * common operations.
 *
 * @param <N>
 *         The type of the needle.
 * @param <H>
 *         The type of the haystack (sequence).
 *
 * @author Justin Albano
 */
public interface OrderedSequenceOperations<N, H> extends SequenceOperations<N, H> {

    /**
     * Obtains the last element of the haystack.
     *
     * @param haystack
     *         The haystack to find the last element of.
     * @param desired
     *         The desired element. This needle is supplied to allow
     *         implementations to perform optimizations. For example, if the
     *         needle is longer than the haystack (as is possible when searching
     *         for a string in another string), implementations can immediately
     *         return without searching for the needle.
     *
     * @return The last element of the haystack.
     */
    Observed<N> lastElement(H haystack, N desired);

    /**
     * Obtains the element in the haystack at the supplied index.
     *
     * @param haystack
     *         The haystack to find the last element of.
     * @param index
     *         The index of desired element.
     * @param desired
     *         The desired element. This needle is supplied to allow
     *         implementations to perform optimizations.
     *
     * @return The element in the haystack at the supplied index.
     */
    Observed<N> atIndex(H haystack, int index, N desired);

    /**
     * Obtains the index of the supplied needle in the supplied haystack.
     *
     * @param haystack
     *         The haystack to search.
     * @param needle
     *         The needle to find.
     *
     * @return The index of the supplied needle in the supplied haystack. If the
     * needle is not found, this method should return {@code -1}.
     */
    int indexOf(H haystack, N needle);

    /**
     * Obtains the last index of the supplied needle in the supplied haystack.
     *
     * @param haystack
     *         The haystack to search.
     * @param needle
     *         The needle to find.
     *
     * @return The last index of the supplied needle in the supplied haystack.
     * If the needle is not found, this method should return {@code -1}.
     */
    int lastIndexOf(H haystack, N needle);

    /**
     * Obtains the first element of the haystack.
     *
     * @param haystack
     *         The haystack to find the first element of.
     * @param desired
     *         The desired element. This needle is supplied to allow
     *         implementations to perform optimizations. For example, if the
     *         needle is longer than the haystack (as is possible when searching
     *         for a string in another string), implementations can immediately
     *         return without searching for the needle.
     *
     * @return The first element of the haystack.
     */
    default Observed<N> firstElement(final H haystack, final N desired) {
        return atIndex(haystack, 0, desired);
    }
}
