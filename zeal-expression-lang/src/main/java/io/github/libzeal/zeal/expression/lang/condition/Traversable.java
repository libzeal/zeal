package io.github.libzeal.zeal.expression.lang.condition;

import io.github.libzeal.zeal.expression.lang.evaluation.TraversalContext;
import io.github.libzeal.zeal.expression.lang.evaluation.Traverser;

public interface Traversable {
    void traverseDepthFirst(Traverser traverser);
    void traverseDepthFirst(Traverser traverser, TraversalContext context);
}
