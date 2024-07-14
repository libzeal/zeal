package com.zeal.expression.eval.primitive;

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

    public boolean evaluate(int subject) {

        for (IntEvaluation evaluation: evaluations) {

            if (!evaluation.evaluate(subject)) {
                return false;
            }
        }

        return true;
    }
}
