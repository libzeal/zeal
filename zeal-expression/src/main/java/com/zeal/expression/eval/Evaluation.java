package com.zeal.expression.eval;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.BooleanResult;
import com.zeal.expression.Explainer;
import com.zeal.expression.Explanation;

import java.util.Optional;

@FunctionalInterface
public interface Evaluation<T> {

    BooleanExpression evaluate(T subject);

    static <T> Evaluation<T> of(BooleanFunction<T> func) {
        return o -> BooleanExpression.of(() -> func.apply(o));
    }

    static <T> Evaluation<T> of(BooleanFunction<T> func, Explainer<T> explainer) {
        return subject -> {
            return () -> {
                return new BooleanResult(
                        () -> func.apply(subject),
                        explainer.explain(subject)
                );
            };
        };
    }

    @FunctionalInterface
    static interface BooleanFunction<T> {
        boolean apply(T o);
    }
}
