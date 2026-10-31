package io.github.libzeal.zeal.values.sdk.sequence;

/**
 * A value representing a sequence of ordered elements.
 *
 * @param <T>
 *     The type of the elements in the sequence.
 * @param <V>
 *     The type of the value. This type is used to maintain the correct return
 *     type for fluent interfaces, allowing for method chaining.
 *
 * @author Justin Albano
 */
public interface OrderedSequenceValue<T, V extends OrderedSequenceValue<T, V>> extends SequenceValue<T, V> {

    /**
     * Checks that the desired element occurs at the desired index.
     *
     * @param desired
     *     The desired element.
     * @param index
     *     The desired index.
     *
     * @return The value to allow for method chaining.
     */
    V hasAtIndex(T desired, int index);

    /**
     * Checks that the desired element does not occur at the desired index.
     *
     * @param desired
     *     The desired element.
     * @param index
     *     The index where the desired element should not occur.
     *
     * @return The value to allow for method chaining.
     */
    V doesNotHaveAtIndex(T desired, int index);

    /**
     * Checks that the last occurrence of the desired element is at the desired index.
     *
     * @param desired
     *     The desired element.
     * @param index
     *     The expected index of the last occurrence of the desired element. An index of {@code -1} checks that the
     *     desired element does not occur in the sequence.
     *
     * @return The value to allow for method chaining.
     */
    V lastIndexOfIs(T desired, int index);

    /**
     * Checks that the last occurrence of the desired element is not at the desired index.
     *
     * @param desired
     *     The desired element.
     * @param index
     *     The index that the last occurrence of the desired element should not be at.
     *
     * @return The value to allow for method chaining.
     */
    V lastIndexOfIsNot(T desired, int index);

    V startsWith(T desired);
    V doesNotStartWith(T desired);
    V endsWith(T desired);
    V doesNotEndWith(T desired);
}
