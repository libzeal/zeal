package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.sequence.BaseSequenceValue;
import io.github.libzeal.zeal.values.sdk.sequence.SequenceOperations;

import java.util.Collection;
import java.util.Objects;

/**
 * A value used to evaluate {@link Iterable}s.
 *
 * @param <T>
 *         The type of the elements of the {@link Iterable}.
 *
 * @author Justin Albano
 */
public final class IterableValue<T> extends BaseSequenceValue<Iterable<T>, T, IterableValue<T>> {

    /**
     * Creates a new iterable value using the supplied subject.
     *
     * @param subject
     *         The desired subject.
     */
    public IterableValue(final Iterable<T> subject) {
        super(subject, "Iterable value", new ConcreteIterableSequenceOperations<>());
    }

    private static class ConcreteIterableSequenceOperations<T, I extends Iterable<T>>
            implements SequenceOperations<T, I> {

        @Override
        public int size(final I haystack) {

            if (haystack instanceof final Collection<?> collection) {
                return collection.size();
            }

            int count = 0;

            for (final T e : haystack) {
                count++;
            }

            return count;

        }

        @Override
        public boolean includes(final I haystack, final T needle) {

            if (haystack instanceof final Collection<?> collection) {
                return includesInCollection(needle, collection);
            }

            for (final T element : haystack) {
                if (Objects.equals(element, needle)) {
                    return true;
                }
            }

            return false;
        }

        private static <T> boolean includesInCollection(
                final T needle,
                final Collection<?> collection
        ) {
            try {
                return collection.contains(needle);
            }
            catch (final NullPointerException e) {
                // Some collections do not permit null elements and may throw
                // when a null needle is used. This section catches this
                // exception and if the needle is null (the cause of the NPE),
                // the needle is considered "not found."

                if (needle == null) {
                    return false;
                }

                throw e;
            }
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
