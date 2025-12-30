package io.github.libzeal.zeal.logic.evaluation.traverse;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A traverser that collects up evaluations in the order in which they were visited.
 * <p>
 * This traverser is intended to be supplied to a traversable evaluation exactly once. This traverser assumes that the
 * first evaluation found during traversal is the root (parent) evaluation and all subsequent evaluations are child
 * evaluations.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class CollectingTraverserAction implements TraverserAction {

    private final List<Evaluation> evaluations;

    /**
     * Creates a new traverser with an empty found list.
     */
    public CollectingTraverserAction() {
        this.evaluations = new ArrayList<>();
    }

    /**
     * Obtains the list of evaluations that were found after this traverser was supplied to a traversable evaluation.
     * This method is intended to be called only after this traverser is supplied to a traversable evaluation.
     *
     * @return The list of evaluations found during traversal.
     */
    public List<Evaluation> found() {
        return evaluations;
    }

    /**
     * Obtains the list of children found during traversal.
     *
     * @return The found list of children.
     */
    public List<Evaluation> children() {

        if (evaluations.size() > 1) {
            return evaluations.subList(1, evaluations.size());
        }
        else {
            return new ArrayList<>(0);
        }
    }

    /**
     * Obtains the found evaluation at the supplied index if such an evaluation exists.
     *
     * @param index
     *     The index of the desired found evaluation.
     *
     * @return An {@link Optional} populated with the evaluation at the supplied index if it exists; an empty
     *     {@link Optional} otherwise.
     */
    public Optional<Evaluation> get(final int index) {

        if (index >= 0 && index < evaluations.size()) {
            return Optional.ofNullable(evaluations.get(index));
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public void on(final Evaluation evaluation, final TraversalContext context) {
        evaluations.add(evaluation);
    }
}
