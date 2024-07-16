package com.zeal.expression.eval.primitive;

import com.zeal.expression.BooleanExpression;

public interface FloatEvaluationBooleanExpression extends BooleanExpression {

    float subject();

    static FloatEvaluationBooleanExpression not(FloatEvaluationBooleanExpression expression) {
        return new FloatEvaluationBooleanExpression() {

            @Override
            public boolean isTrue() {
                return expression.isFalse();
            }

            @Override
            public float subject() {
                return expression.subject();
            }
        };
    }
}
