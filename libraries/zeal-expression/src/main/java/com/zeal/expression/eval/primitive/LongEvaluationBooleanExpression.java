package com.zeal.expression.eval.primitive;

import com.zeal.expression.BooleanExpression;

public interface LongEvaluationBooleanExpression extends BooleanExpression {

    long subject();

    static LongEvaluationBooleanExpression not(LongEvaluationBooleanExpression expression) {
        return new LongEvaluationBooleanExpression() {

            @Override
            public boolean isTrue() {
                return expression.isFalse();
            }

            @Override
            public long subject() {
                return expression.subject();
            }
        };
    }
}
