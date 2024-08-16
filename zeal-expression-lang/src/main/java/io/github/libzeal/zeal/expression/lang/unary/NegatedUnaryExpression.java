package io.github.libzeal.zeal.expression.lang.unary;

import io.github.libzeal.zeal.expression.lang.NegatedExpression;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;

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
    public Evaluation skip() {
        return negated.skip();
    }

    @Override
    public T subject() {
        return wrapped.subject();
    }
}
