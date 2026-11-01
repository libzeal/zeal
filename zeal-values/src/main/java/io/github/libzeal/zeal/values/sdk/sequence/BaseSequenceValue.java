package io.github.libzeal.zeal.values.sdk.sequence;

import io.github.libzeal.zeal.values.sdk.BaseValue;

import java.util.Arrays;
import java.util.Collection;

/**
 * A base class used by sequences to include common conditions.
 *
 * @param <S>
 *         The type of the sequence.
 * @param <T>
 *         The type of the elements of the sequence.
 * @param <V>
 *         The value of the implementing value (used for fluent interfaces).
 *
 * @author Justin Albano
 */
public abstract class BaseSequenceValue<S, T, V extends BaseSequenceValue<S, T, V>>
        extends BaseValue<S, V>
        implements SequenceValue<T, V> {

    private final SequenceOperations<T, S> operations;

    protected BaseSequenceValue(final S subject, final String name, final SequenceOperations<T, S> operations) {
        super(subject, name);
        this.operations = operations;
    }

    @Override
    public V isEmpty() {
        return append(
                SequenceConditions.isEmpty(operations)
        );
    }

    @Override
    public V isNotEmpty() {
        return append(
                SequenceConditions.isNotEmpty(operations)
        );
    }

    @Override
    public V hasLengthOf(final int length) {
        return append(
                SequenceConditions.hasLengthOf(length, operations)
        );
    }

    @Override
    public V doesNotHaveLengthOf(final int length) {
        return append(
                SequenceConditions.doesNotHaveLengthOf(length, operations)
        );
    }

    @Override
    public V isLongerThan(final int length) {
        return append(
                SequenceConditions.isLongerThan(length, operations)
        );
    }

    @Override
    public V isLongerThanOrEqualTo(final int length) {
        return append(
                SequenceConditions.isLongerThanOrEqualTo(length, operations)
        );
    }

    @Override
    public V isShorterThan(final int length) {
        return append(
                SequenceConditions.isShorterThan(length, operations)
        );
    }

    @Override
    public V isShorterThanOrEqualTo(final int length) {
        return append(
                SequenceConditions.isShorterThanOrEqualTo(length, operations)
        );
    }

    @Override
    public V includes(final T desired) {
        return append(
                SequenceConditions.includes(desired, operations)
        );
    }

    @Override
    public V includesAll(final Collection<? extends T> desired) {
        return append(
                SequenceConditions.includesAll(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final V includesAllOf(final T... desired) {
        return includesAll(Arrays.asList(desired));
    }

    @Override
    public V includesAny(final Collection<? extends T> desired) {
        return append(
                SequenceConditions.includesAny(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final V includesAnyOf(final T... desired) {
        return includesAny(Arrays.asList(desired));
    }

    @Override
    public V excludes(final T desired) {
        return append(
                SequenceConditions.excludes(desired, operations)
        );
    }

    @Override
    public V excludesAll(final Collection<? extends T> desired) {
        return append(
                SequenceConditions.excludesAll(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final V excludesAllOf(final T... desired) {
        return excludesAll(Arrays.asList(desired));
    }

    @Override
    public V excludesAny(final Collection<? extends T> desired) {
        return append(
                SequenceConditions.excludesAny(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final V excludesAnyOf(final T... desired) {
        return excludesAny(Arrays.asList(desired));
    }

    @Override
    public V includesExactly(final T desired, final long times) {
        return append(
                SequenceConditions.includesExactly(desired, times, operations)
        );
    }

    @Override
    public V includesMoreThan(final T desired, final long times) {
        return append(
                SequenceConditions.includesMoreThan(desired, times, operations)
        );
    }

    @Override
    public V includesMoreThanOrEqualTo(final T desired, final long times) {
        return append(
                SequenceConditions.includesMoreThanOrEqualTo(desired, times, operations)
        );
    }

    @Override
    public V includesLessThan(final T desired, final long times) {
        return append(
                SequenceConditions.includesLessThan(desired, times, operations)
        );
    }

    @Override
    public V includesLessThanOrEqualTo(final T desired, final long times) {
        return append(
                SequenceConditions.includesLessThanOrEqualTo(desired, times, operations)
        );
    }
}
