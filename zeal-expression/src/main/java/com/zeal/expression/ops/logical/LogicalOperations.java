package com.zeal.expression.ops.logical;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.eval.Evaluator;
import com.zeal.expression.eval.primitive.DoubleEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.FloatEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.IntEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.LongEvaluationBooleanExpression;

public final class LogicalOperations {

    private LogicalOperations() {}

    public static BooleanExpression not(BooleanExpression expression) {
        return new NotOperation(expression);
    }

    public static BooleanExpression and(BooleanExpression first, BooleanExpression second, BooleanExpression... remaining) {
        return new AndOperation(toArray(first, second, remaining));
    }

    private static BooleanExpression[] toArray(BooleanExpression first, BooleanExpression second,
                                               BooleanExpression... remaining) {

        BooleanExpression[] array = new BooleanExpression[remaining.length + 2];

        array[0] = first;
        array[1] = second;

        System.arraycopy(remaining, 0, array, 2, remaining.length);

        return array;
    }

    public static BooleanExpression or(BooleanExpression first, BooleanExpression second, BooleanExpression... remaining) {
        return new OrOperation(toArray(first, second, remaining));
    }

    public static <S> Evaluator<S> not(Evaluator<S> expression) {
        return Evaluator.not(expression);
    }

    public static FloatEvaluationBooleanExpression not(FloatEvaluationBooleanExpression expression) {
        return FloatEvaluationBooleanExpression.not(expression);
    }

    public static DoubleEvaluationBooleanExpression not(DoubleEvaluationBooleanExpression expression) {
        return DoubleEvaluationBooleanExpression.not(expression);
    }

    public static IntEvaluationBooleanExpression not(IntEvaluationBooleanExpression expression) {
        return IntEvaluationBooleanExpression.not(expression);
    }

    public static LongEvaluationBooleanExpression not(LongEvaluationBooleanExpression expression) {
        return LongEvaluationBooleanExpression.not(expression);
    }
}
