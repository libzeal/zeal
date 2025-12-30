package io.github.libzeal.zeal.logic.evaluation.traverse;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;

public interface Traverser {
    void traverse(final Evaluation evaluation, final TraverserAction action);
}
