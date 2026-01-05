package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.collection.BaseIterableValue.IterableSequenceOperations;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public final class IterableValue<T> extends
    BaseIterableValue<T, Iterable<T>, IterableSequenceOperations<T, Iterable<T>>, IterableValue<T>> {

    public IterableValue(final Iterable<T> subject) {
        super(subject, "Iterable value", new ConcreteIterableSequenceOperations<>());
    }

    private static class ConcreteIterableSequenceOperations<T, I extends Iterable<T>>
        implements IterableSequenceOperations<T, I> {

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
