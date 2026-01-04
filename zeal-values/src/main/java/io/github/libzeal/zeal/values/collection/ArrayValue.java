package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.api.BaseObjectValue;
import io.github.libzeal.zeal.values.api.sequence.*;
import io.github.libzeal.zeal.values.api.sequence.OrderedSequenceValueBuilder.OrderedSequenceOperations;
import io.github.libzeal.zeal.values.api.sequence.RepeatableSequenceValueBuilder.RepeatableSequenceOperations;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class ArrayValue<T> extends BaseObjectValue<T[], ArrayValue<T>>
    implements OrderedSequenceValue<T, T[], ArrayValue<T>>,
    RepeatableSequenceValue<T, T[], ArrayValue<T>> {

    private final ArraySequenceOperations<T> operations = new ArraySequenceOperations<>();

    public ArrayValue(final T[] subject) {
        super(subject, "Array value");
    }

    @Override
    public ArrayValue<T> isEmpty() {
        return append(
            SequenceValueBuilder.isEmpty(operations)
        );
    }

    @Override
    public ArrayValue<T> isNotEmpty() {
        return append(
            SequenceValueBuilder.isNotEmpty(operations)
        );
    }

    @Override
    public ArrayValue<T> hasLengthOf(final int length) {
        return append(
            SequenceValueBuilder.hasLengthOf(length, operations)
        );
    }

    @Override
    public ArrayValue<T> doesNotHaveLengthOf(final int length) {
        return append(
            SequenceValueBuilder.doesNotHaveLengthOf(length, operations)
        );
    }

    @Override
    public ArrayValue<T> isShorterThan(final int length) {
        return append(
            SequenceValueBuilder.isShorterThan(length, operations)
        );
    }

    @Override
    public ArrayValue<T> isShorterThanOrEqualTo(final int length) {
        return append(
            SequenceValueBuilder.isShorterThanOrEqualTo(length, operations)
        );
    }

    @Override
    public ArrayValue<T> isLongerThan(final int length) {
        return append(
            SequenceValueBuilder.isLongerThan(length, operations)
        );
    }

    @Override
    public ArrayValue<T> isLongerThanOrEqualTo(final int length) {
        return append(
            SequenceValueBuilder.isLongerThanOrEqualTo(length, operations)
        );
    }

    @Override
    public ArrayValue<T> includes(final T desired) {
        return append(
            OrderedSequenceValueBuilder.includes(desired, operations)
        );
    }

    @Override
    public ArrayValue<T> includesAll(final Collection<T> desired) {
        return append(
            SequenceValueBuilder.includesAll(desired, operations)
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
            SequenceValueBuilder.includesAny(desired, operations)
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
            RepeatableSequenceValueBuilder.includesExactly(desired, times, operations)
        );
    }

    @Override
    public ArrayValue<T> includesMoreThan(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesMoreThan(desired, times, operations)
        );
    }

    @Override
    public ArrayValue<T> includesMoreThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesMoreThanOrEqualTo(desired, times, operations)
        );
    }

    @Override
    public ArrayValue<T> includesLessThan(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesLessThan(desired, times, operations)
        );
    }

    @Override
    public ArrayValue<T> includesLessThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesLessThanOrEqualTo(desired, times, operations)
        );
    }

    @Override
    public ArrayValue<T> excludes(final T desired) {
        return append(
            OrderedSequenceValueBuilder.excludes(desired, operations)
        );
    }

    @Override
    public ArrayValue<T> excludesAll(final Collection<T> desired) {
        return append(
            SequenceValueBuilder.excludesAll(desired, operations)
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
            SequenceValueBuilder.excludesAny(desired, operations)
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
            OrderedSequenceValueBuilder.hasAtIndex(desired, index, operations)
        );
    }

    @Override
    public ArrayValue<T> doesNotHaveAtIndex(final T desired, final int index) {
        return append(
            OrderedSequenceValueBuilder.doesNotHaveAtIndex(desired, index, operations)
        );
    }

    @Override
    public ArrayValue<T> startsWith(final T desired) {
        return append(
            OrderedSequenceValueBuilder.startsWith(desired, operations)
        );
    }

    @Override
    public ArrayValue<T> doesNotStartWith(final T desired) {
        return append(
            OrderedSequenceValueBuilder.doesNotStartWith(desired, operations)
        );
    }

    @Override
    public ArrayValue<T> endsWith(final T desired) {
        return append(
            OrderedSequenceValueBuilder.endsWith(desired, operations)
        );
    }

    @Override
    public ArrayValue<T> doesNotEndWith(final T desired) {
        return append(
            OrderedSequenceValueBuilder.doesNotEndWith(desired, operations)
        );
    }

    private static final class ArraySequenceOperations<T>
        implements OrderedSequenceOperations<T, T[]>, RepeatableSequenceOperations<T, T[]> {

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
        public T atIndex(final T[] haystack, final int index) {

            if (index > 0 && index < haystack.length) {
                return haystack[index];
            }
            else {
                return null;
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
        public T lastElement(final T[] haystack) {
            return haystack[haystack.length - 1];
        }
    }
}
