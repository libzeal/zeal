package com.zeal.assertion.check;

import com.zeal.expression.eval.Evaluator;

import java.util.function.Function;
import java.util.function.Supplier;

public class ObjectCheckableAssertion<S> extends BaseCheckableAssertion<Evaluator<S>> {

    public ObjectCheckableAssertion(Evaluator<S> expression) {
        super(expression);
    }

    public S orUse(S other) {

        if (expression.isFalse()) {
            return other;
        }

        return expression.subject();
    }

    public S orUse(Supplier<S> supplier) {

        if (expression.isFalse()) {
            return supplier.get();
        }

        return expression.subject();
    }

    public S or(Function<S, S> op) {

        S subject = expression.subject();

        if (expression.isFalse()) {
            return op.apply(subject);
        }

        return subject;
    }
}
