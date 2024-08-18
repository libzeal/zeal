package io.github.libzeal.zeal.logic.evaluation;

public interface Traverser {

    void on(Evaluation evaluation, TraversalContext context);
}
