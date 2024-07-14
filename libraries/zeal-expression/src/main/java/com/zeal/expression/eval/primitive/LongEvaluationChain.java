package com.zeal.expression.eval.primitive;

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

    public boolean evaluate(long subject) {

        for (LongEvaluation evaluation: evaluations) {

            if (!evaluation.evaluate(subject)) {
                return false;
            }
        }

        return true;
    }
}
