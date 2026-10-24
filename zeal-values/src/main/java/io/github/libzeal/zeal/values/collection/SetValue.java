package io.github.libzeal.zeal.values.collection;

import io.github.libzeal.zeal.values.sdk.BaseValue;
import io.github.libzeal.zeal.values.sdk.sequence.InspectableSequenceExpressions;
import io.github.libzeal.zeal.values.sdk.sequence.RepeatableSequenceExpressions;
import io.github.libzeal.zeal.values.sdk.sequence.SizedSequenceExpressions;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class SetValue<T> extends BaseValue<Set<T>, SetValue<T>>
    implements CollectionValue<T, Set<T>, SetValue<T>> {

    private final SetOperations<T> operations;

    public SetValue(final Set<T> subject) {
        super(subject, "Set value");
        this.operations = new SetOperations<>();
    }

    // TODO Optimize these to short circuit, since Sets have unique values

    @Override
    public SetValue<T> includesExactly(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesExactly(desired, times, operations)
        );
    }

    @Override
    public SetValue<T> includesMoreThan(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesMoreThan(desired, times, operations)
        );
    }

    @Override
    public SetValue<T> includesMoreThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesMoreThanOrEqualTo(desired, times, operations)
        );
    }

    @Override
    public SetValue<T> includesLessThan(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesLessThan(desired, times, operations)
        );
    }

    @Override
    public SetValue<T> includesLessThanOrEqualTo(final T desired, final long times) {
        return append(
            RepeatableSequenceExpressions.includesLessThanOrEqualTo(desired, times, operations)
        );
    }

    @Override
    public SetValue<T> isEmpty() {
        return append(
            SizedSequenceExpressions.isEmpty(operations)
        );
    }

    @Override
    public SetValue<T> isNotEmpty() {
        return append(
            SizedSequenceExpressions.isNotEmpty(operations)
        );
    }

    @Override
    public SetValue<T> hasLengthOf(final int length) {
        return append(
            SizedSequenceExpressions.hasLengthOf(length, operations)
        );
    }

    @Override
    public SetValue<T> doesNotHaveLengthOf(final int length) {
        return append(
            SizedSequenceExpressions.doesNotHaveLengthOf(length, operations)
        );
    }

    @Override
    public SetValue<T> isShorterThan(final int length) {
        return append(
            SizedSequenceExpressions.isShorterThan(length, operations)
        );
    }

    @Override
    public SetValue<T> isShorterThanOrEqualTo(final int length) {
        return append(
            SizedSequenceExpressions.isShorterThanOrEqualTo(length, operations)
        );
    }

    @Override
    public SetValue<T> isLongerThan(final int length) {
        return append(
            SizedSequenceExpressions.isLongerThan(length, operations)
        );
    }

    @Override
    public SetValue<T> isLongerThanOrEqualTo(final int length) {
        return append(
            SizedSequenceExpressions.isLongerThanOrEqualTo(length, operations)
        );
    }

    @Override
    public SetValue<T> includes(final T desired) {
        return append(
            InspectableSequenceExpressions.includes(desired, operations)
        );
    }

    @Override
    public SetValue<T> includesAll(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.includesAll(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final SetValue<T> includesAllOf(final T... desired) {
        return includesAll(Arrays.asList(desired));
    }

    @Override
    public SetValue<T> includesAny(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.includesAny(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final SetValue<T> includesAnyOf(final T... desired) {
        return includesAny(Arrays.asList(desired));
    }

    @Override
    public SetValue<T> excludes(final T desired) {
        return append(
            InspectableSequenceExpressions.excludes(desired, operations)
        );
    }

    @Override
    public SetValue<T> excludesAll(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.excludesAll(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final SetValue<T> excludesAllOf(final T... desired) {
        return excludesAll(Arrays.asList(desired));
    }

    @Override
    public SetValue<T> excludesAny(final Collection<T> desired) {
        return append(
            InspectableSequenceExpressions.excludesAny(desired, operations)
        );
    }

    @Override
    @SafeVarargs
    public final SetValue<T> excludesAnyOf(final T... desired) {
        return excludesAny(Arrays.asList(desired));
    }

    private static final class SetOperations<T>
        implements CollectionOperations<T, Set<T>> {

        @Override
        public int occurrences(final Set<T> haystack, final T needle) {
            return Collections.frequency(haystack, needle);
        }

        @Override
        public List<T> findAllIn(final Set<T> haystack, final Collection<T> needles) {
            return needles.stream()
                .filter(haystack::contains)
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
    }
}
