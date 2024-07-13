package com.zeal.expression.subject.values;

import com.zeal.expression.subject.SingleSubjectBooleanExpression;
import com.zeal.expression.subject.sdk.BaseObjectEvaluator;
import com.zeal.expression.subject.sdk.ChainedEvaluator;

import java.util.Objects;
import java.util.function.Predicate;

public final class ObjectEvaluator<S>
        extends BaseObjectEvaluator<S, ObjectEvaluator<S>> {

    public ObjectEvaluator(S value) {
        super(value);
    }
}
