package com.zeal.expression.eval.primitive;

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

    public boolean evaluate(float subject) {

        for (FloatEvaluation evaluation: evaluations) {

            if (!evaluation.evaluate(subject)) {
                return false;
            }
        }

        return true;
    }
}
