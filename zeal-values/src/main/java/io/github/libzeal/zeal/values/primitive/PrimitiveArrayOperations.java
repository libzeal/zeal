package io.github.libzeal.zeal.values.primitive;

import io.github.libzeal.zeal.logic.unary.Observed;
import io.github.libzeal.zeal.values.sdk.sequence.OrderedSequenceOperations;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import static java.util.Objects.requireNonNull;

final class PrimitiveArrayOperations<T, A>
        implements OrderedSequenceOperations<T, A> {

    private final ToIntFunction<A> length;
    private final ElementAt<A, T> element;

    PrimitiveArrayOperations(final ToIntFunction<A> length, final ElementAt<A, T> element) {
        this.length = requireNonNull(length);
        this.element = requireNonNull(element);
    }

    @FunctionalInterface
    interface ElementAt<A, T> {
        T at(A array, int index);
    }

    T elementAt(final A array, final int index) {
        return element.at(array, index);
    }

    @Override
    public int size(final A haystack) {
        return length.applyAsInt(haystack);
    }

    @Override
    public boolean includes(final A haystack, final @Nullable T needle) {
        return indexOf(haystack, needle) >= 0;
    }

    @Override
    public int occurrences(final A haystack, final @Nullable T needle) {

        int count = 0;

        for (int i = 0; i < size(haystack); i++) {
            if (Objects.equals(elementAt(haystack, i), needle)) {
                count++;
            }
        }

        return count;
    }

    @Override
    public List<T> findAllSatisfying(final A haystack, final Predicate<? super T> predicate, final int limit) {

        final List<T> matches = new ArrayList<>();

        for (int i = 0; i < size(haystack) && matches.size() < limit; i++) {

            final T current = elementAt(haystack, i);

            if (predicate.test(current)) {
                matches.add(current);
            }
        }

        return matches;
    }

    @Override
    public int indexOf(final A haystack, final @Nullable T needle) {

        for (int i = 0; i < size(haystack); i++) {
            if (Objects.equals(elementAt(haystack, i), needle)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(final A haystack, final @Nullable T needle) {

        for (int i = size(haystack) - 1; i >= 0; i--) {
            if (Objects.equals(elementAt(haystack, i), needle)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public Observed<T> atIndex(final A haystack, final int index, final @Nullable T desired) {

        if (index >= 0 && index < size(haystack)) {
            return Observed.found(elementAt(haystack, index));
        }
        else {
            return Observed.missing();
        }
    }

    @Override
    public Observed<T> lastElement(final A haystack, final @Nullable T desired) {

        final int size = size(haystack);

        if (size == 0) {
            return Observed.missing();
        }
        else {
            return Observed.found(elementAt(haystack, size - 1));
        }
    }
}
