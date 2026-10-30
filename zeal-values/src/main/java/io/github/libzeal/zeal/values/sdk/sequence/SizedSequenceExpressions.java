package io.github.libzeal.zeal.values.sdk.sequence;

import io.github.libzeal.zeal.logic.unary.Condition;
import io.github.libzeal.zeal.logic.unary.ConditionBuilder;
import io.github.libzeal.zeal.logic.unary.ObservableUnaryExpression.Field;
import io.github.libzeal.zeal.values.sdk.StandardRationales.Operators;

import static io.github.libzeal.zeal.values.sdk.StandardRationales.Names.withValue;

public class SizedSequenceExpressions {

    private static final String LENGTH = "length";

    public interface SizedSequenceOperations<S> {
        int size(S haystack);
    }

    public static <S> Condition<S> isEmpty(final SizedSequenceOperations<S> ops) {
        return ConditionBuilder.observing(ops::size)
                .passesIf(size -> size == 0)
                .name("isEmpty")
                .expected(Operators.EQ.display(LENGTH, 0))
                .actual(actualLength())
                .build();
    }

    private static <S> Field<S, Integer> actualLength() {
        return context -> Operators.EQ.display(LENGTH, context.observed());
    }

    public static <S> Condition<S> isNotEmpty(final SizedSequenceOperations<S> ops) {
        return ConditionBuilder.observing(ops::size)
                .passesIf(size -> size > 0)
                .name("isNotEmpty")
                .expected(Operators.GT.display(LENGTH, 0))
                .actual(actualLength())
                .build();
    }

    public static <S> Condition<S> hasLengthOf(final int length, final SizedSequenceOperations<S> ops) {
        return ConditionBuilder.observing(ops::size)
                .passesIf(size -> size == length)
                .name("hasLengthOf[" + length + "]")
                .expected(Operators.EQ.display(LENGTH, length))
                .actual(actualLength())
                .build();
    }

    public static <S> Condition<S> doesNotHaveLengthOf(final int length, final SizedSequenceOperations<S> ops) {
        return ConditionBuilder.observing(ops::size)
                .passesIf(size -> size != length)
                .name(withValue("doesNotHaveLengthOf", length))
                .expected(Operators.NE.display(LENGTH, length))
                .actual(actualLength())
                .build();
    }

    public static <S> Condition<S> isShorterThan(final int length, final SizedSequenceOperations<S> ops) {
        return ConditionBuilder.observing(ops::size)
                .passesIf(size -> size < length)
                .name(withValue("isShorterThan", length))
                .expected(Operators.LT.display(LENGTH, length))
                .actual(actualLength())
                .build();
    }

    public static <S> Condition<S> isShorterThanOrEqualTo(final int length, final SizedSequenceOperations<S> ops) {
        return ConditionBuilder.observing(ops::size)
                .passesIf(size -> size <= length)
                .name(withValue("isShorterThanOrEqualTo", length))
                .expected(Operators.LTE.display(LENGTH, length))
                .actual(actualLength())
                .build();
    }

    public static <S> Condition<S> isLongerThan(final int length, final SizedSequenceOperations<S> ops) {
        return ConditionBuilder.observing(ops::size)
                .passesIf(size -> size > length)
                .name(withValue("isLongerThan", length))
                .expected(Operators.GT.display(LENGTH, length))
                .actual(actualLength())
                .build();
    }

    public static <S> Condition<S> isLongerThanOrEqualTo(final int length, final SizedSequenceOperations<S> ops) {
        return ConditionBuilder.observing(ops::size)
                .passesIf(size -> size >= length)
                .name(withValue("isLongerThanOrEqualTo", length))
                .expected(Operators.GTE.display(LENGTH, length))
                .actual(actualLength())
                .build();
    }
}
