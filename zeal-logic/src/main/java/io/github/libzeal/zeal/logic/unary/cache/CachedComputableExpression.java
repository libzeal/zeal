package io.github.libzeal.zeal.logic.unary.cache;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.unary.future.ComputableExpression;

import static java.util.Objects.requireNonNull;

public record CachedComputableExpression<T, C>(String name,
                                        CacheablePredicate<T, C> predicate,
                                        CachedComputableRationale<T, C> computableRationale) implements ComputableExpression<T> {

    public CachedComputableExpression {
        requireNonNull(name);
        requireNonNull(predicate);
        requireNonNull(computableRationale);
    }

    @Override
    public Expression compute(T subject) {
        return new CachedExpression<>(name, subject, predicate, computableRationale);
    }
}
