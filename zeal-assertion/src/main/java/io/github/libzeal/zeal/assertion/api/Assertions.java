package io.github.libzeal.zeal.assertion.api;

import io.github.libzeal.zeal.expression.SubjectExpression;
import io.github.libzeal.zeal.expression.evalulation.EvaluatedExpression;

public class Assertions {

    private Assertions() {}

    public static <T> T require(SubjectExpression<T> expression, String message) {

        if (expression == null) {
            throw new NullPointerException("Cannot evaluate null expression");
        }

        EvaluatedExpression result = expression.evaluate();

        if (result.isFailed()) {

            if (result.failsNotNullCheck()) {
                throw new NullPointerException(message);
            }
            else {
                throw new IllegalArgumentException(message);
            }
        }
        else {
            return expression.subject();
        }
    }
}
