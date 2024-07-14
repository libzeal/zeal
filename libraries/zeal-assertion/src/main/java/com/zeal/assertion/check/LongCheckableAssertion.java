package com.zeal.assertion.check;

import com.zeal.expression.ops.values.LongOperation;
import com.zeal.expression.eval.primitive.LongEvaluationBooleanExpression;

import java.util.function.LongSupplier;

public class LongCheckableAssertion extends BaseCheckableAssertion<LongEvaluationBooleanExpression> {

    public LongCheckableAssertion(LongEvaluationBooleanExpression expression) {
        super(expression);
    }

    public long orUse(int other) {

        if (expression.isFalse()) {
            return other;
        }

        return expression.subject();
    }

    public long orUse(LongSupplier supplier) {

        if (expression.isFalse()) {
            return supplier.getAsLong();
        }

        return expression.subject();
    }

    public long or(LongOperation op) {

        long subject = expression.subject();

        if (expression.isFalse()) {
            return op.apply(subject);
        }

        return subject;
    }
}
