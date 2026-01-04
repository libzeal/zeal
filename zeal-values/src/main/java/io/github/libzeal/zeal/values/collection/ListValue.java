package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.api.sequence.OrderedSequenceValue;
import io.github.libzeal.zeal.values.api.sequence.builder.Element;
import io.github.libzeal.zeal.values.api.sequence.builder.OrderedSequenceValueBuilder;
import io.github.libzeal.zeal.values.api.sequence.builder.OrderedSequenceValueBuilder.OrderedSequenceOperations;
import io.github.libzeal.zeal.values.api.sequence.RepeatableSequenceValue;
import io.github.libzeal.zeal.values.api.sequence.builder.RepeatableSequenceValueBuilder;
import io.github.libzeal.zeal.values.api.sequence.builder.RepeatableSequenceValueBuilder.RepeatableSequenceOperations;
import io.github.libzeal.zeal.values.collection.ListValue.ListSequenceOperations;

import java.util.Collections;
import java.util.List;

public class ListValue<T> extends BaseCollectionValue<T, List<T>, ListSequenceOperations<T>, ListValue<T>>
    implements OrderedSequenceValue<T, List<T>, ListValue<T>>,
        RepeatableSequenceValue<T, List<T>, ListValue<T>> {

    public ListValue(final List<T> subject) {
        super(subject, "List value", new ListSequenceOperations<>());
    }

    @Override
    public ListValue<T> startsWith(final T desired) {
        return append(
            OrderedSequenceValueBuilder.startsWith(desired, operations())
        );
    }

    @Override
    public ListValue<T> doesNotStartWith(final T desired) {
        return append(
            OrderedSequenceValueBuilder.doesNotStartWith(desired, operations())
        );
    }

    @Override
    public ListValue<T> endsWith(final T desired) {
        return append(
            OrderedSequenceValueBuilder.endsWith(desired, operations())
        );
    }

    @Override
    public ListValue<T> doesNotEndWith(final T desired) {
        return append(
            OrderedSequenceValueBuilder.doesNotEndWith(desired, operations())
        );
    }

    @Override
    public ListValue<T> hasAtIndex(final T desired, final int index) {
        return append(
            OrderedSequenceValueBuilder.hasAtIndex(desired, index, operations())
        );
    }

    @Override
    public ListValue<T> doesNotHaveAtIndex(final T desired, final int index) {
        return append(
            OrderedSequenceValueBuilder.doesNotHaveAtIndex(desired, index, operations())
        );
    }

    @Override
    public ListValue<T> includesExactly(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesExactly(desired, times, operations())
        );
    }

    @Override
    public ListValue<T> includesMoreThan(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesMoreThan(desired, times, operations())
        );
    }

    @Override
    public ListValue<T> includesMoreThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesMoreThanOrEqualTo(desired, times, operations())
        );
    }

    @Override
    public ListValue<T> includesLessThan(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesLessThan(desired, times, operations())
        );
    }

    @Override
    public ListValue<T> includesLessThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesLessThanOrEqualTo(desired, times, operations())
        );
    }

//    public ListValue<T> lastIndexOfIs(final T desired, final int index) {
//
//    }
//
//    public ListValue<T> lastIndexOfIsNot(final T desired, final int index) {
//
//    }

    public static final class ListSequenceOperations<T>
        extends CollectionSequenceOperations<T, List<T>>
        implements OrderedSequenceOperations<T, List<T>>, RepeatableSequenceOperations<T, List<T>> {

        @Override
        public Element<T> lastElement(final List<T> haystack, final T desired) {

            if (haystack.isEmpty()) {
                return Element.missing();
            }
            else {
                return Element.found(haystack.get(haystack.size() - 1));
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
                return Element.found(haystack.get(0));
            }
        }

        @Override
        public int occurrences(final List<T> haystack, final T needle) {
            return Collections.frequency(haystack, needle);
        }
    }
}
