package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.rationale.Rationale;

import java.time.Duration;
import java.util.List;

/**
 * An {@link Expression} that has been evaluated. An evaluated expression
 * contains a state, the name of the evaluation that was performed, and a
 * rationale that describes how the state was reached (the rationale used to
 * reach the state).
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
     * Obtains the elapsed time the evaluation (how long the evaluation took to
     * complete).
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

    default List<Cause.Trace> causeTraces() {
        return switch (cause()) {
            case Cause.Self() -> List.of(Cause.Trace.of(this));
            case final Cause.Delegated delegated -> delegated.traces()
                    .stream()
                    .map(trace -> trace.prepend(this))
                    .toList();
        };
    }

    default List<Evaluation> rootCauses() {
        return causeTraces().stream()
                .map(Cause.Trace::rootCause)
                .toList();
    }
}
