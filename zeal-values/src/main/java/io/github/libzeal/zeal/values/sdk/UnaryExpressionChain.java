package io.github.libzeal.zeal.values.sdk;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.unary.ComputableExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

class UnaryExpressionChain<T> {

    private final List<ComputableExpression<T>> expressions;

    private UnaryExpressionChain(final List<ComputableExpression<T>> expressions) {
        this.expressions = requireNonNull(expressions);
    }

    public UnaryExpressionChain() {
        this(new ArrayList<>());
    }

    public UnaryExpressionChain<T> append(final ComputableExpression<T> computable) {

        expressions.add(computable);

        return this;
    }

    public UnaryExpressionChain<T> prepend(final ComputableExpression<T> computable) {

        expressions.addFirst(computable);

        return this;
    }

    public List<Expression> bind(final T subject) {
        return expressions.stream()
                .map(e -> e.compute(subject))
                .collect(Collectors.toList());
    }
}
