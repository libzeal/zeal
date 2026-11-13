package io.github.libzeal.zeal.values.sdk.sequence;

/**
 * A value with a finite size.
 *
 * @param <V>
 *         The type of the implementing value (used for fluent interfaces).
 *
 * @author Justin Albano
 */
public interface SizedValue<V extends SizedValue<V>> {

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
     * Checks if the value has the supplied size.
     *
     * @param size
     *         The desired size.
     *
     * @return This value (fluent interface).
     */
    V hasSizeOf(int size);

    /**
     * Checks if the value does not have the supplied size.
     *
     * @param size
     *         The undesired size.
     *
     * @return This value (fluent interface).
     */
    V doesNotHaveSizeOf(int size);

    /**
     * Checks if the value is longer than the supplied size.
     *
     * @param size
     *         The desired lower bound.
     *
     * @return This value (fluent interface).
     */
    V isLongerThan(int size);

    /**
     * Checks if the value is longer than or equal to the supplied size.
     *
     * @param size
     *         The desired lower bound.
     *
     * @return This value (fluent interface).
     */
    V isLongerThanOrEqualTo(int size);

    /**
     * Checks if the value is shorter than the supplied size.
     *
     * @param size
     *         The desired upper bound.
     *
     * @return This value (fluent interface).
     */
    V isShorterThan(int size);

    /**
     * Checks if the value is shorter than or equal to the supplied size.
     *
     * @param size
     *         The desired upper bound.
     *
     * @return This value (fluent interface).
     */
    V isShorterThanOrEqualTo(int size);
}
