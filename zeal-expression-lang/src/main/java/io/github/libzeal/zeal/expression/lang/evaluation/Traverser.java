package io.github.libzeal.zeal.expression.lang.evaluation;

public interface Traverser {

    void on(Evaluation evaluation, TraversalContext context);
}
