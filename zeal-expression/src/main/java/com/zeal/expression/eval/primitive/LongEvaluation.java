package com.zeal.expression.eval.primitive;

import com.zeal.expression.BooleanExpression;

public interface LongEvaluation {

    BooleanExpression evaluate(long subject);

    static LongEvaluation of(LongToBooleanFunction func) {
        return o -> BooleanExpression.of(() -> func.apply(o));
    }

    @FunctionalInterface
    static interface LongToBooleanFunction {
        boolean apply(long d);
    }
}
