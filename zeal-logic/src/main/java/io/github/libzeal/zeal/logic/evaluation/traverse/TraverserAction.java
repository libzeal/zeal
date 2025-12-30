package io.github.libzeal.zeal.logic.evaluation.traverse;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;

public interface TraverserAction {

    /**
     * Called each time a new evaluation is visited.
     *
     * @param evaluation
     *     The evaluation that was visited.
     * @param context
     *     The context of the evaluation that was visited, such as the depth of the evaluation.
     */
    void on(Evaluation evaluation, TraversalContext context);
}
