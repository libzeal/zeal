package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import java.time.Duration;

public class SkippedTerminalEvaluation implements TerminalEvaluation {

    private final String name;
    private final CauseGenerator cause;

    public SkippedTerminalEvaluation(final String name, final CauseGenerator cause) {
        this.name = name;
        this.cause = cause;
    }

    @Override
    public Result result() {
        return Result.SKIPPED;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Rationale rationale() {
        return SimpleRationale.skipped();
    }

    @Override
    public Duration elapsedTime() {
        return Duration.ZERO;
    }

    @Override
    public Cause cause() {
        return cause.generate(this);
    }
}
