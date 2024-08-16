package io.github.libzeal.zeal.expression.lang.evaluation;

import io.github.libzeal.zeal.expression.lang.rationale.Rationale;

import static java.util.Objects.requireNonNull;

public class TerminalFalseEvaluation implements FalseEvaluation {

    private final String name;
    private final Rationale rationale;

    public TerminalFalseEvaluation(final String name, final Rationale rationale) {
        this.name = requireNonNull(name);
        this.rationale = requireNonNull(rationale);
    }

    @Override
    public Result result() {
        return Result.FALSE;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Rationale rationale() {
        return rationale;
    }

    @Override
    public RootCause rootCause() {
        return new RootCause(this);
    }
}
