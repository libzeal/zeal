package com.zeal.expression.eval.primitive;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.ops.logical.AndOperation;

import java.util.ArrayList;
import java.util.List;

public final class IntEvaluationChain {

    private final List<IntEvaluation> evaluations;

    private IntEvaluationChain(List<IntEvaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public IntEvaluationChain() {
        this(new ArrayList<>(1));
    }

    public IntEvaluationChain append(IntEvaluation evaluation) {

        evaluations.add(evaluation);

        return this;
    }

    public BooleanExpression evaluate(int subject) {
        return new AndOperation(toArray(subject, evaluations));
    }

    private static BooleanExpression[] toArray(int subject, List<IntEvaluation> evaluations) {
        return evaluations.stream()
                .map(e -> e.evaluate(subject))
                .toArray(BooleanExpression[]::new);
    }
}
