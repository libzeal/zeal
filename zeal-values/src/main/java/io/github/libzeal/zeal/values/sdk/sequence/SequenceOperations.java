package io.github.libzeal.zeal.values.sdk.sequence;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * A common set of operations for all sequences. These operations are used by
 * {@link BaseSequenceValue} or third party implementations to perform common
 * operations.
 *
 * @param <T>
 *         The type of the elements in the sequence.
 * @param <S>
 *         The type of the sequence. This type is also the type of the subject
 *         of the value and the type of the haystack when searching.
 *
 * @author Justin Albano
 */
public interface SequenceOperations<T, S> {

    /**
     * Obtains the size of the supplied haystack.
     *
     * @param haystack
     *         The sequence to measure the size of.
     *
     * @return The size of the supplied haystack.
     */
    int size(S haystack);

    /**
     * Checks if the supplied needle is included in the supplied haystack.
     *
     * @param haystack
     *         The sequence to search.
     * @param needle
     *         The desired element.
     *
     * @return True if the supplied needle is found in the supplied haystack;
     * false otherwise.
     */
    boolean includes(S haystack, T needle);

    /**
     * Finds the number of occurrences of the supplied needle in the supplied
     * haystack.
     *
     * @param haystack
     *         The haystack to search.
     * @param needle
     *         The desired element.
     *
     * @return The number of occurrences of the supplied needle in the supplied
     * haystack.
     */
    int occurrences(S haystack, T needle);

    /**
     * Obtains a list of the supplied needles that were found in the supplied
     * haystack. This resulting list is a subset (or matching) set to the
     * needles provided.
     *
     * @param haystack
     *         The sequence to search.
     * @param needles
     *         The desired elements.
     *
     * @return A list containing the supplied needles that were found in the
     * supplied haystack.
     */
    default List<T> findAllIn(final S haystack, final Collection<? extends T> needles) {
        return needles.stream()
                .filter(needle -> includes(haystack, needle))
                .collect(toList());
    }
}
