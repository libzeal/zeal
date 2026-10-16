package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.BaseValue;
import io.github.libzeal.zeal.values.sdk.sequence.InspectableSequenceExpressions;
import io.github.libzeal.zeal.values.sdk.sequence.InspectableSequenceExpressions.InspectableSequenceOperations;
import io.github.libzeal.zeal.values.sdk.sequence.RepeatableSequenceExpressions;
import io.github.libzeal.zeal.values.sdk.sequence.RepeatableSequenceExpressions.RepeatableSequenceOperations;
import io.github.libzeal.zeal.values.sdk.sequence.SizedSequenceExpressions;
import io.github.libzeal.zeal.values.sdk.sequence.SizedSequenceExpressions.SizedSequenceOperations;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class CollectionValue<T> extends BaseValue<Collection<T>, CollectionValue<T>>
    implements CollectionSequenceValue<T, Collection<T>, CollectionValue<T>> {

    private final ConcreteCollectionSequenceOperations<T> operations;

    public CollectionValue(final Collection<T> subject) {
        super(subject, "Collection value");
        this.operations = new ConcreteCollectionSequenceOperations<>();
    }

    @Override
    public CollectionValue<T> isEmpty() {
        return append(
            SizedSequenceExpressions.isEmpty(operations)
        );
    }

    @Override
    public CollectionValue<T> isNotEmpty() {
        return append(
            SizedSequenceExpressions.isNotEmpty(operations)
        );
    }

    @Override
    public CollectionValue<T> hasLengthOf(final int length) {
        return append(
            SizedSequenceExpressions.hasLengthOf(length, operations)
        );
    }

    @Override
    public CollectionValue<T> doesNotHaveLengthOf(final int length) {
        return append(
            SizedSequenceExpressions.doesNotHaveLengthOf(length, operations)
        );
    }

    @Override
    public CollectionValue<T> isShorterThan(final int length) {
        return append(
            SizedSequenceExpressions.isShorterThan(length, operations)
        );
    }

    @Override
    public CollectionValue<T> isShorterThanOrEqualTo(final int length) {
        return append(
            SizedSequenceExpressions.isShorterThanOrEqualTo(length, operations)
        );
    }

    @Override
    public CollectionValue<T> isLongerThan(final int length) {
        return append(
            SizedSequenceExpressions.isLongerThan(length, operations)
        );
    }

    @Override
    public CollectionValue<T> isLongerThanOrEqualTo(final int length) {
        return append(
            SizedSequenceExpressions.isLongerThanOrEqualTo(length, operations)
        );
    }

    @Override
    public CollectionValue<T> includes(final T desired) {
        return append(
            InspectableSequenceExpressions.includes(desired, operations)
        );
    }

    @Override
    public CollectionValue<T> includesAll(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.includesAll(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final CollectionValue<T> includesAllOf(final T... desired) {
        return includesAll(Arrays.asList(desired));
    }

    @Override
    public CollectionValue<T> includesAny(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.includesAny(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final CollectionValue<T> includesAnyOf(final T... desired) {
        return includesAny(Arrays.asList(desired));
    }

    @Override
    public CollectionValue<T> excludes(final T desired) {
        return append(
            InspectableSequenceExpressions.excludes(desired, operations)
        );
    }

    @Override
    public CollectionValue<T> excludesAll(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.excludesAll(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final CollectionValue<T> excludesAllOf(final T... desired) {
        return excludesAll(Arrays.asList(desired));
    }

    @Override
    public CollectionValue<T> excludesAny(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.excludesAny(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final CollectionValue<T> excludesAnyOf(final T... desired) {
        return excludesAny(Arrays.asList(desired));
    }

    @Override
    public CollectionValue<T> includesExactly(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesExactly(desired, times, operations)
        );
    }

    @Override
    public CollectionValue<T> includesMoreThan(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesMoreThan(desired, times, operations)
        );
    }

    @Override
    public CollectionValue<T> includesMoreThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesMoreThanOrEqualTo(desired, times, operations)
        );
    }

    @Override
    public CollectionValue<T> includesLessThan(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesLessThan(desired, times, operations)
        );
    }

    @Override
    public CollectionValue<T> includesLessThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesLessThanOrEqualTo(desired, times, operations)
        );
    }

    protected static class ConcreteCollectionSequenceOperations<T>
        implements SizedSequenceOperations<Collection<T>>,
        InspectableSequenceOperations<T, Collection<T>>,
        RepeatableSequenceOperations<T, Collection<T>> {

        @Override
        public List<T> findAllIn(final Collection<T> haystack, final Collection<T> needles) {
            return haystack.stream()
                .filter(needles::contains)
                .collect(toList());
        }

        @Override
        public int size(final Collection<T> haystack) {
            return haystack.size();
        }

        @Override
        public boolean isEmpty(final Collection<T> haystack) {
            return haystack.isEmpty();
        }

        @Override
        public boolean includes(final Collection<T> haystack, final T needle) {
            return haystack.contains(needle);
        }

        @Override
        public int occurrences(final Collection<T> haystack, final T needle) {
            return Collections.frequency(haystack, needle);
        }
    }
}
