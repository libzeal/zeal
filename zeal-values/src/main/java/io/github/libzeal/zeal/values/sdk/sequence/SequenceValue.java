package io.github.libzeal.zeal.values.sdk.sequence;

import org.jspecify.annotations.Nullable;

import java.util.Collection;

/**
 * A value that represents a sequence of elements. This is not limited to
 * collection-based values; string-based values and third-party values may also
 * implement this interface.
 *
 * @param <T>
 *         The type of the element.
 * @param <V>
 *         The type of the implementing value (used for fluent interfaces).
 *
 * @author Justin Albano
 */
public interface SequenceValue<T extends @Nullable Object, V extends SequenceValue<T, V>> {

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
    V includes(@Nullable T desired);

    /**
     * Checks that the value includes the supplied elements.
     *
     * @param desired
     *         The desired elements.
     *
     * @return This value (fluent interface).
     */
    V includesAll(@Nullable Collection<? extends @Nullable T> desired);

    /**
     * Checks that the value includes the supplied elements.
     *
     * @param desired
     *         The desired elements.
     *
     * @return This value (fluent interface).
     */
    @SuppressWarnings("unchecked")
    V includesAllOf(@Nullable T... desired);

    /**
     * Checks that the value includes any of the supplied elements.
     *
     * @param desired
     *         The desired elements.
     *
     * @return This value (fluent interface).
     */
    V includesAny(@Nullable Collection<? extends @Nullable T> desired);

    /**
     * Checks that the value includes any of the supplied elements.
     *
     * @param desired
     *         The desired elements.
     *
     * @return This value (fluent interface).
     */
    @SuppressWarnings("unchecked")
    V includesAnyOf(@Nullable T... desired);

    /**
     * Checks that the value excludes the supplied element.
     *
     * @param desired
     *         The desired element to exclude.
     *
     * @return This value (fluent interface).
     */
    V excludes(@Nullable T desired);

    /**
     * Checks that the value excludes the supplied elements.
     *
     * @param desired
     *         The desired elements to exclude.
     *
     * @return This value (fluent interface).
     */
    V excludesAll(@Nullable Collection<? extends @Nullable T> desired);

    /**
     * Checks that the value excludes the supplied elements.
     *
     * @param desired
     *         The desired elements to exclude.
     *
     * @return This value (fluent interface).
     */
    @SuppressWarnings("unchecked")
    V excludesAllOf(@Nullable T... desired);

    /**
     * Checks that the value excludes any of the supplied elements.
     *
     * @param desired
     *         The desired elements to exclude.
     *
     * @return This value (fluent interface).
     */
    V excludesAny(@Nullable Collection<? extends @Nullable T> desired);

    /**
     * Checks that the value excludes any of the supplied elements.
     *
     * @param desired
     *         The desired elements to exclude.
     *
     * @return This value (fluent interface).
     */
    @SuppressWarnings("unchecked")
    V excludesAnyOf(@Nullable T... desired);

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
    V includesExactly(@Nullable T desired, long times);

    /**
     * Checks that the desired element occurs exactly once.
     *
     * @param desired
     *         The desired element.
     *
     * @return This value (fluent interface).
     */
    default V includesOnce(@Nullable T desired) {
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
    V includesMoreThan(@Nullable T desired, long times);

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
    V includesMoreThanOrEqualTo(@Nullable T desired, long times);

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
    V includesLessThan(@Nullable T desired, long times);

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
    V includesLessThanOrEqualTo(@Nullable T desired, long times);
}
