package com.zeal.expression.eval.primitive;

import com.zeal.expression.BooleanExpression;

public interface FloatEvaluation {

    BooleanExpression evaluate(float subject);

    static FloatEvaluation of(FloatToBooleanFunction func) {
        return o -> BooleanExpression.of(() -> func.apply(o));
    }

    @FunctionalInterface
    static interface FloatToBooleanFunction {
        boolean apply(float d);
    }
}
