package com.zeal.expression.eval.primitive;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.ops.logical.AndOperation;

import java.util.ArrayList;
import java.util.List;

public final class FloatEvaluationChain {

    private final List<FloatEvaluation> evaluations;

    private FloatEvaluationChain(List<FloatEvaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public FloatEvaluationChain() {
        this(new ArrayList<>(1));
    }

    public FloatEvaluationChain append(FloatEvaluation evaluation) {

        evaluations.add(evaluation);

        return this;
    }

    public BooleanExpression evaluate(float subject) {
        return new AndOperation(toArray(subject, evaluations));
    }

    private static BooleanExpression[] toArray(float subject, List<FloatEvaluation> evaluations) {
        return evaluations.stream()
                .map(e -> e.evaluate(subject))
                .toArray(BooleanExpression[]::new);
    }
}
