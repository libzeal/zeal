package com.zeal.expression.api.ops;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.eval.Evaluator;
import com.zeal.expression.eval.primitive.DoubleEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.FloatEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.IntEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.LongEvaluationBooleanExpression;
import com.zeal.expression.ops.logical.*;

public final class LogicalOperations {

    private LogicalOperations() {}

    public static BooleanExpression not(BooleanExpression expression) {
        return new NotOperation(expression);
    }

    public static BooleanExpression and(BooleanExpression a, BooleanExpression b) {
        return new BinaryAndOperation(a, b);
    }

    public static BooleanExpression and(BooleanExpression first, BooleanExpression second, BooleanExpression... remaining) {
        return new CompoundAndOperation(first, second, remaining);
    }

    public static BooleanExpression or(BooleanExpression a, BooleanExpression b) {
        return new BinaryOrOperation(a, b);
    }

    public static BooleanExpression or(BooleanExpression first, BooleanExpression second, BooleanExpression... remaining) {
        return new CompoundOrOperation(first, second, remaining);
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
