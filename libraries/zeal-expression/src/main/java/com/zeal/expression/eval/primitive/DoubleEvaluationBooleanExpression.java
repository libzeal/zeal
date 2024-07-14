package com.zeal.expression.eval.primitive;

import com.zeal.expression.BooleanExpression;

public interface DoubleEvaluationBooleanExpression extends BooleanExpression {

    double subject();

    static DoubleEvaluationBooleanExpression not(DoubleEvaluationBooleanExpression expression) {
        return new DoubleEvaluationBooleanExpression() {

            @Override
            public boolean isTrue() {
                return expression.isFalse();
            }

            @Override
            public double subject() {
                return expression.subject();
            }
        };
    }
}
