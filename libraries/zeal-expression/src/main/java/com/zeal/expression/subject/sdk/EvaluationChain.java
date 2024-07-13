package com.zeal.expression.subject.sdk;

import com.zeal.expression.subject.Evaluation;

import java.util.ArrayList;
import java.util.List;

public class EvaluationChain<S> {

    private final List<Evaluation<S>> evaluations;

    public EvaluationChain(List<Evaluation<S>> evaluations) {
        this.evaluations = evaluations;
    }

    public EvaluationChain() {
        this(new ArrayList<>(1));
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
