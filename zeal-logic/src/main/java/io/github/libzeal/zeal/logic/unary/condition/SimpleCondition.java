package io.github.libzeal.zeal.logic.unary.condition;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.unary.future.rationale.ComputableRationale;
import io.github.libzeal.zeal.logic.unary.future.rationale.SimpleComputableRationale;
import io.github.libzeal.zeal.logic.unary.future.ComputedExpression;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * A simple condition that builds an expression based on a supplied predicate.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.1
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
    public Expression compute(T subject) {

        final ComputableRationale<T> generator = new SimpleComputableRationale<>(
            context -> "satisfied",
            context -> context.ifPassedOrElse("satisfied", "unsatisfied")
        );

        return new ComputedExpression<>(name, subject, predicate, generator);
    }
}
