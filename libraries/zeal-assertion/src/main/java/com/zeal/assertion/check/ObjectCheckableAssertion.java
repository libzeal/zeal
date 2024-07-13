package com.zeal.assertion.check;

import com.zeal.expression.ops.values.ObjectOperation;
import com.zeal.expression.subject.SingleSubjectBooleanExpression;

import java.util.function.Function;
import java.util.function.Supplier;

public class ObjectCheckableAssertion<S> {

    protected final SingleSubjectBooleanExpression<S> expression;

    public ObjectCheckableAssertion(SingleSubjectBooleanExpression<S> expression) {
        this.expression = expression;
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

    public <T extends Throwable> void orThrow(T throwable) throws T {

        if (expression.isFalse()) {
            throw throwable;
        }
    }

    public <T extends Throwable> void orThrow(Supplier<T> throwable) throws T {

        if (expression.isFalse()) {
            throw throwable.get();
        }
    }

    public <T extends Throwable> void orThrow(Function<S, T> mapper) throws T {

        if (expression.isFalse()) {
            throw mapper.apply(expression.subject());
        }
    }
}
