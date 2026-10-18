package io.github.libzeal.zeal.logic.evaluation.traverse;

import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;

/**
 * An entity that traverses the evaluation tree.
 *
 * @author Justin Albano
 */
public class DepthFirstTraverser implements Traverser {

    @Override
    public void traverse(final Evaluation evaluation, final TraverserAction action) {
        traverse(evaluation, action, TraversalContext.create());
    }

    private void traverse(final Evaluation evaluation, final TraverserAction action,
                          final TraversalContext context) {

        if (evaluation instanceof final CompoundEvaluation compoundEvaluation) {

            final TraversalContext childContext = context.withIncrementedDepth();

            action.on(evaluation, context);

            for (final Evaluation child : compoundEvaluation.children()) {
                traverse(child, action, childContext);
            }
        }
        else {
            action.on(evaluation, context);
        }
    }
}
