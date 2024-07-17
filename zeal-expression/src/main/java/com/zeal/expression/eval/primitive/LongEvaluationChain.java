package com.zeal.expression.eval.primitive;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.ops.logical.AndOperation;

import java.util.ArrayList;
import java.util.List;

public final class LongEvaluationChain {

    private final List<LongEvaluation> evaluations;

    private LongEvaluationChain(List<LongEvaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public LongEvaluationChain() {
        this(new ArrayList<>(1));
    }

    public LongEvaluationChain append(LongEvaluation evaluation) {

        evaluations.add(evaluation);

        return this;
    }

    public BooleanExpression evaluate(long subject) {
        return new AndOperation(toArray(subject, evaluations));
    }

    private static BooleanExpression[] toArray(long subject, List<LongEvaluation> evaluations) {
        return evaluations.stream()
                .map(e -> e.evaluate(subject))
                .toArray(BooleanExpression[]::new);
    }
}
