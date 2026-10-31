package io.github.libzeal.zeal.values.sdk.sequence;

import java.util.Collection;

public interface SequenceValue<T, V extends SequenceValue<T, V>> {

    V isEmpty();
    V isNotEmpty();
    V hasLengthOf(int length);
    V doesNotHaveLengthOf(int length);
    V isLongerThan(int length);
    V isLongerThanOrEqualTo(int length);
    V isShorterThan(int length);
    V isShorterThanOrEqualTo(int length);

    V includes(T desired);
    V includesAll(Collection<T> desired);
    V includesAllOf(T... desired);
    V includesAny(Collection<T> desired);
    V includesAnyOf(T... desired);
    V excludes(T desired);
    V excludesAll(Collection<T> desired);
    V excludesAllOf(T... desired);
    V excludesAny(Collection<T> desired);
    V excludesAnyOf(T... desired);

    /**
     * Checks that the desired element occurs the desired number of times in the
     * sequence.
     *
     * @param desired
     *     The desired element.
     * @param times
     *     The desired number of times the supplied element should occur.
     *
     * @return The value to allow for method chaining.
     */
    V includesExactly(T desired, long times);

    /**
     * Checks that the desired element occurs exactly once in the sequence.
     *
     * @param desired
     *     The desired element.
     *
     * @return The value to allow for method chaining.
     */
    default V includesOnce(T desired) {
        return includesExactly(desired, 1);
    }

    /**
     * Checks that the desired element occurs more than the desired number of
     * times in the sequence.
     *
     * @param desired
     *     The desired element.
     * @param times
     *     The minimum number of times, exclusively, the supplied element should
     *     occur.
     *
     * @return The value to allow for method chaining.
     */
    V includesMoreThan(T desired, long times);

    /**
     * Checks that the desired element occurs more than or equal the desired
     * number of times in the sequence.
     *
     * @param desired
     *     The desired element.
     * @param times
     *     The minimum number of times, inclusively, the supplied element should
     *     occur.
     *
     * @return The value to allow for method chaining.
     */
    V includesMoreThanOrEqualTo(T desired, long times);

    /**
     * Checks that the desired element occurs less than the desired number of
     * times in the sequence.
     *
     * @param desired
     *     The desired element.
     * @param times
     *     The maximum number of times, exclusively, the supplied element should
     *     occur.
     *
     * @return The value to allow for method chaining.
     */
    V includesLessThan(T desired, long times);

    /**
     * Checks that the desired element occurs less than or equal the desired
     * number of times in the sequence.
     *
     * @param desired
     *     The desired element.
     * @param times
     *     The maximum number of times, inclusively, the supplied element should
     *     occur.
     *
     * @return The value to allow for method chaining.
     */
    V includesLessThanOrEqualTo(T desired, long times);
}
