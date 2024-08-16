package io.github.libzeal.zeal.expression.lang.evaluation;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class RootCause {

    private final Evaluation evaluation;

    public RootCause(final Evaluation evaluation) {
        this.evaluation = requireNonNull(evaluation);
    }

    public boolean is(final Evaluation other) {
        return Objects.equals(evaluation, other);
    }

    public Evaluation evaluation() {
        return evaluation;
    }
}
