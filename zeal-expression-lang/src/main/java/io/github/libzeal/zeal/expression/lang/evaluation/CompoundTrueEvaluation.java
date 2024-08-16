package io.github.libzeal.zeal.expression.lang.evaluation;

import io.github.libzeal.zeal.expression.lang.rationale.Rationale;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class CompoundTrueEvaluation implements TrueEvaluation {

    private final String name;
    private final Rationale rationale;
    private final List<Evaluation> children;

    public CompoundTrueEvaluation(final String name, final Rationale rationale, final List<Evaluation> children) {
        this.name = requireNonNull(name);
        this.rationale = requireNonNull(rationale);
        this.children = requireNonNull(children);
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
