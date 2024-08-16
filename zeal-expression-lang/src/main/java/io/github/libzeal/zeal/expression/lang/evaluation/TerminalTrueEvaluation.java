package io.github.libzeal.zeal.expression.lang.evaluation;

import io.github.libzeal.zeal.expression.lang.rationale.Rationale;

import static java.util.Objects.requireNonNull;

public class TerminalTrueEvaluation implements TrueEvaluation {

    private final String name;
    private final Rationale rationale;

    public TerminalTrueEvaluation(final String name, final Rationale rationale) {
        this.name = requireNonNull(name);
        this.rationale = requireNonNull(rationale);
    }

    @Override
    public Result result() {
        return Result.TRUE;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Rationale rationale() {
        return rationale;
    }
}
