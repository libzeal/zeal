package io.github.libzeal.zeal.expression.lang.evaluation;

import io.github.libzeal.zeal.expression.lang.rationale.Rationale;
import io.github.libzeal.zeal.expression.lang.rationale.SimpleRationale;

import static java.util.Objects.requireNonNull;

public class TerminalSkippedEvaluation implements SkippedEvaluation {

    private final String name;

    public TerminalSkippedEvaluation(final String name) {
        this.name = requireNonNull(name);
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
}
