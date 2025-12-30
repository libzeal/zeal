package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import java.time.Duration;
import java.util.List;

public class SkippedCompoundEvaluation implements CompoundEvaluation {

    private final String name;
    private final CauseGenerator cause;
    private final List<Evaluation> children;

    public SkippedCompoundEvaluation(final String name, final CauseGenerator cause,
                                     final List<Evaluation> children) {
        this.name = name;
        this.cause = cause;
        this.children = children;
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

    @Override
    public List<Evaluation> children() {
        return children;
    }
}
