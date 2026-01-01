package io.github.libzeal.zeal.values.api;

import io.github.libzeal.zeal.logic.AndExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.unary.future.ComputableExpression;

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

        expressions.add(0, computable);

        return this;
    }

    public Evaluation evaluate(final String name, final T subject) {

        final List<Expression> expressionsWithSubject = expressions.stream()
                .map(e -> e.compute(subject))
                .collect(Collectors.toList());

        final AndExpression and = new AndExpression(name, expressionsWithSubject);

        return and.evaluate();
    }
}
