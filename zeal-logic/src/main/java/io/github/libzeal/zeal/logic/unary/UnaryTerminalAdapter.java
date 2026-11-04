package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.CompoundExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import org.jspecify.annotations.Nullable;

import java.util.List;

record UnaryCompoundAdapter<T>(
        @Nullable T subject, CompoundExpression delegate
) implements CompoundUnaryExpression<T> {

    @Override
    public String name() {
        return delegate.name();
    }

    @Override
    public List<Expression> children() {
        return delegate.children();
    }

    @Override
    public CompoundEvaluation evaluate() {
        return delegate.evaluate();
    }
}
