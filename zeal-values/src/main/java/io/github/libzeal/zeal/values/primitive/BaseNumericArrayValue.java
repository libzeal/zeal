package io.github.libzeal.zeal.values.primitive;

import io.github.libzeal.zeal.logic.unary.Condition;
import io.github.libzeal.zeal.values.sdk.sequence.BaseOrderedSequenceValue;
import org.jspecify.annotations.Nullable;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

import static io.github.libzeal.zeal.logic.format.ObjectFormatter.stringify;
import static java.util.Objects.requireNonNull;

/**
 * Value containing checks shared by all numeric array values.
 * <p>
 * Comparison is performed using the supplied comparator.
 *
 * @param <A>
 *         The type of the array (such as {@code int[]}).
 * @param <T>
 *         The type of the elements in the array (such as {@link Integer}).
 * @param <V>
 *         The implementation type (the value type) used for fluent interface
 *         return values.
 *
 * @author Justin Albano
 */
abstract class BaseNumericArrayValue<A, T extends Number, V extends BaseNumericArrayValue<A, T, V>>
        extends BaseOrderedSequenceValue<A, T, V> {

    private final PrimitiveArrayOperations<T, A> operations;
    private final Comparator<? super T> order;

    BaseNumericArrayValue(
            final @Nullable A subject,
            final String name,
            final PrimitiveArrayOperations<T, A> operations,
            final Comparator<? super T> order
    ) {
        super(subject, name, operations);
        this.operations = requireNonNull(operations);
        this.order = requireNonNull(order);
    }

    final PrimitiveArrayOperations<T, A> operations() {
        return operations;
    }

    /**
     * Checks if the value (array) is sorted in ascending order. Duplicates are
     * allowed for this check.
     *
     * @return This value (fluent interface).
     */
    public V isAscending() {
        return append(isAscendingCondition());
    }

    private Condition<A> isAscendingCondition() {
        return observing(this::firstDescent)
                .passesIf(Optional::isEmpty)
                .name("isAscending")
                .expected("ascending")
                .actual(context -> context.ifPassedOrElse("ascending", "not ascending"))
                .hint(context -> context.observed()
                        .map(pair -> pair.describe("greater"))
                        .orElse("All elements are in ascending order")
                )
                .build();
    }

    /**
     * Checks if the value (array) is sorted in descending order. Duplicates are
     * allowed for this check.
     *
     * @return This value (fluent interface).
     */
    public V isDescending() {
        return append(isDescendingCondition());
    }

    private Condition<A> isDescendingCondition() {
        return observing(this::firstAscent)
                .passesIf(Optional::isEmpty)
                .name("isDescending")
                .expected("descending")
                .actual(context -> context.ifPassedOrElse("descending", "not descending"))
                .hint(context -> context.observed()
                        .map(pair -> pair.describe("less"))
                        .orElse("All elements are in descending order")
                )
                .build();
    }

    final V minimumIs(final String expected, final Predicate<? super T> matches) {
        return append(minIsCondition(expected, matches));
    }

    private Condition<A> minIsCondition(final String expected, final Predicate<? super T> matches) {
        return observing(this::min)
                .passesIf(min -> min.filter(matches).isPresent())
                .name("minIs[" + expected + "]")
                .expected("min = " + expected)
                .actual(context -> context.observed()
                        .map(min -> "min = " + stringify(min))
                        .orElse("empty")
                )
                .hint(context -> {
                    if (context.passed()) {
                        return "The minimum element is " + expected;
                    }
                    else if (context.observed().isPresent()) {
                        return "The minimum element should be " + expected;
                    }
                    else {
                        return "An empty array has no minimum element";
                    }
                })
                .build();
    }

    private Optional<T> min(final A array) {
        return smallest(array, order);
    }

    final V maximumIs(final String expected, final Predicate<? super T> matches) {
        return append(maxIsCondition(expected, matches));
    }

    private Condition<A> maxIsCondition(final String expected, final Predicate<? super T> matches) {
        return observing(this::max)
                .passesIf(max -> max.filter(matches).isPresent())
                .name("maxIs[" + expected + "]")
                .expected("max = " + expected)
                .actual(context -> context.observed()
                        .map(max -> "max = " + stringify(max))
                        .orElse("empty")
                )
                .hint(context -> {
                    if (context.passed()) {
                        return "The maximum element is " + expected;
                    }
                    else if (context.observed().isPresent()) {
                        return "The maximum element should be " + expected;
                    }
                    else {
                        return "An empty array has no maximum element";
                    }
                })
                .build();
    }

    private Optional<T> max(final A array) {
        return smallest(array, order.reversed());
    }

    private Optional<T> smallest(final A array, final Comparator<? super T> comparator) {

        final int size = operations.size(array);

        if (size == 0) {
            return Optional.empty();
        }

        T smallest = operations.elementAt(array, 0);

        for (int i = 1; i < size; i++) {

            final T current = operations.elementAt(array, i);

            if (comparator.compare(current, smallest) < 0) {
                smallest = current;
            }
        }

        return Optional.of(smallest);
    }

    private Optional<OutOfOrder<T>> firstDescent(final A array) {
        return firstOutOfOrder(array, order);
    }

    private Optional<OutOfOrder<T>> firstAscent(final A array) {
        return firstOutOfOrder(array, order.reversed());
    }

    /**
     * Finds the first element that is greater than its next element using the
     * supplied comparator.
     *
     * @param array
     *         The array to search for the out-of-order element.
     * @param comparator
     *         The comparator used to compare each subsequent element.
     *
     * @return An {@link Optional} populated with the first out-of-order element
     * if an out-of-order element is found; an empty {@link Optional}
     * otherwise.
     */
    private Optional<OutOfOrder<T>> firstOutOfOrder(final A array, final Comparator<? super T> comparator) {

        for (int i = 0; i + 1 < operations.size(array); i++) {

            final T current = operations.elementAt(array, i);
            final T next = operations.elementAt(array, i + 1);

            if (comparator.compare(current, next) > 0) {
                return Optional.of(new OutOfOrder<>(i, current, next));
            }
        }

        return Optional.empty();
    }

    private record OutOfOrder<T>(int index, T current, T next) {

        String describe(final String comparison) {
            return "Element " + stringify(current) + " at index " + index + " is " + comparison + " than element " +
                    stringify(next) + " at index " + (index + 1);
        }
    }
}
