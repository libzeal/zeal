package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.api.BaseObjectValue;
import io.github.libzeal.zeal.values.api.sequence.InspectableSequenceValue;
import io.github.libzeal.zeal.values.api.sequence.RepeatableSequenceValue;
import io.github.libzeal.zeal.values.api.sequence.SizedSequenceValue;
import io.github.libzeal.zeal.values.api.sequence.builder.InspectableSequenceValueBuilder;
import io.github.libzeal.zeal.values.api.sequence.builder.InspectableSequenceValueBuilder.InspectableSequenceOperations;
import io.github.libzeal.zeal.values.api.sequence.builder.RepeatableSequenceValueBuilder;
import io.github.libzeal.zeal.values.api.sequence.builder.RepeatableSequenceValueBuilder.RepeatableSequenceOperations;
import io.github.libzeal.zeal.values.api.sequence.builder.SizedSequenceValueBuilder;
import io.github.libzeal.zeal.values.api.sequence.builder.SizedSequenceValueBuilder.SizedSequenceOperations;
import io.github.libzeal.zeal.values.collection.BaseIterableValue.IterableSequenceOperations;

import java.util.Arrays;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

public abstract class BaseIterableValue<
        T,
        I extends Iterable<T>,
        O extends IterableSequenceOperations<T, I>,
        V extends BaseIterableValue<T, I, O, V>
    >
    extends BaseObjectValue<I, V>
    implements SizedSequenceValue<I, V>, InspectableSequenceValue<T, V>, RepeatableSequenceValue<T, V> {

    private final O operations;

    protected BaseIterableValue(final I subject, final String name, final O operations) {
        super(subject, name);
        this.operations = requireNonNull(operations);
    }

    @Override
    public V isEmpty() {
        return append(
            SizedSequenceValueBuilder.isEmpty(operations())
        );
    }

    @Override
    public V isNotEmpty() {
        return append(
            SizedSequenceValueBuilder.isNotEmpty(operations())
        );
    }

    @Override
    public V hasLengthOf(final int length) {
        return append(
            SizedSequenceValueBuilder.hasLengthOf(length, operations())
        );
    }

    @Override
    public V doesNotHaveLengthOf(final int length) {
        return append(
            SizedSequenceValueBuilder.doesNotHaveLengthOf(length, operations())
        );
    }

    @Override
    public V isShorterThan(final int length) {
        return append(
            SizedSequenceValueBuilder.isShorterThan(length, operations())
        );
    }

    @Override
    public V isShorterThanOrEqualTo(final int length) {
        return append(
            SizedSequenceValueBuilder.isShorterThanOrEqualTo(length, operations())
        );
    }

    @Override
    public V isLongerThan(final int length) {
        return append(
            SizedSequenceValueBuilder.isLongerThan(length, operations())
        );
    }

    @Override
    public V isLongerThanOrEqualTo(final int length) {
        return append(
            SizedSequenceValueBuilder.isLongerThanOrEqualTo(length, operations())
        );
    }

    @Override
    public V includes(final T desired) {
        return append(
            InspectableSequenceValueBuilder.includes(desired, operations())
        );
    }

    @Override
    public V includesAll(final Collection<T> desired) {
        return append(
            InspectableSequenceValueBuilder.includesAll(desired, operations())
        );
    }

    @Override
    @SafeVarargs
    public final V includesAllOf(final T... desired) {
        return includesAll(Arrays.asList(desired));
    }

    @Override
    public V includesAny(final Collection<T> desired) {
        return append(
            InspectableSequenceValueBuilder.includesAny(desired, operations())
        );
    }

    @Override
    @SafeVarargs
    public final V includesAnyOf(final T... desired) {
        return includesAny(Arrays.asList(desired));
    }

    @Override
    public V excludes(final T desired) {
        return append(
            InspectableSequenceValueBuilder.excludes(desired, operations())
        );
    }

    @Override
    public V excludesAll(final Collection<T> desired) {
        return append(
            InspectableSequenceValueBuilder.excludesAll(desired, operations())
        );
    }

    @Override
    @SafeVarargs
    public final V excludesAllOf(final T... desired) {
        return excludesAll(Arrays.asList(desired));
    }

    @Override
    public V excludesAny(final Collection<T> desired) {
        return append(
            InspectableSequenceValueBuilder.excludesAny(desired, operations())
        );
    }

    @Override
    @SafeVarargs
    public final V excludesAnyOf(final T... desired) {
        return excludesAny(Arrays.asList(desired));
    }

    @Override
    public V includesExactly(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesExactly(desired, times, operations())
        );
    }

    @Override
    public V includesMoreThan(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesMoreThan(desired, times, operations())
        );
    }

    @Override
    public V includesMoreThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesMoreThanOrEqualTo(desired, times, operations())
        );
    }

    @Override
    public V includesLessThan(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesLessThan(desired, times, operations())
        );
    }

    @Override
    public V includesLessThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceValueBuilder.includesLessThanOrEqualTo(desired, times, operations())
        );
    }

    protected final O operations() {
        return operations;
    }

    protected interface IterableSequenceOperations<T, I extends Iterable<T>>
        extends SizedSequenceOperations<I>,
        InspectableSequenceOperations<T, I>, RepeatableSequenceOperations<T, I> {
    }
}
