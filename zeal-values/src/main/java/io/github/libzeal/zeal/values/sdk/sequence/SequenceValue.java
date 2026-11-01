package io.github.libzeal.zeal.values.sdk.sequence;

import java.util.Collection;

public interface SequenceValue<T, V extends SequenceValue<T, V>> {

    /**
     * Checks if the value is empty.
     *
     * @return This value (fluent interface).
     */
    V isEmpty();

    /**
     * Checks if the value is not empty.
     *
     * @return This value (fluent interface).
     */
    V isNotEmpty();

    /**
     * Checks if the value has the supplied length.
     *
     * @param length
     *         The desired length.
     *
     * @return This value (fluent interface).
     */
    V hasLengthOf(int length);

    /**
     * Checks if the value does not have the supplied length.
     *
     * @param length
     *         The undesired length.
     *
     * @return This value (fluent interface).
     */
    V doesNotHaveLengthOf(int length);

    /**
     * Checks if the value is longer than the supplied length.
     *
     * @param length
     *         The desired lower bound.
     *
     * @return This value (fluent interface).
     */
    V isLongerThan(int length);

    /**
     * Checks if the value is longer than or equal to the supplied length.
     *
     * @param length
     *         The desired lower bound.
     *
     * @return This value (fluent interface).
     */
    V isLongerThanOrEqualTo(int length);

    /**
     * Checks if the value is shorter than the supplied length.
     *
     * @param length
     *         The desired upper bound.
     *
     * @return This value (fluent interface).
     */
    V isShorterThan(int length);

    /**
     * Checks if the value is shorter than or equal to the supplied length.
     *
     * @param length
     *         The desired upper bound.
     *
     * @return This value (fluent interface).
     */
    V isShorterThanOrEqualTo(int length);

    /**
     * Checks that the value includes the supplied element.
     *
     * @param desired
     *         The desired element.
     *
     * @return This value (fluent interface).
     */
    V includes(T desired);

    /**
     * Checks that the value includes the supplied elements.
     *
     * @param desired
     *         The desired elements.
     *
     * @return This value (fluent interface).
     */
    V includesAll(Collection<T> desired);

    /**
     * Checks that the value includes the supplied elements.
     *
     * @param desired
     *         The desired elements.
     *
     * @return This value (fluent interface).
     */
    V includesAllOf(T... desired);

    /**
     * Checks that the value includes any of the supplied elements.
     *
     * @param desired
     *         The desired elements.
     *
     * @return This value (fluent interface).
     */
    V includesAny(Collection<T> desired);

    /**
     * Checks that the value includes any of the supplied elements.
     *
     * @param desired
     *         The desired elements.
     *
     * @return This value (fluent interface).
     */
    V includesAnyOf(T... desired);

    /**
     * Checks that the value excludes the supplied element.
     *
     * @param desired
     *         The desired element to exclude.
     *
     * @return This value (fluent interface).
     */
    V excludes(T desired);

    /**
     * Checks that the value excludes the supplied elements.
     *
     * @param desired
     *         The desired elements to exclude.
     *
     * @return This value (fluent interface).
     */
    V excludesAll(Collection<T> desired);

    /**
     * Checks that the value excludes the supplied elements.
     *
     * @param desired
     *         The desired elements to exclude.
     *
     * @return This value (fluent interface).
     */
    V excludesAllOf(T... desired);

    /**
     * Checks that the value excludes any of the supplied elements.
     *
     * @param desired
     *         The desired elements to exclude.
     *
     * @return This value (fluent interface).
     */
    V excludesAny(Collection<T> desired);

    /**
     * Checks that the value excludes any of the supplied elements.
     *
     * @param desired
     *         The desired elements to exclude.
     *
     * @return This value (fluent interface).
     */
    V excludesAnyOf(T... desired);

    /**
     * Checks that the desired element occurs the desired number of times.
     *
     * @param desired
     *         The desired element.
     * @param times
     *         The desired number of times the supplied element should occur.
     *
     * @return This value (fluent interface).
     */
    V includesExactly(T desired, long times);

    /**
     * Checks that the desired element occurs exactly once.
     *
     * @param desired
     *         The desired element.
     *
     * @return This value (fluent interface).
     */
    default V includesOnce(T desired) {
        return includesExactly(desired, 1);
    }

    /**
     * Checks that the desired element occurs more than the desired number of
     * times.
     *
     * @param desired
     *         The desired element.
     * @param times
     *         The minimum number of times, exclusively, the supplied element
     *         should occur.
     *
     * @return This value (fluent interface).
     */
    V includesMoreThan(T desired, long times);

    /**
     * Checks that the desired element occurs more than or equal the desired
     * number of times.
     *
     * @param desired
     *         The desired element.
     * @param times
     *         The minimum number of times, inclusively, the supplied element
     *         should occur.
     *
     * @return This value (fluent interface).
     */
    V includesMoreThanOrEqualTo(T desired, long times);

    /**
     * Checks that the desired element occurs less than the desired number of
     * times.
     *
     * @param desired
     *         The desired element.
     * @param times
     *         The maximum number of times, exclusively, the supplied element
     *         should occur.
     *
     * @return This value (fluent interface).
     */
    V includesLessThan(T desired, long times);

    /**
     * Checks that the desired element occurs less than or equal the desired
     * number of times.
     *
     * @param desired
     *         The desired element.
     * @param times
     *         The maximum number of times, inclusively, the supplied element
     *         should occur.
     *
     * @return This value (fluent interface).
     */
    V includesLessThanOrEqualTo(T desired, long times);
}
