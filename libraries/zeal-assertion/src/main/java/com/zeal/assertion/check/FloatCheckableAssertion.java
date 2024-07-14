package com.zeal.assertion.check;

import com.zeal.expression.eval.primitive.FloatEvaluationBooleanExpression;
import com.zeal.expression.ops.values.FloatOperation;

public class FloatCheckableAssertion extends BaseCheckableAssertion<FloatEvaluationBooleanExpression> {

    public FloatCheckableAssertion(FloatEvaluationBooleanExpression expression) {
        super(expression);
    }

    public float orUse(float other) {

        if (expression.isFalse()) {
            return other;
        }

        return expression.subject();
    }

    public float orUse(FloatSupplier supplier) {

        if (expression.isFalse()) {
            return supplier.getAsFloat();
        }

        return expression.subject();
    }

    public float or(FloatOperation op) {

        float subject = expression.subject();

        if (expression.isFalse()) {
            return op.apply(subject);
        }

        return subject;
    }
}
