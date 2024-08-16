package io.github.libzeal.zeal.expression.lang.evaluation;

import io.github.libzeal.zeal.expression.lang.rationale.Rationale;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class CompoundFalseEvaluation implements FalseEvaluation {

    private final String name;
    private final Rationale rationale;
    private final RootCause rootCause;
    private final List<Evaluation> children;

    public CompoundFalseEvaluation(final String name, final Rationale rationale, final RootCause rootCause,
                                   final List<Evaluation> children) {
        this.name = requireNonNull(name);
        this.rationale = requireNonNull(rationale);
        this.rootCause = rootCause;
        this.children = requireNonNull(children);
    }

    public static Evaluation selfRootCause(final String name, final Rationale rationale, final List<Evaluation> children) {
        return new CompoundFalseEvaluation(name, rationale, children);
    }

    public CompoundFalseEvaluation(final String name, final Rationale rationale, final List<Evaluation> children) {
        this(name, rationale, null, children);
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

        if (rootCause == null) {
            return new RootCause(this);
        }
        else {
            return rootCause;
        }
    }
}
