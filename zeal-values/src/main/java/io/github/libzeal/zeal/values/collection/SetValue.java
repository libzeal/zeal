package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.collection.SetValue.SetSequenceOperations;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class SetValue<T> extends BaseCollectionValue<T, Set<T>, SetSequenceOperations<T>, SetValue<T>> {

    public SetValue(final Set<T> subject) {
        super(subject, "Set value", new SetSequenceOperations<>());
    }

    // TODO Optimize these to short circuit, since Sets have unique values

    @Override
    public SetValue<T> includesExactly(final T desired, final long times) {
        return super.includesExactly(desired, times);
    }

    @Override
    public SetValue<T> includesMoreThan(final T desired, final long times) {
        return super.includesMoreThan(desired, times);
    }

    @Override
    public SetValue<T> includesMoreThanOrEqualTo(final T desired, final long times) {
        return super.includesMoreThanOrEqualTo(desired, times);
    }

    @Override
    public SetValue<T> includesLessThan(final T desired, final long times) {
        return super.includesLessThan(desired, times);
    }

    @Override
    public SetValue<T> includesLessThanOrEqualTo(final T desired, final long times) {
        return super.includesLessThanOrEqualTo(desired, times);
    }

    public static final class SetSequenceOperations<T>
        implements CollectionSequenceOperations<T, Set<T>> {

        @Override
        public int occurrences(final Set<T> haystack, final T needle) {
            return Collections.frequency(haystack, needle);
        }

        @Override
        public List<T> findAllIn(final Set<T> haystack, final Collection<T> needles) {
            return haystack.stream()
                .filter(needles::contains)
                .collect(toList());
        }

        @Override
        public boolean includes(final Set<T> haystack, final T needle) {
            return haystack.contains(needle);
        }

        @Override
        public int size(final Set<T> haystack) {
            return haystack.size();
        }

        @Override
        public boolean isEmpty(final Set<T> haystack) {
            return haystack.isEmpty();
        }
    }
}
