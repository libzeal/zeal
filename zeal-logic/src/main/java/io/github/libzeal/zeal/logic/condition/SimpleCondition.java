package io.github.libzeal.zeal.logic.condition;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.rationale.RationaleGenerator;
import io.github.libzeal.zeal.logic.rationale.SimpleRationaleGenerator;
import io.github.libzeal.zeal.logic.unary.TerminalUnaryExpression;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * A simple condition that builds an expression based on a supplied predicate.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.4.0
 */
class SimpleCondition<T> implements Condition<T> {

    private final String name;
    private final Predicate<T> predicate;

    /**
     * Creates a new condition.
     *
     * @param name
     *     The name of the condition.
     * @param predicate
     *     The predicate used to evaluate the expression.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
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

        return TerminalUnaryExpression.of(name, subject, predicate, generator);
    }
}
