package io.github.libzeal.zeal.values.sdk;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.unary.Condition;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

class UnaryExpressionChain<T> {

    private final List<Condition<T>> conditions;

    private UnaryExpressionChain(final List<Condition<T>> conditions) {
        this.conditions = requireNonNull(conditions);
    }

    public UnaryExpressionChain() {
        this(new ArrayList<>());
    }

    public UnaryExpressionChain<T> append(final Condition<T> condition) {

        conditions.add(condition);

        return this;
    }

    public UnaryExpressionChain<T> prepend(final Condition<T> condition) {

        conditions.addFirst(condition);

        return this;
    }

    public List<Expression> bind(final T subject) {
        return conditions.stream()
                .map(condition -> condition.bind(subject))
                .toList();
    }
}
