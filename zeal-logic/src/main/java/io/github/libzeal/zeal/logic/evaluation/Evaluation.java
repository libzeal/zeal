package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.rationale.Rationale;

import java.time.Duration;

/**
 * An {@link Expression} that has been evaluated. An evaluation contains:
 * <ul>
 *     <li>A boolean result {@link #isTrue()}</li>
 *     <li>The name of the evaluation (commonly the name of the expression that
 *     was evaluated</li>
 *     <li>A rationale that describes the expected and actual values of the
 *     evaluation</li>
 *     <li>The cause of the evaluation (that describes the evaluations that led
 *     to the true or false value of this evaluation)</li>
 * </ul>
 *
 * @author Justin Albano
 */
public sealed interface Evaluation permits TerminalEvaluation, CompoundEvaluation {

    /**
     * Checks whether the evaluation is true.
     *
     * @return True if the evaluation is true; false otherwise.
     */
    boolean isTrue();

    /**
     * Checks whether the evaluation is false.
     *
     * @return True if the evaluation is false; false otherwise.
     */
    default boolean isFalse() {
        return !isTrue();
    }

    /**
     * Obtains the name of the evaluation that was preformed.
     *
     * @return The name of the evaluation that was performed.
     */
    String name();

    /**
     * Obtains the rationale for the evaluation.
     *
     * @return The rationale for the evaluation.
     */
    Rationale rationale();

    /**
     * Obtains the duration the evaluation (how long the evaluation took to
     * complete).
     *
     * @return The duration of the evaluation.
     *
     * @since 0.2.1
     */
    Duration duration();

    /**
     * Obtains the cause of the evaluation.
     *
     * @return The cause of the evaluation.
     *
     * @since 0.2.1
     */
    Cause cause();
}
