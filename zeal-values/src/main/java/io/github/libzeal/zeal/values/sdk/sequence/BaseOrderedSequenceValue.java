package io.github.libzeal.zeal.values.sdk.sequence;

import org.jspecify.annotations.Nullable;

import static java.util.Objects.requireNonNull;

/**
 * A base class used by ordered sequences to include common conditions.
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
public abstract class BaseOrderedSequenceValue<S extends @Nullable Object, T, V extends BaseOrderedSequenceValue<S, T, V>>
        extends BaseSequenceValue<S, T, V>
        implements OrderedSequenceValue<T, V> {

    private final OrderedSequenceOperations<T, S> operations;

    /**
     * Creates a base ordered sequence value.
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
    protected BaseOrderedSequenceValue(final @Nullable S subject, final String name, final OrderedSequenceOperations<T, S> operations) {
        super(subject, name, operations);
        this.operations = requireNonNull(operations);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Ordered sequence override the default behavior provided by general
     * sequences and include a hint that specifies the index that the desired
     * element was found at if the desired element is found.
     */
    @Override
    public V includes(final @Nullable T desired) {
        return append(
                OrderedSequenceConditions.includes(desired, operations)
        );
    }

    /**
     * {@inheritDoc}
     * <p>
     * Ordered sequence override the default behavior provided by general
     * sequences and include a hint that specifies the index that the desired
     * element was found at if the desired element is found.
     */
    @Override
    public V excludes(final @Nullable T desired) {
        return append(
                OrderedSequenceConditions.excludes(desired, operations)
        );
    }

    @Override
    public V hasAtIndex(final @Nullable T desired, final int index) {
        return append(
                OrderedSequenceConditions.hasAtIndex(desired, index, operations)
        );
    }

    @Override
    public V doesNotHaveAtIndex(final @Nullable T desired, final int index) {
        return append(
                OrderedSequenceConditions.doesNotHaveAtIndex(desired, index, operations)
        );
    }

    @Override
    public V lastIndexOfIs(final @Nullable T desired, final int index) {
        return append(
                OrderedSequenceConditions.lastIndexOfIs(desired, index, operations)
        );
    }

    @Override
    public V lastIndexOfIsNot(final @Nullable T desired, final int index) {
        return append(
                OrderedSequenceConditions.lastIndexOfIsNot(desired, index, operations)
        );
    }

    @Override
    public V startsWith(final @Nullable T desired) {
        return append(
                OrderedSequenceConditions.startsWith(desired, operations)
        );
    }

    @Override
    public V doesNotStartWith(final @Nullable T desired) {
        return append(
                OrderedSequenceConditions.doesNotStartWith(desired, operations)
        );
    }

    @Override
    public V endsWith(final @Nullable T desired) {
        return append(
                OrderedSequenceConditions.endsWith(desired, operations)
        );
    }

    @Override
    public V doesNotEndWith(final @Nullable T desired) {
        return append(
                OrderedSequenceConditions.doesNotEndWith(desired, operations)
        );
    }
}
