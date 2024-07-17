package com.zeal.expression.eval.primitive;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.eval.Evaluation;
import com.zeal.expression.ops.logical.AndOperation;

import java.util.ArrayList;
import java.util.List;

public final class DoubleEvaluationChain {

    private final List<DoubleEvaluation> evaluations;

    private DoubleEvaluationChain(List<DoubleEvaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public DoubleEvaluationChain() {
        this(new ArrayList<>(1));
    }

    public DoubleEvaluationChain append(DoubleEvaluation evaluation) {

        evaluations.add(evaluation);

        return this;
    }

    public BooleanExpression evaluate(double subject) {
        return new AndOperation(toArray(subject, evaluations));
    }

    private static BooleanExpression[] toArray(double subject, List<DoubleEvaluation> evaluations) {
        return evaluations.stream()
                .map(e -> e.evaluate(subject))
                .toArray(BooleanExpression[]::new);
    }
}
