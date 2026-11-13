package io.github.libzeal.zeal.values.sdk.sequence;

import io.github.libzeal.zeal.logic.unary.Condition;
import io.github.libzeal.zeal.logic.unary.ConditionBuilder;
import io.github.libzeal.zeal.logic.unary.ObservableUnaryExpression.Field;
import io.github.libzeal.zeal.values.sdk.StandardRationales.Operators;

import java.util.function.ToIntFunction;

import static io.github.libzeal.zeal.values.sdk.StandardRationales.Names.withValue;

/**
 * A set of common conditions for sized values. These conditions are used by
 * the supported first-party sized values and is also intended for use by
 * third-party sized values.
 *
 * @author Justin Albano
 */
public final class SizeConditions {

    private static final String SIZE = "size";

    private SizeConditions() {
    }

    /**
     * Creates a condition that checks if a value is empty.
     *
     * @param size
     *         The function used to obtain the size of the value.
     * @param <S>
     *         The type of the value.
     *
     * @return A condition that checks if a value is empty.
     */
    public static <S> Condition<S> isEmpty(final ToIntFunction<? super S> size) {
        return ConditionBuilder.observing((S s) -> size.applyAsInt(s))
                .passesIf(observed -> observed == 0)
                .name("isEmpty")
                .expected(Operators.EQ.display(SIZE, 0))
                .actual(actualSize())
                .build();
    }

    private static <S> Field<S, Integer> actualSize() {
        return context -> Operators.EQ.display(SIZE, context.observed());
    }

    /**
     * Creates a condition that checks if a value is not empty.
     *
     * @param size
     *         The function used to obtain the size of the value.
     * @param <S>
     *         The type of the value.
     *
     * @return A condition that checks if a value is not empty.
     */
    public static <S> Condition<S> isNotEmpty(final ToIntFunction<? super S> size) {
        return ConditionBuilder.observing((S s) -> size.applyAsInt(s))
                .passesIf(observed -> observed > 0)
                .name("isNotEmpty")
                .expected(Operators.GT.display(SIZE, 0))
                .actual(actualSize())
                .build();
    }

    /**
     * Creates a condition that checks if a value has the supplied size.
     *
     * @param expected
     *         The desired size.
     * @param size
     *         The function used to obtain the size of the value.
     * @param <S>
     *         The type of the value.
     *
     * @return A condition that checks if a value has the supplied size.
     */
    public static <S> Condition<S> hasSizeOf(final int expected, final ToIntFunction<? super S> size) {
        return ConditionBuilder.observing((S s) -> size.applyAsInt(s))
                .passesIf(observed -> observed == expected)
                .name(withValue("hasSizeOf", expected))
                .expected(Operators.EQ.display(SIZE, expected))
                .actual(actualSize())
                .build();
    }

    /**
     * Creates a condition that checks if a value does not have the supplied
     * size.
     *
     * @param expected
     *         The undesired size.
     * @param size
     *         The function used to obtain the size of the value.
     * @param <S>
     *         The type of the value.
     *
     * @return A condition that checks if a value does not have the supplied
     * size.
     */
    public static <S> Condition<S> doesNotHaveSizeOf(final int expected, final ToIntFunction<? super S> size) {
        return ConditionBuilder.observing((S s) -> size.applyAsInt(s))
                .passesIf(observed -> observed != expected)
                .name(withValue("doesNotHaveSizeOf", expected))
                .expected(Operators.NE.display(SIZE, expected))
                .actual(actualSize())
                .build();
    }

    /**
     * Creates a condition that checks if a value is shorter than the supplied
     * size.
     *
     * @param expected
     *         The desired upper bound.
     * @param size
     *         The function used to obtain the size of the value.
     * @param <S>
     *         The type of the value.
     *
     * @return A condition that checks if a value is shorter than the supplied
     * size.
     */
    public static <S> Condition<S> isShorterThan(final int expected, final ToIntFunction<? super S> size) {
        return ConditionBuilder.observing((S s) -> size.applyAsInt(s))
                .passesIf(observed -> observed < expected)
                .name(withValue("isShorterThan", expected))
                .expected(Operators.LT.display(SIZE, expected))
                .actual(actualSize())
                .build();
    }

    /**
     * Creates a condition that checks if a value is shorter than or equal to
     * the supplied size.
     *
     * @param expected
     *         The desired upper bound.
     * @param size
     *         The function used to obtain the size of the value.
     * @param <S>
     *         The type of the value.
     *
     * @return A condition that checks if a value is shorter than or equal to
     * the supplied size.
     */
    public static <S> Condition<S> isShorterThanOrEqualTo(final int expected, final ToIntFunction<? super S> size) {
        return ConditionBuilder.observing((S s) -> size.applyAsInt(s))
                .passesIf(observed -> observed <= expected)
                .name(withValue("isShorterThanOrEqualTo", expected))
                .expected(Operators.LTE.display(SIZE, expected))
                .actual(actualSize())
                .build();
    }

    /**
     * Creates a condition that checks if a value is longer than the supplied
     * size.
     *
     * @param expected
     *         The desired lower bound.
     * @param size
     *         The function used to obtain the size of the value.
     * @param <S>
     *         The type of the value.
     *
     * @return A condition that checks if a value is longer than the supplied
     * size.
     */
    public static <S> Condition<S> isLongerThan(final int expected, final ToIntFunction<? super S> size) {
        return ConditionBuilder.observing((S s) -> size.applyAsInt(s))
                .passesIf(observed -> observed > expected)
                .name(withValue("isLongerThan", expected))
                .expected(Operators.GT.display(SIZE, expected))
                .actual(actualSize())
                .build();
    }

    /**
     * Creates a condition that checks if a value is longer than or equal to
     * the supplied size.
     *
     * @param expected
     *         The desired lower bound.
     * @param size
     *         The function used to obtain the size of the value.
     * @param <S>
     *         The type of the value.
     *
     * @return A condition that checks if a value is longer than or equal to
     * the supplied size.
     */
    public static <S> Condition<S> isLongerThanOrEqualTo(final int expected, final ToIntFunction<? super S> size) {
        return ConditionBuilder.observing((S s) -> size.applyAsInt(s))
                .passesIf(observed -> observed >= expected)
                .name(withValue("isLongerThanOrEqualTo", expected))
                .expected(Operators.GTE.display(SIZE, expected))
                .actual(actualSize())
                .build();
    }
}
