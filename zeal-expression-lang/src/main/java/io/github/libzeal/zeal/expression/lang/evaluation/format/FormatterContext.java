package io.github.libzeal.zeal.expression.lang.evaluation.format;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Cause;

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
