package io.github.libzeal.zeal.assertion.api;

import io.github.libzeal.zeal.assertion.error.PreconditionIllegalArgumentException;
import io.github.libzeal.zeal.assertion.error.PreconditionNullPointerException;
import io.github.libzeal.zeal.expression.SubjectExpression;
import io.github.libzeal.zeal.expression.evalulation.EvaluatedExpression;
import io.github.libzeal.zeal.expression.evalulation.EvaluationState;

public class Assertions {

    private Assertions() {}

    public static <T> T require(SubjectExpression<T> expression, String message) {

        if (expression == null) {
            throw new NullPointerException("Cannot evaluate null expression");
        }

        EvaluatedExpression result = expression.evaluate();
        T subject = expression.subject();

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
        return eval.state() != null && eval.state().equals(EvaluationState.FAILED);
    }
}
