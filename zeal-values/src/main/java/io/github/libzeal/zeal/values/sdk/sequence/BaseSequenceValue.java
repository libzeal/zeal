package io.github.libzeal.zeal.values.sdk.sequence;

import io.github.libzeal.zeal.values.sdk.BaseValue;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

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
public abstract class BaseSequenceValue<S extends @Nullable Object, T, V extends BaseSequenceValue<S, T, V>>
        extends BaseValue<S, V>
        implements SequenceValue<T, V> {

    private final SequenceOperations<T, S> operations;

    /**
     * Creates a base sequence value.
     *
     * @param subject
     *         The subject of the value.
     * @param name
     *         The name of the value.
     * @param operations
     *         The operations to use in the value.
     *
     * @throws NullPointerException
     *         The name or operations are {@code null}.
     */
    protected BaseSequenceValue(final @Nullable S subject, final String name, final SequenceOperations<T, S> operations) {
        super(subject, name);
        this.operations = requireNonNull(operations);
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
    public V includes(final @Nullable T desired) {
        return append(
                SequenceConditions.includes(desired, operations)
        );
    }

    @Override
    public V includesAll(final @Nullable Collection<? extends @Nullable T> desired) {
        return append(
                SequenceConditions.includesAll(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final V includesAllOf(final @Nullable T... desired) {
        return includesAll(Arrays.asList(desired));
    }

    @Override
    public V includesAny(final @Nullable Collection<? extends @Nullable T> desired) {
        return append(
                SequenceConditions.includesAny(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final V includesAnyOf(final @Nullable T... desired) {
        return includesAny(Arrays.asList(desired));
    }

    @Override
    public V excludes(final @Nullable T desired) {
        return append(
                SequenceConditions.excludes(desired, operations)
        );
    }

    @Override
    public V excludesAll(final @Nullable Collection<? extends @Nullable T> desired) {
        return append(
                SequenceConditions.excludesAll(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final V excludesAllOf(final @Nullable T... desired) {
        return excludesAll(Arrays.asList(desired));
    }

    @Override
    public V excludesAny(final @Nullable Collection<? extends @Nullable T> desired) {
        return append(
                SequenceConditions.excludesAny(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final V excludesAnyOf(final @Nullable T... desired) {
        return excludesAny(Arrays.asList(desired));
    }

    @Override
    public V includesExactly(final @Nullable T desired, final long times) {
        return append(
                SequenceConditions.includesExactly(desired, times, operations)
        );
    }

    @Override
    public V includesMoreThan(final @Nullable T desired, final long times) {
        return append(
                SequenceConditions.includesMoreThan(desired, times, operations)
        );
    }

    @Override
    public V includesMoreThanOrEqualTo(final @Nullable T desired, final long times) {
        return append(
                SequenceConditions.includesMoreThanOrEqualTo(desired, times, operations)
        );
    }

    @Override
    public V includesLessThan(final @Nullable T desired, final long times) {
        return append(
                SequenceConditions.includesLessThan(desired, times, operations)
        );
    }

    @Override
    public V includesLessThanOrEqualTo(final @Nullable T desired, final long times) {
        return append(
                SequenceConditions.includesLessThanOrEqualTo(desired, times, operations)
        );
    }
}
