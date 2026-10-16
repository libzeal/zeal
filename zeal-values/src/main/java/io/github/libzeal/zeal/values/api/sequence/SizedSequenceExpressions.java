package io.github.libzeal.zeal.values.api.sequence;

import io.github.libzeal.zeal.logic.unary.future.ComputableExpression;
import io.github.libzeal.zeal.logic.unary.future.rationale.ComputableField;
import io.github.libzeal.zeal.values.api.SimpleValueBuilder;
import io.github.libzeal.zeal.values.api.StandardRationales.Operators;

import static io.github.libzeal.zeal.values.api.StandardRationales.Names.withValue;

public class SizedSequenceExpressions {
    
    private static final String LENGTH = "length";

    public interface SizedSequenceOperations<S> {
        int size(S haystack);
        boolean isEmpty(S haystack);
    }

    public static <S> ComputableExpression<S> isEmpty(final SizedSequenceOperations<S> ops) {
        return SimpleValueBuilder.notNullable(ops::isEmpty)
            .name("isEmpty")
            .expected(Operators.EQ.display(LENGTH, 0))
            .actual(actualLength(ops))
            .build();
    }

    private static <S> ComputableField<S> actualLength(final SizedSequenceOperations<S> ops) {
        return context -> {
            final int size = ops.size(context.subject());
            return Operators.EQ.display(LENGTH, size);
        };
    }

    public static <S> ComputableExpression<S> isNotEmpty(final SizedSequenceOperations<S> ops) {
        return SimpleValueBuilder.notNullable((S s) -> !ops.isEmpty(s))
            .name("isNotEmpty")
            .expected(Operators.GT.display(LENGTH, 0))
            .actual(actualLength(ops))
            .build();
    }

    public static <S> ComputableExpression<S> hasLengthOf(final int length, final SizedSequenceOperations<S> ops) {
        return SimpleValueBuilder.notNullable((S s) -> ops.size(s) == length)
            .name("hasLengthOf[" + length + "]")
            .expected(Operators.EQ.display(LENGTH, length))
            .actual(actualLength(ops))
            .build();
    }

    public static <S> ComputableExpression<S> doesNotHaveLengthOf(final int length, final SizedSequenceOperations<S> ops) {
        return SimpleValueBuilder.notNullable((S s) -> ops.size(s) != length)
            .name(withValue("doesNotHaveLengthOf", length))
            .expected(Operators.NE.display(LENGTH, length))
            .actual(actualLength(ops))
            .build();
    }

    public static <S> ComputableExpression<S> isShorterThan(final int length, final SizedSequenceOperations<S> ops) {
        return SimpleValueBuilder.notNullable((S s) -> ops.size(s) < length)
            .name(withValue("isShorterThan", length))
            .expected(Operators.LT.display(LENGTH, length))
            .actual(actualLength(ops))
            .build();
    }

    public static <S> ComputableExpression<S> isShorterThanOrEqualTo(final int length, final SizedSequenceOperations<S> ops) {
        return SimpleValueBuilder.notNullable((S s) -> ops.size(s) <= length)
            .name(withValue("isShorterThanOrEqualTo", length))
            .expected(Operators.LTE.display(LENGTH, length))
            .actual(actualLength(ops))
            .build();
    }

    public static <S> ComputableExpression<S> isLongerThan(final int length, final SizedSequenceOperations<S> ops) {
        return SimpleValueBuilder.notNullable((S s) -> ops.size(s) > length)
            .name(withValue("isLongerThan", length))
            .expected(Operators.GT.display(LENGTH, length))
            .actual(actualLength(ops))
            .build();
    }

    public static <S> ComputableExpression<S> isLongerThanOrEqualTo(final int length, final SizedSequenceOperations<S> ops) {
        return SimpleValueBuilder.notNullable((S s) -> ops.size(s) >= length)
            .name(withValue("isLongerThanOrEqualTo", length))
            .expected(Operators.GTE.display(LENGTH, length))
            .actual(actualLength(ops))
            .build();
    }
}
