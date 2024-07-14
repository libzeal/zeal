package com.zeal.expression.eval.base;

import com.zeal.expression.eval.Evaluation;

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

    public EvaluationChain<S> append(Evaluation<S> evaluation) {

        evaluations.add(evaluation);

        return this;
    }

    public boolean evaluate(S subject) {

        for (Evaluation<S> evaluation: evaluations) {

            if (!evaluation.evaluate(subject)) {
                return false;
            }
        }

        return true;
    }
}
