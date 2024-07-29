package io.github.libzeal.zeal.expression.criteria;

public class EvaluationContext {

    private final boolean skip;

    public EvaluationContext(boolean skip) {
        this.skip = skip;
    }

    public EvaluationContext() {
        this(false);
    }

    public boolean skip() {
        return skip;
    }

    public EvaluationContext withSkip() {
        return new EvaluationContext(true);
    }
}
