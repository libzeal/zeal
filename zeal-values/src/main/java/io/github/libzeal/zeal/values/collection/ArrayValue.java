package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.api.BaseObjectValue;
import io.github.libzeal.zeal.values.api.sequence.*;
import io.github.libzeal.zeal.values.api.sequence.builder.*;
import io.github.libzeal.zeal.values.api.sequence.builder.InspectableSequenceValueBuilder.InspectableSequenceOperations;
import io.github.libzeal.zeal.values.api.sequence.builder.OrderedSequenceValueBuilder.OrderedSequenceOperations;
import io.github.libzeal.zeal.values.api.sequence.builder.RepeatableSequenceValueBuilder.RepeatableSequenceOperations;
import io.github.libzeal.zeal.values.api.sequence.builder.SizedSequenceValueBuilder.SizedSequenceOperations;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class ArrayValue<T> extends BaseObjectValue<T[], ArrayValue<T>> implements
    SizedSequenceValue<T[], ArrayValue<T>>,
    InspectableSequenceValue<T, T[], ArrayValue<T>>,
    OrderedSequenceValue<T, T[], ArrayValue<T>>,
    RepeatableSequenceValue<T, T[], ArrayValue<T>> {

    private final ArraySequenceOperations<T> operations = new ArraySequenceOperations<>();

    public ArrayValue(final T[] subject) {
        super(subject, "Array value");
    }

    @Override
    public ArrayValue<T> isEmpty() {
        return append(
            SizedSequenceValueBuilder.isEmpty(operations)
        );
    }

    @Override
    public ArrayValue<T> isNotEmpty() {
        return append(
            SizedSequenceValueBuilder.isNotEmpty(operations)
        );
    }

    @Override
    public ArrayValue<T> hasLengthOf(final int length) {
        return append(
            SizedSequenceValueBuilder.hasLengthOf(length, operations)
        );
    }

    @Override
    public ArrayValue<T> doesNotHaveLengthOf(final int length) {
        return append(
            SizedSequenceValueBuilder.doesNotHaveLengthOf(length, operations)
        );
    }

    @Override
    public ArrayValue<T> isShorterThan(final int length) {
        return append(
            SizedSequenceValueBuilder.isShorterThan(length, operations)
        );
    }

    @Override
    public ArrayValue<T> isShorterThanOrEqualTo(final int length) {
        return append(
            SizedSequenceValueBuilder.isShorterThanOrEqualTo(length, operations)
        );
    }

    @Override
    public ArrayValue<T> isLongerThan(final int length) {
        return append(
            SizedSequenceValueBuilder.isLongerThan(length, operations)
        );
    }

    @Override
    public ArrayValue<T> isLongerThanOrEqualTo(final int length) {
        return append(
            SizedSequenceValueBuilder.isLongerThanOrEqualTo(length, operations)
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
            InspectableSequenceValueBuilder.includesAll(desired, operations)
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
            InspectableSequenceValueBuilder.includesAny(desired, operations)
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
            InspectableSequenceValueBuilder.excludesAll(desired, operations)
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
            InspectableSequenceValueBuilder.excludesAny(desired, operations)
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

    protected static final class ArraySequenceOperations<T>
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
