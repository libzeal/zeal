package io.github.libzeal.zeal.expression.lang.evaluation;

public interface Traverser {

    void on(TrueEvaluation evaluation, TraversalContext context);
    void on(FalseEvaluation evaluation, TraversalContext context);
    void on(SkippedEvaluation evaluation, TraversalContext context);
}
