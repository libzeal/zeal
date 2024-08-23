package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.NegatedExpression;
import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;

import static java.util.Objects.requireNonNull;

/**
 * An expression that negates a wrapped {@link UnaryExpression} (a logical <em>not</em>).
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class NegatedUnaryExpression<T> implements UnaryExpression<T> {

    private final UnaryExpression<T> wrapped;
    private final NegatedExpression negated;

    /**
     * Creates a negated expression.
     *
     * @param wrapped
     *     The expression to negate.
     *
     * @throws NullPointerException
     *     The supplied wrapped expression is {@code null}.
     */
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
