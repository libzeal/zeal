package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.NegatedExpression;
import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;

import static java.util.Objects.requireNonNull;

public class NegatedUnaryExpression<T> implements UnaryExpression<T> {

    private final UnaryExpression<T> wrapped;
    private final NegatedExpression negated;

    public NegatedUnaryExpression(final UnaryExpression<T> wrapped) {
        this.wrapped = requireNonNull(wrapped);
        this.negated = new NegatedExpression(wrapped);
    }

    @Override
    public Evaluation evaluate() {
        return negated.evaluate();
    }

    @Override
    public String name() {
        return negated.name();
    }

    @Override
    public Evaluation skip(final Cause cause) {
        return negated.skip(cause);
    }

    @Override
    public T subject() {
        return wrapped.subject();
    }
}
