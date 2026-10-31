package io.github.libzeal.zeal.values.sdk;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.unary.Condition;
import io.github.libzeal.zeal.values.core.ObjectValue;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A chain of expressions where new expressions can be inserted at the head
 * (prepend) or the tail (append). This chain is used by implementations of
 * {@link ObjectValue} for storing the conditions that will be bound when the
 * value is evaluated.
 *
 * @param <S>
 *         The type of the subject.
 *
 * @author Justin Albano
 */
class UnaryExpressionChain<S> {

    private final List<Condition<S>> conditions;

    private UnaryExpressionChain(final List<Condition<S>> conditions) {
        this.conditions = requireNonNull(conditions);
    }

    public UnaryExpressionChain() {
        this(new ArrayList<>());
    }

    public UnaryExpressionChain<S> append(final Condition<S> condition) {

        conditions.add(condition);

        return this;
    }

    public UnaryExpressionChain<S> prepend(final Condition<S> condition) {

        conditions.addFirst(condition);

        return this;
    }

    /**
     * Binds the subject to the stored conditions, producing a list of
     * expressions. The expressions in the returned list correspond to the
     * conditions stored in the chain (the subject is applied to each condition
     * and the resulting expressions are stored in the same order in the
     * returned list).
     *
     * @param subject
     *         The subject to bind.
     *
     * @return A list of expressions corresponding to the conditions in the
     * chain bound to the supplied subject.
     */
    public List<Expression> bind(final S subject) {
        return conditions.stream()
                .map(condition -> condition.bind(subject))
                .toList();
    }
}
