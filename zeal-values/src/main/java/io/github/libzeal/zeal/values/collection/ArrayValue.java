package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.sequence.BaseOrderedSequenceValue;
import io.github.libzeal.zeal.values.sdk.sequence.Element;
import io.github.libzeal.zeal.values.sdk.sequence.OrderedSequenceOperations;

import java.util.Arrays;

/**
 * A value used to evaluate arrays.
 *
 * @param <T>
 *         The type of the elements in the array.
 *
 * @author Justin Albano
 */
public class ArrayValue<T> extends BaseOrderedSequenceValue<T[], T, ArrayValue<T>> {

    /**
     * Creates a new array value using the supplied subject.
     *
     * @param subject
     *         The desired subject.
     */
    public ArrayValue(final T[] subject) {
        super(subject, "Array value", new ArrayOperations<>());
    }

    private static final class ArrayOperations<T>
            implements OrderedSequenceOperations<T, T[]> {

        private final ListOperations<T> list = new ListOperations<>();

        @Override
        public int size(final T[] haystack) {
            return list.size(Arrays.asList(haystack));
        }

        @Override
        public boolean includes(final T[] haystack, final T needle) {
            return list.includes(Arrays.asList(haystack), needle);
        }

        @Override
        public int occurrences(final T[] haystack, final T needle) {
            return list.occurrences(Arrays.asList(haystack), needle);
        }

        @Override
        public int indexOf(final T[] haystack, final T needle) {
            return list.indexOf(Arrays.asList(haystack), needle);
        }

        @Override
        public int lastIndexOf(final T[] haystack, final T needle) {
            return list.lastIndexOf(Arrays.asList(haystack), needle);
        }

        @Override
        public Element<T> atIndex(final T[] haystack, final int index, final T desired) {
            return list.atIndex(Arrays.asList(haystack), index, desired);
        }

        @Override
        public Element<T> lastElement(final T[] haystack, final T desired) {
            return list.lastElement(Arrays.asList(haystack), desired);
        }
    }
}
