package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.TerminalExpression;
import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import org.jspecify.annotations.Nullable;

record UnaryTerminalAdapter<T>(
        @Nullable T subject, TerminalExpression delegate
) implements TerminalUnaryExpression<T> {

    @Override
    public String name() {
        return delegate.name();
    }

    @Override
    public TerminalEvaluation evaluate() {
        return delegate.evaluate();
    }
}
