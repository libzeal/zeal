package io.github.libzeal.zeal.values.api.sequence;

import io.github.libzeal.zeal.values.api.BaseObjectValue;

import java.util.List;
import java.util.Set;

/**
 * A value representing a sequence of elements that can be repeated in the
 * sequence. {@link List} and {@link String} are examples of a repeatable
 * sequence, while {@link Set} is a sequence that is not repeatable.
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
public interface RepeatableSequenceValue<T, S, V extends BaseObjectValue<S, V>>
    extends SequenceValue<T, S, V> {

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
    default V includesMoreThan(T desired, int times) {
        return includesMoreThan(desired, (long) times);
    }

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
    default V includesMoreThanOrEqualTo(T desired, int times) {
        return includesMoreThanOrEqualTo(desired, (long) times);
    }

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
    default V includesLessThan(T desired, int times) {
        return includesLessThan(desired, (long) times);
    }

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
    default V includesLessThanOrEqualTo(T desired, int times) {
        return includesLessThanOrEqualTo(desired, (long) times);
    }
}
