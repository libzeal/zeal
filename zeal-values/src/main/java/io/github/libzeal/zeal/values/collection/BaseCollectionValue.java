package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.api.sequence.RepeatableSequenceValueBuilder.RepeatableSequenceOperations;
import io.github.libzeal.zeal.values.collection.BaseCollectionValue.CollectionSequenceOperations;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public abstract class BaseCollectionValue<
            T,
            C extends Collection<T>,
            O extends CollectionSequenceOperations<T, C>,
            E extends BaseCollectionValue<T, C, O, E>>
        extends BaseIterableValue<T, C, O, E> {

    protected BaseCollectionValue(final C subject, final String name, final O ops) {
        super(subject, name, ops);
    }

    protected static class CollectionSequenceOperations<T, I extends Collection<T>>
        extends IterableSequenceOperations<T, I> implements RepeatableSequenceOperations<T, I> {

        @Override
        public List<T> findAllIn(final I haystack, final Collection<T> needles) {
            return haystack.stream()
                .filter(needles::contains)
                .collect(toList());
        }

        @Override
        public int size(final I haystack) {
            return haystack.size();
        }

        @Override
        public boolean isEmpty(final I haystack) {
            return haystack.isEmpty();
        }

        @Override
        public boolean includes(final I haystack, final T needle) {
            return haystack.contains(needle);
        }

        @Override
        public int occurrences(final I haystack, final T needle) {
            return Collections.frequency(haystack, needle);
        }
    }
}
