package com.zeal.expression.eval.primitive;

import com.zeal.expression.BooleanExpression;

public interface DoubleEvaluation {

    BooleanExpression evaluate(double subject);

    static DoubleEvaluation of(DoubleToBooleanFunction func) {
        return o -> BooleanExpression.of(() -> func.apply(o));
    }

    @FunctionalInterface
    static interface DoubleToBooleanFunction {
        boolean apply(double d);
    }
}
