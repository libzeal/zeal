package com.zeal.expression.eval;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.BooleanResult;

public interface Evaluator<S> extends BooleanExpression {

    S subject();

    static <T> Evaluator<T> not(Evaluator<T> expression) {
        return new Evaluator<T>() {

            @Override
            public BooleanResult result() {
                return expression.result().not();
            }

            @Override
            public T subject() {
                return expression.subject();
            }
        };
    }
}
