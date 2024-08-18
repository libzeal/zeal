package io.github.libzeal.zeal.logic.condition;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.rationale.RationaleGenerator;
import io.github.libzeal.zeal.logic.rationale.SimpleRationaleGenerator;
import io.github.libzeal.zeal.logic.unary.TerminalUnaryExpression;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

class SimpleCondition<T> implements Condition<T> {

    private final String name;
    private final Predicate<T> predicate;

    public SimpleCondition(final String name, final Predicate<T> predicate) {
        this.name = requireNonNull(name);
        this.predicate = requireNonNull(predicate);
    }

    @Override
    public Expression create(T subject) {

        final RationaleGenerator<T> generator = new SimpleRationaleGenerator<>(
            (s, passed) -> "satisfied",
            (s, passed) -> passed ? "satisfied" : "unsatisfied"
        );

        return TerminalUnaryExpression.ofNullable(
            name,
            subject,
            predicate,
            generator
        );
    }
}
