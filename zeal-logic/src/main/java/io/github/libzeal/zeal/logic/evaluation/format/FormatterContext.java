package io.github.libzeal.zeal.logic.evaluation.format;

import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class FormatterContext {

    private final Cause cause;
    private final int depth;

    public FormatterContext(final Cause cause, final int depth) {
        this.cause = requireNonNull(cause);
        this.depth = depth;
    }

    public Cause rootCause() {
        return cause;
    }

    public boolean isRootCause(final Evaluation evaluation) {

        if (evaluation == null) {
            return false;
        }

        return Objects.equals(cause.evaluation(), evaluation);
    }

    public int depth() {
        return depth;
    }
}
