package com.zeal.expression.eval.base;

import com.zeal.expression.api.eval.ObjectEvaluations;
import com.zeal.expression.eval.Evaluation;

import java.util.function.Predicate;

public abstract class BaseObjectEvaluator<S, E extends BaseObjectEvaluator<S, E>>
        extends ChainedEvaluator<S, E> {

    public BaseObjectEvaluator(S value) {
        super(value);
    }

    public E isNull() {
        return satisfies(ObjectEvaluations.isNull());
    }

    public E isNotNull() {
        return satisfies(ObjectEvaluations.isNotNull());
    }

    public E hasType(Class<?> type) {
        return satisfies(ObjectEvaluations.hasType(type));
    }

    public E isInstanceOf(Class<?> type) {
        return satisfies(ObjectEvaluations.isInstanceOf(type));
    }

    public E is(Object other) {
        return satisfies(ObjectEvaluations.is(other));
    }

    public E isEqualTo(Object other) {
        return satisfies(ObjectEvaluations.isEqualTo(other));
    }

    public E hasHashCode(int hashCode) {
        return satisfies(ObjectEvaluations.hasHashCode(hashCode));
    }

    public E satisfies(Predicate<S> predicate) {
        return chain(predicate::test);
    }

    public E satisfies(Evaluation<S> evaluation) {
        return chain(evaluation);
    }
}
