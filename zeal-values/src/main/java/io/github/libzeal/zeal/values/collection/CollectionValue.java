package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.api.sequence.RepeatableSequenceValue;
import io.github.libzeal.zeal.values.api.sequence.RepeatableSequenceValueBuilder;
import io.github.libzeal.zeal.values.collection.BaseCollectionValue.CollectionSequenceOperations;

import java.util.Collection;

public class CollectionValue<T> extends BaseCollectionValue<
        T,
        Collection<T>,
        CollectionSequenceOperations<T, Collection<T>>,
        CollectionValue<T>>
    implements RepeatableSequenceValue<T, Collection<T>, CollectionValue<T>> {

    public CollectionValue(final Collection<T> subject) {
        super(subject, "Collection value", new CollectionSequenceOperations<>());
    }

    @Override
    public CollectionValue<T> includesExactly(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesExactly(desired, times, operations())
        );
    }

    @Override
    public CollectionValue<T> includesMoreThan(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesMoreThan(desired, times, operations())
        );
    }

    @Override
    public CollectionValue<T> includesMoreThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesMoreThanOrEqualTo(desired, times, operations())
        );
    }

    @Override
    public CollectionValue<T> includesLessThan(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesLessThan(desired, times, operations())
        );
    }

    @Override
    public CollectionValue<T> includesLessThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesLessThanOrEqualTo(desired, times, operations())
        );
    }
}
