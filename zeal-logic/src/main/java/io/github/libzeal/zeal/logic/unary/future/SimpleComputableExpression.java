package io.github.libzeal.zeal.logic.unary.future;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.unary.future.rationale.ComputableRationale;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class SimpleComputableExpression<T> implements ComputableExpression<T> {

    private final String name;
    private final Predicate<T> predicate;
    private final ComputableRationale<T> computableRationale;

    public SimpleComputableExpression(final String name, final Predicate<T> predicate,
                                      final ComputableRationale<T> computableRationale) {
        this.name = requireNonNull(name);
        this.predicate = requireNonNull(predicate);
        this.computableRationale = requireNonNull(computableRationale);
    }

    @Override
    public Expression compute(T subject) {
        return new ComputedExpression<>(name, subject, predicate, computableRationale);
    }
}
