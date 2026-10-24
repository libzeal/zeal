package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import java.time.Duration;
import java.util.List;

import static java.util.Objects.requireNonNull;

public record CompoundEvaluation(Result result, String name, Rationale rationale, Duration elapsedTime,
                                 Cause cause, List<Evaluation> children) implements Evaluation {

    public CompoundEvaluation {
        requireNonNull(result);
        requireNonNull(name);
        requireNonNull(rationale);
        requireNonNull(elapsedTime);
        requireNonNull(cause);
        children = List.copyOf(children);
    }

    public static CompoundEvaluation skipped(final String name, final Evaluation decider,
                                             final List<Evaluation> children) {
        return new CompoundEvaluation(Result.SKIPPED, name, SimpleRationale.skipped(), Duration.ZERO,
            Cause.of(decider), children);
    }
}
