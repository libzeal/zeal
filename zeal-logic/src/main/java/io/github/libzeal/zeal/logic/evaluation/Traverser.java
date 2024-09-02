package io.github.libzeal.zeal.logic.evaluation;

/**
 * An entity that traverses the evaluation tree.
 *
 * @author Justin Albano
 * @since 0.4.0
 */
public interface Traverser {

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
