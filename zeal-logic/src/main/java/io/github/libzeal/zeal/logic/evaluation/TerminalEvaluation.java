package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.rationale.Rationale;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

/**
 * A simple evaluation that contains no children.
 *
 * @param isTrue
 *         The result (true or false) of the evaluation.
 * @param name
 *         The name of the evaluation (often the name of the original
 *         expression).
 * @param rationale
 *         The rationale of the evaluation.
 * @param duration
 *         The duration that the evaluation took to complete.
 *
 * @author Justin Albano
 */
public record TerminalEvaluation(
        boolean isTrue,
        String name,
        Rationale rationale,
        Duration duration
) implements Evaluation {

    /**
     * Creates a new terminal evaluation.
     *
     * @param isTrue
     *         The result (true or false) of the evaluation.
     * @param name
     *         The name of the evaluation (often the name of the original
     *         expression).
     * @param rationale
     *         The rationale of the evaluation.
     * @param duration
     *         The duration that the evaluation took to complete.
     *
     * @throws NullPointerException
     *         Any of the supplied arguments are {@code null}.
     */
    public TerminalEvaluation {
        requireNonNull(name);
        requireNonNull(rationale);
        requireNonNull(duration);
    }

    @Override
    public Cause cause() {
        return new Cause(this, DirectCause.self());
    }
}
