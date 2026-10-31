package io.github.libzeal.zeal.values.sdk.sequence;

public abstract class BaseOrderedSequenceValue<S, T, V extends BaseOrderedSequenceValue<S, T, V>>
    extends BaseSequenceValue<S, T, V>
    implements OrderedSequenceValue<T, V> {

    private final OrderedSequenceOperations<T, S> operations;

    protected BaseOrderedSequenceValue(final S subject, final String name, final OrderedSequenceOperations<T, S> operations) {
        super(subject, name, operations);
        this.operations = operations;
    }

    // TODO Javadoc: unlike unordered sequences, the hint for includes/excludes on an ordered sequence states the
    //  index at which the desired element was found (or that it was not found); explain this difference
    @Override
    public V includes(final T desired) {
        return append(
            OrderedSequenceExpressions.includes(desired, operations)
        );
    }

    @Override
    public V excludes(final T desired) {
        return append(
            OrderedSequenceExpressions.excludes(desired, operations)
        );
    }

    @Override
    public V hasAtIndex(final T desired, final int index) {
        return append(
            OrderedSequenceExpressions.hasAtIndex(desired, index, operations)
        );
    }

    @Override
    public V doesNotHaveAtIndex(final T desired, final int index) {
        return append(
            OrderedSequenceExpressions.doesNotHaveAtIndex(desired, index, operations)
        );
    }

    @Override
    public V lastIndexOfIs(final T desired, final int index) {
        return append(
            OrderedSequenceExpressions.lastIndexOfIs(desired, index, operations)
        );
    }

    @Override
    public V lastIndexOfIsNot(final T desired, final int index) {
        return append(
            OrderedSequenceExpressions.lastIndexOfIsNot(desired, index, operations)
        );
    }

    @Override
    public V startsWith(final T desired) {
        return append(
            OrderedSequenceExpressions.startsWith(desired, operations)
        );
    }

    @Override
    public V doesNotStartWith(final T desired) {
        return append(
            OrderedSequenceExpressions.doesNotStartWith(desired, operations)
        );
    }

    @Override
    public V endsWith(final T desired) {
        return append(
            OrderedSequenceExpressions.endsWith(desired, operations)
        );
    }

    @Override
    public V doesNotEndWith(final T desired) {
        return append(
            OrderedSequenceExpressions.doesNotEndWith(desired, operations)
        );
    }
}
