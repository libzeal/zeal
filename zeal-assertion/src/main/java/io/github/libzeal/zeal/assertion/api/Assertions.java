package io.github.libzeal.zeal.assertion.api;

import io.github.libzeal.zeal.assertion.error.PreconditionIllegalArgumentException;
import io.github.libzeal.zeal.assertion.error.PreconditionNullPointerException;
import io.github.libzeal.zeal.expression.UnaryExpression;
import io.github.libzeal.zeal.expression.evaluation.EvaluatedExpression;
import io.github.libzeal.zeal.expression.evaluation.Result;

public class Assertions {

    private Assertions() {}

    public static <T> T require(UnaryExpression<T> expression) {
        return require(expression, "Precondition failed");
    }

    public static <T> T require(UnaryExpression<T> expression, String message) {

        if (expression == null) {
            throw new NullPointerException("Cannot evaluate null expression");
        }

        final EvaluatedExpression result = expression.evaluate();
        final T subject = expression.subject();

        if (isFailed(result)) {

            if (subject == null) {
                throw new PreconditionNullPointerException(result, message);
            }
            else {
                throw new PreconditionIllegalArgumentException(result, message);
            }
        }
        else {
            return subject;
        }
    }

    private static boolean isFailed(EvaluatedExpression eval) {
        return eval.result() != null && eval.result().equals(Result.FAILED);
    }
}
