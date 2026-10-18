package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.BaseValue;
import io.github.libzeal.zeal.values.sdk.sequence.InspectableSequenceExpressions;
import io.github.libzeal.zeal.values.sdk.sequence.RepeatableSequenceExpressions;
import io.github.libzeal.zeal.values.sdk.sequence.SizedSequenceExpressions;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SimpleCollectionValue<T> extends BaseValue<Collection<T>, SimpleCollectionValue<T>>
    implements CollectionValue<T, Collection<T>, SimpleCollectionValue<T>> {

    private final SimpleCollectionOperations<T> operations;

    public SimpleCollectionValue(final Collection<T> subject) {
        super(subject, "Collection value");
        this.operations = new SimpleCollectionOperations<>();
    }

    @Override
    public SimpleCollectionValue<T> isEmpty() {
        return append(
            SizedSequenceExpressions.isEmpty(operations)
        );
    }

    @Override
    public SimpleCollectionValue<T> isNotEmpty() {
        return append(
            SizedSequenceExpressions.isNotEmpty(operations)
        );
    }

    @Override
    public SimpleCollectionValue<T> hasLengthOf(final int length) {
        return append(
            SizedSequenceExpressions.hasLengthOf(length, operations)
        );
    }

    @Override
    public SimpleCollectionValue<T> doesNotHaveLengthOf(final int length) {
        return append(
            SizedSequenceExpressions.doesNotHaveLengthOf(length, operations)
        );
    }

    @Override
    public SimpleCollectionValue<T> isShorterThan(final int length) {
        return append(
            SizedSequenceExpressions.isShorterThan(length, operations)
        );
    }

    @Override
    public SimpleCollectionValue<T> isShorterThanOrEqualTo(final int length) {
        return append(
            SizedSequenceExpressions.isShorterThanOrEqualTo(length, operations)
        );
    }

    @Override
    public SimpleCollectionValue<T> isLongerThan(final int length) {
        return append(
            SizedSequenceExpressions.isLongerThan(length, operations)
        );
    }

    @Override
    public SimpleCollectionValue<T> isLongerThanOrEqualTo(final int length) {
        return append(
            SizedSequenceExpressions.isLongerThanOrEqualTo(length, operations)
        );
    }

    @Override
    public SimpleCollectionValue<T> includes(final T desired) {
        return append(
            InspectableSequenceExpressions.includes(desired, operations)
        );
    }

    @Override
    public SimpleCollectionValue<T> includesAll(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.includesAll(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final SimpleCollectionValue<T> includesAllOf(final T... desired) {
        return includesAll(Arrays.asList(desired));
    }

    @Override
    public SimpleCollectionValue<T> includesAny(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.includesAny(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final SimpleCollectionValue<T> includesAnyOf(final T... desired) {
        return includesAny(Arrays.asList(desired));
    }

    @Override
    public SimpleCollectionValue<T> excludes(final T desired) {
        return append(
            InspectableSequenceExpressions.excludes(desired, operations)
        );
    }

    @Override
    public SimpleCollectionValue<T> excludesAll(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.excludesAll(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final SimpleCollectionValue<T> excludesAllOf(final T... desired) {
        return excludesAll(Arrays.asList(desired));
    }

    @Override
    public SimpleCollectionValue<T> excludesAny(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.excludesAny(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final SimpleCollectionValue<T> excludesAnyOf(final T... desired) {
        return excludesAny(Arrays.asList(desired));
    }

    @Override
    public SimpleCollectionValue<T> includesExactly(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesExactly(desired, times, operations)
        );
    }

    @Override
    public SimpleCollectionValue<T> includesMoreThan(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesMoreThan(desired, times, operations)
        );
    }

    @Override
    public SimpleCollectionValue<T> includesMoreThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesMoreThanOrEqualTo(desired, times, operations)
        );
    }

    @Override
    public SimpleCollectionValue<T> includesLessThan(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesLessThan(desired, times, operations)
        );
    }

    @Override
    public SimpleCollectionValue<T> includesLessThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesLessThanOrEqualTo(desired, times, operations)
        );
    }

    private static class SimpleCollectionOperations<T>
        implements CollectionOperations<T, Collection<T>> {

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
