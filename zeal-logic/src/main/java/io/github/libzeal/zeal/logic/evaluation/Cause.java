package io.github.libzeal.zeal.logic.evaluation;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Cause {

    private final Evaluation evaluation;

    public Cause(final Evaluation evaluation) {
        this.evaluation = requireNonNull(evaluation);
    }

    public boolean is(final Evaluation other) {
        return Objects.equals(evaluation, other);
    }

    public Evaluation evaluation() {
        return evaluation;
    }
}
