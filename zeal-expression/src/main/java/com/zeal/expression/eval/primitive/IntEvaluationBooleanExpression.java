package com.zeal.expression.eval.primitive;

import com.zeal.expression.BooleanExpression;

public interface IntEvaluationBooleanExpression extends BooleanExpression {

    int subject();

    static IntEvaluationBooleanExpression not(IntEvaluationBooleanExpression expression) {
        return new IntEvaluationBooleanExpression() {

            @Override
            public boolean isTrue() {
                return expression.isFalse();
            }

            @Override
            public int subject() {
                return expression.subject();
            }
        };
    }
}
