package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.api.sequence.RepeatableSequenceValue;
import io.github.libzeal.zeal.values.api.sequence.builder.RepeatableSequenceValueBuilder.RepeatableSequenceOperations;
import io.github.libzeal.zeal.values.collection.BaseCollectionValue.CollectionSequenceOperations;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CollectionValue<T> extends BaseCollectionValue<T, Collection<T>, CollectionSequenceOperations<T, Collection<T>>, CollectionValue<T>>
    implements RepeatableSequenceValue<T, CollectionValue<T>> {

    public CollectionValue(final Collection<T> subject) {
        super(subject, "Collection value", new ConcreteCollectionSequenceOperations<>());
    }

    protected static class ConcreteCollectionSequenceOperations<T>
        implements CollectionSequenceOperations<T, Collection<T>>, RepeatableSequenceOperations<T, Collection<T>> {

        @Override
        public List<T> findAllIn(final Collection<T> haystack, final Collection<T> needles) {
            return haystack.stream()
                .filter(needles::contains)
                .collect(toList());
        }

        @Override
        public int size(final Collection<T> haystack) {
            return haystack.size();
        }

        @Override
        public boolean isEmpty(final Collection<T> haystack) {
            return haystack.isEmpty();
        }

        @Override
        public boolean includes(final Collection<T> haystack, final T needle) {
            return haystack.contains(needle);
        }

        @Override
        public int occurrences(final Collection<T> haystack, final T needle) {
            return Collections.frequency(haystack, needle);
        }
    }
}
