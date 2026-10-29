package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.rationale.Rationale;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

public record TerminalEvaluation(boolean isTrue, String name, Rationale rationale,
                                 Duration elapsedTime) implements Evaluation {

    public TerminalEvaluation {
        requireNonNull(name);
        requireNonNull(rationale);
        requireNonNull(elapsedTime);
    }

    @Override
    public Cause cause() {
        return Cause.self();
    }
}
