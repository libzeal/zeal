package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.api.BaseObjectValue;
import io.github.libzeal.zeal.values.api.sequence.SequenceValue;
import io.github.libzeal.zeal.values.api.sequence.SequenceValueBuilder;
import io.github.libzeal.zeal.values.api.sequence.SequenceValueBuilder.SequenceOperations;
import io.github.libzeal.zeal.values.collection.BaseIterableValue.IterableSequenceOperations;

import java.util.*;
import java.util.stream.StreamSupport;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public abstract class BaseIterableValue<T, I extends Iterable<T>,
        O extends IterableSequenceOperations<T, I>,
        E extends BaseIterableValue<T, I, O, E>>
    extends BaseObjectValue<I, E>
    implements SequenceValue<T, I, E> {

    private final O sequenceOps;

    protected BaseIterableValue(final I subject, final String name, final O sequenceOps) {
        super(subject, name);
        this.sequenceOps = requireNonNull(sequenceOps);
    }

    protected O operations() {
        return sequenceOps;
    }

    @Override
    public E isEmpty() {
        return append(
            SequenceValueBuilder.isEmpty(sequenceOps)
        );
    }

    @Override
    public E isNotEmpty() {
        return append(
            SequenceValueBuilder.isNotEmpty(sequenceOps)
        );
    }

    @Override
    public E hasLengthOf(final int length) {
        return append(
            SequenceValueBuilder.hasLengthOf(length, sequenceOps)
        );
    }

    @Override
    public E doesNotHaveLengthOf(final int length) {
        return append(
            SequenceValueBuilder.doesNotHaveLengthOf(length, sequenceOps)
        );
    }

    @Override
    public E isShorterThan(final int length) {
        return append(
            SequenceValueBuilder.isShorterThan(length, sequenceOps)
        );
    }

    @Override
    public E isShorterThanOrEqualTo(final int length) {
        return append(
            SequenceValueBuilder.isShorterThanOrEqualTo(length, sequenceOps)
        );
    }

    @Override
    public E isLongerThan(final int length) {
        return append(
            SequenceValueBuilder.isLongerThan(length, sequenceOps)
        );
    }

    @Override
    public E isLongerThanOrEqualTo(final int length) {
        return append(
            SequenceValueBuilder.isLongerThanOrEqualTo(length, sequenceOps)
        );
    }

    @Override
    public E includes(final T desired) {
        return append(
            SequenceValueBuilder.includes(desired, sequenceOps)
        );
    }

    @Override
    public E includesAll(final Collection<T> desired) {
        return append(
            SequenceValueBuilder.includesAll(desired, sequenceOps)
        );
    }

    @Override
    @SafeVarargs
    public final E includesAllOf(final T... desired) {
        return includesAll(Arrays.asList(desired));
    }

    @Override
    public E includesAny(final Collection<T> desired) {
        return append(
            SequenceValueBuilder.includesAny(desired, sequenceOps)
        );
    }

    @Override
    @SafeVarargs
    public final E includesAnyOf(final T... desired) {
        return includesAny(Arrays.asList(desired));
    }

    @Override
    public E excludes(final T desired) {
        return append(
            SequenceValueBuilder.excludes(desired, sequenceOps)
        );
    }

    @Override
    public E excludesAll(final Collection<T> desired) {
        return append(
            SequenceValueBuilder.excludesAll(desired, sequenceOps)
        );
    }

    @Override
    @SafeVarargs
    public final E excludesAllOf(final T... desired) {
        return excludesAll(Arrays.asList(desired));
    }

    @Override
    public E excludesAny(final Collection<T> desired) {
        return append(
            SequenceValueBuilder.excludesAny(desired, sequenceOps)
        );
    }

    @Override
    @SafeVarargs
    public final E excludesAnyOf(final T... desired) {
        return excludesAny(Arrays.asList(desired));
    }

    protected static class IterableSequenceOperations<T, I extends Iterable<T>>
        implements SequenceOperations<T, I> {

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
    }
}
