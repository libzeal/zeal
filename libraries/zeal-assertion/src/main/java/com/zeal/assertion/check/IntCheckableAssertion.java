package com.zeal.assertion.check;

import com.zeal.expression.eval.primitive.IntEvaluationBooleanExpression;
import com.zeal.expression.ops.values.IntOperation;

import java.util.function.IntSupplier;

public class IntCheckableAssertion extends BaseCheckableAssertion<IntEvaluationBooleanExpression> {

    public IntCheckableAssertion(IntEvaluationBooleanExpression expression) {
        super(expression);
    }

    public int orUse(int other) {

        if (expression.isFalse()) {
            return other;
        }

        return expression.subject();
    }

    public int orUse(IntSupplier supplier) {

        if (expression.isFalse()) {
            return supplier.getAsInt();
        }

        return expression.subject();
    }

    public int or(IntOperation op) {

        int subject = expression.subject();

        if (expression.isFalse()) {
            return op.apply(subject);
        }

        return subject;
    }
}
