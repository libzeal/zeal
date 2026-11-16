package io.github.libzeal.zeal.values.sdk.sequence;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

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
public interface SequenceOperations<T extends @Nullable Object, S> {

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
    boolean includes(S haystack, @Nullable T needle);

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
    int occurrences(S haystack, @Nullable T needle);

    /**
     * Finds the elements of the supplied haystack that satisfy the supplied
     * predicate. The matching elements are returned in the iteration order of
     * the haystack (if the haystack has an order).
     * <p>
     * The supplied limit is an optimization hint: Implementations may stop
     * searching once the number of matching elements reaches the supplied
     * limit, but are not required to. Therefore, callers must not assume that
     * the returned list contains no more than the supplied number of elements.
     * If an implementation does honor the limit, the returned list must contain
     * the first matching elements, up to the supplied limit.
     * <p>
     * Callers should supply a positive limit. A limit of zero (or less) would
     * allow an implementation to return an empty list, regardless of whether
     * any element satisfies the predicate.
     *
     * @param haystack
     *         The sequence to search.
     * @param predicate
     *         The predicate that the matching elements should satisfy.
     * @param limit
     *         The number of matching elements after which the search may stop.
     *         This is supplied as an optimization that implementations
     *         <em>may</em> honor.
     *
     * @return A list containing the elements of the supplied haystack that
     * satisfy the supplied predicate. This list may be truncated at, or after,
     * the supplied limit.
     */
    List<T> findAllSatisfying(S haystack, Predicate<? super T> predicate, int limit);

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
    default List<@Nullable T> findAllIn(final S haystack, final Collection<? extends @Nullable T> needles) {

        final List<@Nullable T> found = new ArrayList<>();

        for (final @Nullable T needle : needles) {
            if (includes(haystack, needle)) {
                found.add(needle);
            }
        }

        return found;
    }
}
