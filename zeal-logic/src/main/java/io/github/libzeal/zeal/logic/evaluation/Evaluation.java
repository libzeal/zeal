package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.rationale.Rationale;

import java.time.Duration;

/**
 * An {@link Expression} that has been evaluated. An evaluated expression contains a state, the name of the evaluation
 * that was performed, and a rationale that describes how the state was reached (the rationale used to reach the
 * state).
 *
 * @author Justin Albano
 */
public interface Evaluation {

    /**
     * Obtains the state of the evaluation (i.e., pass or fail).
     *
     * @return The state of the evaluation.
     */
    Result result();

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
     * Obtains the elapsed time the evaluation (how long the evaluation took to complete).
     *
     * @return The elapsed time of the evaluation.
     *
     * @since 0.2.1
     */
    Duration elapsedTime();

    /**
     * Obtains the cause of the evaluation.
     *
     * @return The cause of the evaluation.
     *
     * @since 0.2.1
     */
    Cause cause();
}
