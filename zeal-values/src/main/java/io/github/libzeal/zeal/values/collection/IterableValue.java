package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.BaseValue;
import io.github.libzeal.zeal.values.sdk.sequence.InspectableSequenceValue;
import io.github.libzeal.zeal.values.sdk.sequence.RepeatableSequenceValue;
import io.github.libzeal.zeal.values.sdk.sequence.SizedSequenceValue;
import io.github.libzeal.zeal.values.sdk.sequence.InspectableSequenceExpressions;
import io.github.libzeal.zeal.values.sdk.sequence.InspectableSequenceExpressions.InspectableSequenceOperations;
import io.github.libzeal.zeal.values.sdk.sequence.RepeatableSequenceExpressions;
import io.github.libzeal.zeal.values.sdk.sequence.RepeatableSequenceExpressions.RepeatableSequenceOperations;
import io.github.libzeal.zeal.values.sdk.sequence.SizedSequenceExpressions;
import io.github.libzeal.zeal.values.sdk.sequence.SizedSequenceExpressions.SizedSequenceOperations;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public final class IterableValue<T> extends
    BaseValue<Iterable<T>, IterableValue<T>>
        implements SizedSequenceValue<T, IterableValue<T>>,
            InspectableSequenceValue<T, IterableValue<T>>,
            RepeatableSequenceValue<T, IterableValue<T>> {

    private final ConcreteIterableSequenceOperations<T, Iterable<T>> operations;

    public IterableValue(final Iterable<T> subject) {
        super(subject, "Iterable value");
        this.operations = new ConcreteIterableSequenceOperations<>();
    }

    @Override
    public IterableValue<T> isEmpty() {
        return append(
            SizedSequenceExpressions.isEmpty(operations)
        );
    }

    @Override
    public IterableValue<T> isNotEmpty() {
        return append(
            SizedSequenceExpressions.isNotEmpty(operations)
        );
    }

    @Override
    public IterableValue<T> hasLengthOf(final int length) {
        return append(
            SizedSequenceExpressions.hasLengthOf(length, operations)
        );
    }

    @Override
    public IterableValue<T> doesNotHaveLengthOf(final int length) {
        return append(
            SizedSequenceExpressions.doesNotHaveLengthOf(length, operations)
        );
    }

    @Override
    public IterableValue<T> isShorterThan(final int length) {
        return append(
            SizedSequenceExpressions.isShorterThan(length, operations)
        );
    }

    @Override
    public IterableValue<T> isShorterThanOrEqualTo(final int length) {
        return append(
            SizedSequenceExpressions.isShorterThanOrEqualTo(length, operations)
        );
    }

    @Override
    public IterableValue<T> isLongerThan(final int length) {
        return append(
            SizedSequenceExpressions.isLongerThan(length, operations)
        );
    }

    @Override
    public IterableValue<T> isLongerThanOrEqualTo(final int length) {
        return append(
            SizedSequenceExpressions.isLongerThanOrEqualTo(length, operations)
        );
    }

    @Override
    public IterableValue<T> includes(final T desired) {
        return append(
            InspectableSequenceExpressions.includes(desired, operations)
        );
    }

    @Override
    public IterableValue<T> includesAll(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.includesAll(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final IterableValue<T> includesAllOf(final T... desired) {
        return includesAll(Arrays.asList(desired));
    }

    @Override
    public IterableValue<T> includesAny(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.includesAny(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final IterableValue<T> includesAnyOf(final T... desired) {
        return includesAny(Arrays.asList(desired));
    }

    @Override
    public IterableValue<T> excludes(final T desired) {
        return append(
            InspectableSequenceExpressions.excludes(desired, operations)
        );
    }

    @Override
    public IterableValue<T> excludesAll(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.excludesAll(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final IterableValue<T> excludesAllOf(final T... desired) {
        return excludesAll(Arrays.asList(desired));
    }

    @Override
    public IterableValue<T> excludesAny(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.excludesAny(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final IterableValue<T> excludesAnyOf(final T... desired) {
        return excludesAny(Arrays.asList(desired));
    }

    @Override
    public IterableValue<T> includesExactly(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesExactly(desired, times, operations)
        );
    }

    @Override
    public IterableValue<T> includesMoreThan(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesMoreThan(desired, times, operations)
        );
    }

    @Override
    public IterableValue<T> includesMoreThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesMoreThanOrEqualTo(desired, times, operations)
        );
    }

    @Override
    public IterableValue<T> includesLessThan(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesLessThan(desired, times, operations)
        );
    }

    @Override
    public IterableValue<T> includesLessThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesLessThanOrEqualTo(desired, times, operations)
        );
    }

    private static class ConcreteIterableSequenceOperations<T, I extends Iterable<T>>
        implements SizedSequenceOperations<I>,
            InspectableSequenceOperations<T, I>,
            RepeatableSequenceOperations<T, I>{

        @Override
        public List<T> findAllIn(final I haystack, final Collection<T> needles) {
            return StreamSupport.stream(haystack.spliterator(), false)
                .filter(needles::contains)
                .collect(toList());
        }

        @Override
        public int size(final I haystack) {

            int count = 0;

            for (final T e : haystack) {
                count++;
            }

            return count;

        }

        @Override
        public boolean isEmpty(final I haystack) {
            return haystack.iterator().hasNext();
        }

        @Override
        public boolean includes(final I haystack, final T needle) {

            for (final T element: haystack) {
                if (Objects.equals(element, needle)) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public int occurrences(final I haystack, final T needle) {

            int count = 0;

            for (final T e : haystack) {
                if (Objects.equals(e, needle)) {
                    count++;
                }
            }

            return count;
        }
    }
}
