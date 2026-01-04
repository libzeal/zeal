package io.github.libzeal.zeal.values.api.sequence;

import io.github.libzeal.zeal.values.api.BaseObjectValue;

/**
 * A value representing a sequence of ordered elements.
 *
 * @param <T>
 *     The type of the elements in the sequence.
 * @param <S>
 *     The type of the sequence.
 * @param <V>
 *     The type of the value. This type is used to maintain the correct return
 *     type for fluent interfaces, allowing for method chaining.
 *
 * @author Justin Albano
 */
public interface OrderedSequenceValue<T, S, V extends BaseObjectValue<S, V>>
    extends SequenceValue<T, S, V> {

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

    V startsWith(T desired);
    V doesNotStartWith(T desired);
    V endsWith(T desired);
    V doesNotEndWith(T desired);
}
