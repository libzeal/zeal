package com.zeal.expression.subject.sdk;

import com.zeal.expression.subject.Evaluation;
import com.zeal.expression.subject.SingleSubjectBooleanExpression;

public class ChainedEvaluator<S, E extends ChainedEvaluator<S, E>>
        implements SingleSubjectBooleanExpression<S> {

    private final S subject;
    private final EvaluationChain<S> chain;

    public ChainedEvaluator(S subject) {
        this.subject = subject;
        this.chain = new EvaluationChain<>();
    }

    @SuppressWarnings("unchecked")
    protected E appendToChain(Evaluation<S> evaluation) {
        chain.append(evaluation);
        return (E) this;
    }

    @Override
    public S subject() {
        return subject;
    }

    @Override
    public boolean isTrue() {
        return chain.evaluate(subject);
    }
}
