package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

public record TerminalEvaluation(Result result, String name, Rationale rationale, Duration elapsedTime,
                                 Cause cause) implements Evaluation {

    public TerminalEvaluation {
        requireNonNull(result);
        requireNonNull(name);
        requireNonNull(rationale);
        requireNonNull(elapsedTime);
        requireNonNull(cause);
    }

    public static TerminalEvaluation of(final Result result, final String name, final Rationale rationale,
                                        final Duration elapsedTime) {
        return new TerminalEvaluation(result, name, rationale, elapsedTime, Cause.self());
    }

    public static TerminalEvaluation skipped(final String name, final Evaluation decider) {
        return new TerminalEvaluation(Result.SKIPPED, name, SimpleRationale.skipped(), Duration.ZERO,
            Cause.of(decider));
    }
}
