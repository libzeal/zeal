package com.zeal.expression.eval.base;

import com.zeal.expression.Explanation;
import com.zeal.expression.eval.Evaluation;
import com.zeal.expression.eval.Evaluator;

import java.util.Optional;

public abstract class ChainedEvaluator<S, E extends ChainedEvaluator<S, E>>
        implements Evaluator<S> {

    private final S subject;
    private final EvaluationChain<S> chain;

    public ChainedEvaluator(S subject) {
        this.subject = subject;
        this.chain = new EvaluationChain<>();
    }

    @SuppressWarnings("unchecked")
    protected E chain(Evaluation<S> evaluation) {
        chain.append(evaluation);
        return (E) this;
    }

    @SuppressWarnings("unchecked")
    E prependToChain(Evaluation<S> evaluation) {
        chain.prepend(evaluation);
        return (E) this;
    }

    @Override
    public S subject() {
        return subject;
    }

    @Override
    public boolean isTrue() {
        return chain.evaluate(subject).isTrue();
    }

    @Override
    public Optional<Explanation> failureExplanation() {
        return chain.evaluate(subject).failureExplanation();
    }
}
