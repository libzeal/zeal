package com.zeal.expression.eval.primitive;

import com.zeal.expression.BooleanExpression;

@FunctionalInterface
public interface IntEvaluation {

    BooleanExpression evaluate(int subject);

    static IntEvaluation of(IntToBooleanFunction func) {
        return o -> BooleanExpression.of(() -> func.apply(o));
    }

    @FunctionalInterface
    static interface IntToBooleanFunction {
        boolean apply(int d);
    }
}
