package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.sequence.BaseSequenceValue;
import io.github.libzeal.zeal.values.sdk.sequence.SequenceOperations;

import java.util.Objects;

public final class IterableValue<T> extends BaseSequenceValue<Iterable<T>, T, IterableValue<T>> {

    public IterableValue(final Iterable<T> subject) {
        super(subject, "Iterable value", new ConcreteIterableSequenceOperations<>());
    }

    private static class ConcreteIterableSequenceOperations<T, I extends Iterable<T>>
        implements SequenceOperations<T, I> {

        @Override
        public int size(final I haystack) {

            int count = 0;

            for (final T e : haystack) {
                count++;
            }

            return count;

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
