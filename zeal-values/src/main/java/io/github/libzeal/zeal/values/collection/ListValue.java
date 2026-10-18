package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.BaseValue;
import io.github.libzeal.zeal.values.sdk.sequence.*;
import io.github.libzeal.zeal.values.sdk.sequence.OrderedSequenceExpressions.OrderedSequenceOperations;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ListValue<T> extends BaseValue<List<T>, ListValue<T>>
    implements CollectionValue<T, List<T>, ListValue<T>>,
    OrderedSequenceValue<T, ListValue<T>> {

    private final ListOperations<T> operations;

    public ListValue(final List<T> subject) {
        super(subject, "List value");
        this.operations = new ListOperations<>();
    }

    @Override
    public ListValue<T> startsWith(final T desired) {
        return append(
            OrderedSequenceExpressions.startsWith(desired, operations)
        );
    }

    @Override
    public ListValue<T> doesNotStartWith(final T desired) {
        return append(
            OrderedSequenceExpressions.doesNotStartWith(desired, operations)
        );
    }

    @Override
    public ListValue<T> endsWith(final T desired) {
        return append(
            OrderedSequenceExpressions.endsWith(desired, operations)
        );
    }

    @Override
    public ListValue<T> doesNotEndWith(final T desired) {
        return append(
            OrderedSequenceExpressions.doesNotEndWith(desired, operations)
        );
    }

    @Override
    public ListValue<T> hasAtIndex(final T desired, final int index) {
        return append(
            OrderedSequenceExpressions.hasAtIndex(desired, index, operations)
        );
    }

    @Override
    public ListValue<T> doesNotHaveAtIndex(final T desired, final int index) {
        return append(
            OrderedSequenceExpressions.doesNotHaveAtIndex(desired, index, operations)
        );
    }

    @Override
    public ListValue<T> includesExactly(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesExactly(desired, times, operations)
        );
    }

    @Override
    public ListValue<T> includesMoreThan(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesMoreThan(desired, times, operations)
        );
    }

    @Override
    public ListValue<T> includesMoreThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesMoreThanOrEqualTo(desired, times, operations)
        );
    }

    @Override
    public ListValue<T> includesLessThan(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesLessThan(desired, times, operations)
        );
    }

    @Override
    public ListValue<T> includesLessThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesLessThanOrEqualTo(desired, times, operations)
        );
    }

//    public ListValue<T> lastIndexOfIs(final T desired, final int index) {
//
//    }
//
//    public ListValue<T> lastIndexOfIsNot(final T desired, final int index) {
//
//    }

    @Override
    public ListValue<T> isEmpty() {
        return append(
            SizedSequenceExpressions.isEmpty(operations)
        );
    }

    @Override
    public ListValue<T> isNotEmpty() {
        return append(
            SizedSequenceExpressions.isNotEmpty(operations)
        );
    }

    @Override
    public ListValue<T> hasLengthOf(final int length) {
        return append(
            SizedSequenceExpressions.hasLengthOf(length, operations)
        );
    }

    @Override
    public ListValue<T> doesNotHaveLengthOf(final int length) {
        return append(
            SizedSequenceExpressions.doesNotHaveLengthOf(length, operations)
        );
    }

    @Override
    public ListValue<T> isShorterThan(final int length) {
        return append(
            SizedSequenceExpressions.isShorterThan(length, operations)
        );
    }

    @Override
    public ListValue<T> isShorterThanOrEqualTo(final int length) {
        return append(
            SizedSequenceExpressions.isShorterThanOrEqualTo(length, operations)
        );
    }

    @Override
    public ListValue<T> isLongerThan(final int length) {
        return append(
            SizedSequenceExpressions.isLongerThan(length, operations)
        );
    }

    @Override
    public ListValue<T> isLongerThanOrEqualTo(final int length) {
        return append(
            SizedSequenceExpressions.isLongerThanOrEqualTo(length, operations)
        );
    }

    @Override
    public ListValue<T> includes(final T desired) {
        return append(
            InspectableSequenceExpressions.includes(desired, operations)
        );
    }

    @Override
    public ListValue<T> includesAll(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.includesAll(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final ListValue<T> includesAllOf(final T... desired) {
        return includesAll(Arrays.asList(desired));
    }

    @Override
    public ListValue<T> includesAny(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.includesAny(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final ListValue<T> includesAnyOf(final T... desired) {
        return includesAny(Arrays.asList(desired));
    }

    @Override
    public ListValue<T> excludes(final T desired) {
        return append(
            InspectableSequenceExpressions.excludes(desired, operations)
        );
    }

    @Override
    public ListValue<T> excludesAll(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.excludesAll(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final ListValue<T> excludesAllOf(final T... desired) {
        return excludesAll(Arrays.asList(desired));
    }

    @Override
    public ListValue<T> excludesAny(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.excludesAny(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final ListValue<T> excludesAnyOf(final T... desired) {
        return excludesAny(Arrays.asList(desired));
    }

    private static final class ListOperations<T>
        implements CollectionOperations<T, List<T>>,
            OrderedSequenceOperations<T, List<T>> {

        @Override
        public Element<T> lastElement(final List<T> haystack, final T desired) {

            if (haystack.isEmpty()) {
                return Element.missing();
            }
            else {
                return Element.found(haystack.getLast());
            }
        }

        @Override
        public Element<T> atIndex(final List<T> haystack, final int index, final T desired) {

            if (index < haystack.size()) {
                return Element.found(haystack.get(index));
            }
            else {
                return Element.missing();
            }
        }

        @Override
        public int indexOf(final List<T> haystack, final T needle) {
            return haystack.indexOf(needle);
        }

        @Override
        public Element<T> firstElement(final List<T> haystack, final T desired) {

            if (haystack.isEmpty()) {
                return Element.missing();
            }
            else {
                return Element.found(haystack.getFirst());
            }
        }

        @Override
        public int occurrences(final List<T> haystack, final T needle) {
            return Collections.frequency(haystack, needle);
        }

        @Override
        public List<T> findAllIn(final List<T> haystack, final Collection<T> needles) {
            return haystack.stream()
                .filter(needles::contains)
                .collect(toList());
        }

        @Override
        public boolean includes(final List<T> haystack, final T needle) {
            return haystack.contains(needle);
        }

        @Override
        public int size(final List<T> haystack) {
            return haystack.size();
        }

        @Override
        public boolean isEmpty(final List<T> haystack) {
            return haystack.isEmpty();
        }
    }
}
