package com.zeal.expression.api.eval;

import com.zeal.expression.eval.base.BaseObjectEvaluator;

public final class ObjectEvaluator<S> extends BaseObjectEvaluator<S,
        ObjectEvaluator<S>> {

    public ObjectEvaluator(S value) {
        super(value);
    }
}
