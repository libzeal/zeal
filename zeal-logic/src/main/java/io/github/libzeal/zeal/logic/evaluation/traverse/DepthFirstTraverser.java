package io.github.libzeal.zeal.logic.evaluation.traverse;

import io.github.libzeal.zeal.logic.CompoundExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;

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

        action.on(evaluation, context);

        if (evaluation instanceof final CompoundEvaluation compoundEvaluation) {

            final TraversalContext childContext = context.withIncrementedDepth();

            for (final SkippableEvaluation child : compoundEvaluation.children()) {
                switch (child) {
                    case Evaluated(Evaluation childEvaluation) -> traverse(childEvaluation, action, childContext);
                    case Skipped skipped -> traverse(skipped, action, childContext);
                }
            }
        }
    }

    private void traverse(final Skipped skipped, final TraverserAction action, final TraversalContext context) {

        action.onSkipped(skipped, context);

        if (skipped.expression() instanceof final CompoundExpression compoundExpression) {

            final TraversalContext childContext = context.withIncrementedDepth();

            for (final Expression child : compoundExpression.children()) {
                traverse(new Skipped(child, skipped.cause()), action, childContext);
            }
        }
    }
}
