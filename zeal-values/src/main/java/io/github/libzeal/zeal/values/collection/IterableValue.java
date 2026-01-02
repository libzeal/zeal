package io.github.libzeal.zeal.values.collection;

import java.util.Collection;
import java.util.Objects;

public final class IterableValue<T> extends
        BaseIterableValue<T, Iterable<T>, IterableValue<T>> {

    public IterableValue(final Iterable<T> subject) {
        super(subject, "Iterable value");
    }
}
