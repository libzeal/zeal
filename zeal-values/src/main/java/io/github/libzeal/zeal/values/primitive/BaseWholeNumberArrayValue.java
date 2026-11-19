package io.github.libzeal.zeal.values.primitive;

import io.github.libzeal.zeal.logic.unary.Condition;
import org.jspecify.annotations.Nullable;

import java.util.Comparator;
import java.util.OptionalLong;

/**
 * Value containing common whole number ({@code byte}, {@code short},
 * {@code int}, and {@code long}) array checks. All values are widened to
 * {@code long}s and all sums are computed as {@code long}s to avoid overflows
 * when possible.
 *
 * @param <A>
 *         The type of the array (such as {@code int[]}).
 * @param <T>
 *         The type of the elements of the array (such as {@code Integer}).
 * @param <V>
 *         The implementation type (the value type) used for fluent interface
 *         return values.
 *
 * @author Justin Albano
 */
abstract class BaseWholeNumberArrayValue<A, T extends Number, V extends BaseWholeNumberArrayValue<A, T, V>>
        extends BaseNumericArrayValue<A, T, V> {

    BaseWholeNumberArrayValue(final @Nullable A subject, final String name, final PrimitiveArrayOperations<T, A> operations) {
        super(subject, name, operations, Comparator.comparingLong(Number::longValue));
    }

    /**
     * Checks that the sum is equal to the supplied value.
     *
     * @param expected
     *         The expected sum.
     *
     * @return This value (fluent interface).
     */
    public V sumIs(final long expected) {
        return append(sumIsCondition(expected));
    }

    /**
     * Checks that the minimum is equal to the supplied value.
     *
     * @param expected
     *         The expected minimum.
     *
     * @return This value (fluent interface).
     */
    public V minimumIs(final long expected) {
        return minimumIs(String.valueOf(expected), element -> element.longValue() == expected);
    }

    /**
     * Checks that the maximum is equal to the supplied value.
     *
     * @param expected
     *         The expected maximum.
     *
     * @return This value (fluent interface).
     */
    public V maximumIs(final long expected) {
        return maximumIs(String.valueOf(expected), element -> element.longValue() == expected);
    }

    private Condition<A> sumIsCondition(final long expected) {
        return observing(this::sum)
                .passesIf(sum -> sum.isPresent() && sum.getAsLong() == expected)
                .name("sumIs[" + expected + "]")
                .expected("sum = " + expected)
                .actual(context -> context.observed().isPresent() ?
                        "sum = " + context.observed().getAsLong() :
                        "sum overflows a long"
                )
                .hint(context -> {
                    if (context.passed()) {
                        return "The sum of the elements is " + expected;
                    }
                    else if (context.observed().isPresent()) {
                        return "The sum of the elements should be " + expected;
                    }
                    else {
                        return "The sum of the elements is outside the range of a long";
                    }
                })
                .build();
    }

    /**
     * Computes the sum. An empty return denotes an overflowed sum. A sum can
     * only overflow for {@code long[]} arrays, since {@code int[]} will never
     * overflow a {@code long}.
     *
     * @param array
     *         The array to sum.
     *
     * @return An {@link OptionalLong} populated with the sum; an empty
     * {@link OptionalLong} if the sum overflowed.
     */
    private OptionalLong sum(final A array) {

        long sum = 0;

        try {
            for (int i = 0; i < operations().size(array); i++) {
                sum = Math.addExact(sum, operations().elementAt(array, i).longValue());
            }
        }
        catch (final ArithmeticException e) {
            return OptionalLong.empty();
        }

        return OptionalLong.of(sum);
    }
}
