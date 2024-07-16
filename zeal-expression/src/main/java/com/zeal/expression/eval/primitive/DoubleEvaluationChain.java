package com.zeal.expression.eval.primitive;

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

    public boolean evaluate(double subject) {

        for (DoubleEvaluation evaluation: evaluations) {

            if (!evaluation.evaluate(subject)) {
                return false;
            }
        }

        return true;
    }
}
