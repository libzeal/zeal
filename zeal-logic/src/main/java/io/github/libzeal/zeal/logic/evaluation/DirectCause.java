package io.github.libzeal.zeal.logic.evaluation;

import java.util.List;

/**
 * The direct cause of an evaluation. This direct cause represents the reason
 * that determined the result of an evaluation. For example, if a terminal
 * evaluation false, the direct cause would be itself (represented by
 * {@link Self}). Likewise, if a compound evaluation is false, the direct cause
 * represents the child (or children) that caused the compound evaluation to be
 * false.
 * <p>
 * True evaluations also have a corresponding direct cause that represents the
 * evaluation (or evaluations) that determined the result of the evaluation.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
public sealed interface DirectCause {

    /**
     * Creates a self-referential direct cause. This is used for evaluations
     * whose cause was determined by the evaluation only and is common with
     * terminal evaluations.
     *
     * @return A self-referential cause.
     */
    static DirectCause self() {
        return new Self();
    }

    /**
     * Creates a delegated cause, where one evaluation references another
     * evaluation as its cause. This is common with compound evaluations and
     * skipped evaluations.
     *
     * @param evaluation
     *         The referenced evaluation (the delegated cause).
     *
     * @return A deleted cause that references the supplied evaluation.
     *
     * @throws NullPointerException
     *         The supplied evaluation is {@code null}.
     */
    static Delegated of(final Evaluation evaluation) {
        return new Delegated(List.of(evaluation));
    }

    /**
     * Creates a delegated cause, where one evaluation references the supplied
     * evaluations as its causes. This is common with compound evaluations and
     * skipped evaluation.
     *
     * @param evaluations
     *         The referenced evaluations (the delegated causes).
     *
     * @return A deleted cause that references the supplied evaluations.
     *
     * @throws NullPointerException
     *         The supplied evaluations is {@code null} or any of the
     *         evaluations in the supplied list are {@code null}.
     */
    static Delegated of(final List<Evaluation> evaluations) {
        return new Delegated(evaluations);
    }

    /**
     * A self-referential cause, where an evaluation is determined only by
     * itself. This is common with terminal evaluations.
     */
    record Self() implements DirectCause {
    }

    /**
     * A direct cause that references one or more delegated evaluations.
     *
     * @param evaluations
     *         The referenced evaluations.
     */
    record Delegated(List<Evaluation> evaluations) implements DirectCause {

        /**
         * Creates a new delegated cause.
         *
         * @param evaluations
         *         The referenced evaluations (delegated causes).
         *
         * @throws NullPointerException
         *         The supplied evaluations is {@code null} or any of the
         *         evaluations in the supplied list are {@code null}.
         * @throws IllegalArgumentException
         *         The supplied evaluations list is empty.
         */
        public Delegated {
            if (evaluations.isEmpty()) {
                throw new IllegalArgumentException("A delegated cause requires at least one evaluation");
            }

            evaluations = List.copyOf(evaluations);
        }
    }
}
