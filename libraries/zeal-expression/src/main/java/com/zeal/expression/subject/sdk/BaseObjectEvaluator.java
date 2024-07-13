package com.zeal.expression.subject.sdk;

import com.zeal.expression.subject.SingleSubjectBooleanExpression;

import java.util.Objects;
import java.util.function.Predicate;

public abstract class BaseObjectEvaluator<S, E extends BaseObjectEvaluator<S, E>>
        extends ChainedEvaluator<S, E> {

    public BaseObjectEvaluator(S value) {
        super(value);
    }

    public E isNull() {
        return appendToChain(Objects::isNull);
    }

    public E isNotNull() {
        return appendToChain(Objects::nonNull);
    }

    public SingleSubjectBooleanExpression<S> hasType(Class<?> type) {
        return appendToChain(s -> s != null && s.getClass().equals(type));
    }

    public E is(Object other) {
        return appendToChain(s -> s != null && s == other);
    }

    public E isEqualTo(Object other) {
        return appendToChain(s -> s != null && s.equals(other));
    }

    public E hasHashCode(int hashCode) {
        return appendToChain(s -> s != null && s.hashCode() == hashCode);
    }

    public E satisfies(Predicate<S> evaluation) {
        return appendToChain(evaluation::test);
    }
}
