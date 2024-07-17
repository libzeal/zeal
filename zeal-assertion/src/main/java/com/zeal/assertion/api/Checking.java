package com.zeal.assertion.api;

import com.zeal.assertion.check.*;
import com.zeal.expression.eval.Evaluator;
import com.zeal.expression.eval.primitive.DoubleEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.FloatEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.IntEvaluationBooleanExpression;
import com.zeal.expression.eval.primitive.LongEvaluationBooleanExpression;

public class Checking {

    private Checking() {}

    public static <S> ObjectCheckableAssertion<S> check(Evaluator<S> condition) {
        return new ObjectCheckableAssertion<>(condition);
    }

    public static IntCheckableAssertion check(IntEvaluationBooleanExpression condition) {
        return new IntCheckableAssertion(condition);
    }

    public static LongCheckableAssertion check(LongEvaluationBooleanExpression condition) {
        return new LongCheckableAssertion(condition);
    }

    public static FloatCheckableAssertion check(FloatEvaluationBooleanExpression condition) {
        return new FloatCheckableAssertion(condition);
    }

    public static DoubleCheckableAssertion check(DoubleEvaluationBooleanExpression condition) {
        return new DoubleCheckableAssertion(condition);
    }

    // TODO: [check] Add byte
    // TODO: [check] Add short
    // TODO: [check] Add char
    // TODO: [check] Add boolean
}
