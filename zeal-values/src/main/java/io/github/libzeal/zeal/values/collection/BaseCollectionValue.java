package io.github.libzeal.zeal.values.collection;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public abstract class BaseCollectionValue<T, C extends Collection<T>, E extends BaseCollectionValue<T, C, E>>
        extends BaseIterableValue<T, C, E> {

    protected BaseCollectionValue(final C subject, final String name) {
        super(subject, name);
    }

    @Override
    protected long findSize(final C subject) {
        return subject.size();
    }

    @Override
    protected boolean findIsEmpty(final C subject) {
        return subject.isEmpty();
    }

    @Override
    protected Predicate<T> findContains(final C haystack) {
        return haystack::contains;
    }
}
