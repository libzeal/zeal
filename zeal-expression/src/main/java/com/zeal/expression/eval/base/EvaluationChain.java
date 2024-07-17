package com.zeal.expression.eval.base;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.eval.Evaluation;
import com.zeal.expression.ops.logical.AndOperation;

import java.util.ArrayList;
import java.util.List;

public final class EvaluationChain<S> {

    private final List<Evaluation<S>> evaluations;

    private EvaluationChain(List<Evaluation<S>> evaluations) {
        this.evaluations = evaluations;
    }

    public EvaluationChain(int initialCapacity) {
        this(new ArrayList<>(initialCapacity));
    }

    public EvaluationChain() {
        this(1);
    }

    public EvaluationChain<S> prepend(Evaluation<S> evaluation) {

        evaluations.add(0, evaluation);

        return this;
    }

    public EvaluationChain<S> append(Evaluation<S> evaluation) {

        evaluations.add(evaluation);

        return this;
    }

    public BooleanExpression evaluate(S subject) {
        return new AndOperation(toArray(subject, evaluations));
    }

    private static <T> BooleanExpression[] toArray(T subject, List<Evaluation<T>> evaluations) {
        return evaluations.stream()
                .map(e -> e.evaluate(subject))
                .toArray(BooleanExpression[]::new);
    }
}
