package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.BaseValue;
import io.github.libzeal.zeal.values.sdk.sequence.*;
import io.github.libzeal.zeal.values.sdk.sequence.InspectableSequenceExpressions.InspectableSequenceOperations;
import io.github.libzeal.zeal.values.sdk.sequence.OrderedSequenceExpressions.OrderedSequenceOperations;
import io.github.libzeal.zeal.values.sdk.sequence.RepeatableSequenceExpressions.RepeatableSequenceOperations;
import io.github.libzeal.zeal.values.sdk.sequence.SizedSequenceExpressions.SizedSequenceOperations;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class ArrayValue<T> extends BaseValue<T[], ArrayValue<T>> implements
    SizedSequenceValue<T[], ArrayValue<T>>,
    InspectableSequenceValue<T, ArrayValue<T>>,
    OrderedSequenceValue<T, ArrayValue<T>>,
    RepeatableSequenceValue<T, ArrayValue<T>> {

    private final ArrayOperations<T> operations = new ArrayOperations<>();

    public ArrayValue(final T[] subject) {
        super(subject, "Array value");
    }

    @Override
    public ArrayValue<T> isEmpty() {
        return append(
            SizedSequenceExpressions.isEmpty(operations)
        );
    }

    @Override
    public ArrayValue<T> isNotEmpty() {
        return append(
            SizedSequenceExpressions.isNotEmpty(operations)
        );
    }

    @Override
    public ArrayValue<T> hasLengthOf(final int length) {
        return append(
            SizedSequenceExpressions.hasLengthOf(length, operations)
        );
    }

    @Override
    public ArrayValue<T> doesNotHaveLengthOf(final int length) {
        return append(
            SizedSequenceExpressions.doesNotHaveLengthOf(length, operations)
        );
    }

    @Override
    public ArrayValue<T> isShorterThan(final int length) {
        return append(
            SizedSequenceExpressions.isShorterThan(length, operations)
        );
    }

    @Override
    public ArrayValue<T> isShorterThanOrEqualTo(final int length) {
        return append(
            SizedSequenceExpressions.isShorterThanOrEqualTo(length, operations)
        );
    }

    @Override
    public ArrayValue<T> isLongerThan(final int length) {
        return append(
            SizedSequenceExpressions.isLongerThan(length, operations)
        );
    }

    @Override
    public ArrayValue<T> isLongerThanOrEqualTo(final int length) {
        return append(
            SizedSequenceExpressions.isLongerThanOrEqualTo(length, operations)
        );
    }

    @Override
    public ArrayValue<T> includes(final T desired) {
        return append(
            OrderedSequenceExpressions.includes(desired, operations)
        );
    }

    @Override
    public ArrayValue<T> includesAll(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.includesAll(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final ArrayValue<T> includesAllOf(final T... desired) {
        return includesAll(Arrays.asList(desired));
    }

    @Override
    public ArrayValue<T> includesAny(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.includesAny(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final ArrayValue<T> includesAnyOf(final T... desired) {
        return includesAny(Arrays.asList(desired));
    }

    @Override
    public ArrayValue<T> includesExactly(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesExactly(desired, times, operations)
        );
    }

    @Override
    public ArrayValue<T> includesMoreThan(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesMoreThan(desired, times, operations)
        );
    }

    @Override
    public ArrayValue<T> includesMoreThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesMoreThanOrEqualTo(desired, times, operations)
        );
    }

    @Override
    public ArrayValue<T> includesLessThan(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesLessThan(desired, times, operations)
        );
    }

    @Override
    public ArrayValue<T> includesLessThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesLessThanOrEqualTo(desired, times, operations)
        );
    }

    @Override
    public ArrayValue<T> excludes(final T desired) {
        return append(
            OrderedSequenceExpressions.excludes(desired, operations)
        );
    }

    @Override
    public ArrayValue<T> excludesAll(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.excludesAll(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final ArrayValue<T> excludesAllOf(final T... desired) {
        return excludesAll(Arrays.asList(desired));
    }

    @Override
    public ArrayValue<T> excludesAny(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.excludesAny(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final ArrayValue<T> excludesAnyOf(final T... desired) {
        return excludesAny(Arrays.asList(desired));
    }

    @Override
    public ArrayValue<T> hasAtIndex(final T desired, final int index) {
        return append(
            OrderedSequenceExpressions.hasAtIndex(desired, index, operations)
        );
    }

    @Override
    public ArrayValue<T> doesNotHaveAtIndex(final T desired, final int index) {
        return append(
            OrderedSequenceExpressions.doesNotHaveAtIndex(desired, index, operations)
        );
    }

    @Override
    public ArrayValue<T> startsWith(final T desired) {
        return append(
            OrderedSequenceExpressions.startsWith(desired, operations)
        );
    }

    @Override
    public ArrayValue<T> doesNotStartWith(final T desired) {
        return append(
            OrderedSequenceExpressions.doesNotStartWith(desired, operations)
        );
    }

    @Override
    public ArrayValue<T> endsWith(final T desired) {
        return append(
            OrderedSequenceExpressions.endsWith(desired, operations)
        );
    }

    @Override
    public ArrayValue<T> doesNotEndWith(final T desired) {
        return append(
            OrderedSequenceExpressions.doesNotEndWith(desired, operations)
        );
    }

    private static final class ArrayOperations<T>
        implements
            SizedSequenceOperations<T[]>,
            InspectableSequenceOperations<T, T[]>,
            OrderedSequenceOperations<T, T[]>,
            RepeatableSequenceOperations<T, T[]> {

        @Override
        public int indexOf(final T[] haystack, final T needle) {

            int index = 0;

            for (final T element : haystack) {
                if (Objects.equals(element, needle)) {
                    return index;
                }

                index++;
            }

            return -1;
        }

        @Override
        public Element<T> firstElement(final T[] haystack, final T desired) {

            if (haystack.length > 0) {
                return Element.found(haystack[0]);
            }
            else {
                return Element.missing();
            }
        }

        @Override
        public List<T> findAllIn(final T[] haystack, final Collection<T> needles) {
            return needles.stream()
                .filter(needle -> indexOf(haystack, needle) != -1)
                .collect(toList());
        }

        @Override
        public int occurrences(final T[] haystack, final T needle) {

            int count = 0;

            for (final T element : haystack) {
                if (Objects.equals(element, needle)) {
                    count++;
                }
            }

            return count;
        }

        @Override
        public Element<T> atIndex(final T[] haystack, final int index, final T desired) {

            if (index > 0 && index < haystack.length) {
                return Element.found(haystack[index]);
            }
            else {
                return Element.missing();
            }
        }

        @Override
        public int size(final T[] haystack) {
            return haystack.length;
        }

        @Override
        public boolean isEmpty(final T[] haystack) {
            return haystack.length == 0;
        }

        @Override
        public boolean includes(final T[] haystack, final T needle) {

            for (final T element: haystack) {
                if (Objects.equals(element, needle)) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public Element<T> lastElement(final T[] haystack, final T desired) {

            if (haystack.length > 0) {
                return Element.found(haystack[haystack.length - 1]);
            }
            else {
                return Element.missing();
            }
        }
    }
}
