package com.zeal.assertion.check;

import com.zeal.expression.eval.primitive.DoubleEvaluationBooleanExpression;
import com.zeal.expression.ops.values.DoubleOperation;

import java.util.function.DoubleSupplier;

public class DoubleCheckableAssertion extends BaseCheckableAssertion<DoubleEvaluationBooleanExpression> {

    public DoubleCheckableAssertion(DoubleEvaluationBooleanExpression expression) {
        super(expression);
    }

    public double orUse(double other) {

        if (expression.isFalse()) {
            return other;
        }

        return expression.subject();
    }

    public double orUse(DoubleSupplier supplier) {

        if (expression.isFalse()) {
            return supplier.getAsDouble();
        }

        return expression.subject();
    }

    public double or(DoubleOperation op) {

        double subject = expression.subject();

        if (expression.isFalse()) {
            return op.apply(subject);
        }

        return subject;
    }
}
