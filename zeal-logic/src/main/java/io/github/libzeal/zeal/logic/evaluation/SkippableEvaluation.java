package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.Expression;

import static java.util.Objects.requireNonNull;

/**
 * An evaluation that could have been skipped. This is primarily used for
 * {@link CompoundEvaluation}s, where a child evaluation may be skipped. This
 * allows clients to forego the processing of an expression (not compute the
 * evaluation) while still allowing the casual information about why that
 * evaluation was skipped to be recorded.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
public sealed interface SkippableEvaluation {

    /**
     * Obtains the name of the evaluation.
     *
     * @return The name of the evaluation.
     */
    String name();

    /**
     * A skippable evaluation that was evaluated.
     *
     * @param evaluation
     *         The corresponding evaluation.
     */
    record Evaluated(Evaluation evaluation) implements SkippableEvaluation {

        /**
         * Creates a new evaluated skippable evaluation.
         *
         * @param evaluation
         *         The corresponding evaluation.
         *
         * @throws NullPointerException
         *         The supplied evaluation is {@code null}.
         */
        public Evaluated {
            requireNonNull(evaluation);
        }

        @Override
        public String name() {
            return evaluation.name();
        }
    }

    /**
     * A skipped evaluation.
     *
     * @param expression
     *         The expression whose evaluation was skipped.
     * @param directCause
     *         The direct cause that led to the evaluation being skipped.
     */
    record Skipped(
            Expression expression,
            DirectCause.Delegated directCause
    ) implements SkippableEvaluation {

        /**
         * Creates a skipped evaluation.
         *
         * @param expression
         *         The expression whose evaluation was skipped.
         * @param directCause
         *         The direct cause that led to the evaluation being skipped.
         *
         * @throws NullPointerException
         *         Any of the supplied arguments are {@code null}.
         */
        public Skipped {
            requireNonNull(expression);
            requireNonNull(directCause);
        }

        @Override
        public String name() {
            return expression.name();
        }
    }
}
