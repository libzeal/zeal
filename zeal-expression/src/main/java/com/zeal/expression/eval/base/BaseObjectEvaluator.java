package com.zeal.expression.eval.base;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.eval.ObjectEvaluations;
import com.zeal.expression.eval.Evaluation;

import java.util.function.Predicate;

public abstract class BaseObjectEvaluator<S, E extends BaseObjectEvaluator<S, E>>
        extends ChainedEvaluator<S, E> {

    private boolean includesNotNullCheck;

    protected BaseObjectEvaluator(S value) {
        super(value);

        this.includesNotNullCheck = false;
    }

    public final E isNull() {
        return satisfies(ObjectEvaluations.isNull());
    }

    public final E isNotNull() {

        this.includesNotNullCheck = true;

        return satisfies(ObjectEvaluations.isNotNull());
    }

    public final E hasType(Class<?> type) {
        return satisfies(ObjectEvaluations.hasType(type));
    }

    public final E isInstanceOf(Class<?> type) {
        return satisfies(ObjectEvaluations.isInstanceOf(type));
    }

    public final E is(Object other) {
        return satisfies(ObjectEvaluations.is(other));
    }

    public final E isEqualTo(Object other) {
        return satisfies(ObjectEvaluations.isEqualTo(other));
    }

    public final E hasHashCode(int hashCode) {
        return satisfies(ObjectEvaluations.hasHashCode(hashCode));
    }

    public final E satisfies(Predicate<S> predicate) {
        return chain(s -> BooleanExpression.of(predicate.test(s)));
    }

    public final E satisfies(Evaluation<S> evaluation) {
        return chain(evaluation);
    }

    @Override
    public final boolean hasFailingNotNullCheck() {
        return includesNotNullCheck && isFalse();
    }
}
