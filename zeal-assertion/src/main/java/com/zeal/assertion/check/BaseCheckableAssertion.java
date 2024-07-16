package com.zeal.assertion.check;

import com.zeal.expression.BooleanExpression;

import java.util.function.Supplier;

public abstract class BaseCheckableAssertion<E extends BooleanExpression> implements CheckableAssertion {

    protected final E expression;

    public BaseCheckableAssertion(E expression) {
        this.expression = expression;
    }

    @Override
    public <T extends Throwable> void orThrow(Supplier<T> throwable) throws T {

        if (expression.isFalse()) {
            throw throwable.get();
        }
    }
}
