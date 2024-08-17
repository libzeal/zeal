package io.github.libzeal.zeal.expression.lang.evaluation;

import io.github.libzeal.zeal.expression.lang.rationale.Rationale;
import io.github.libzeal.zeal.expression.lang.rationale.SimpleRationale;

import static java.util.Objects.requireNonNull;

public class TerminalEvaluation implements Evaluation {

    private final Result result;
    private final String name;
    private final Rationale rationale;
    private final Cause cause;

    private TerminalEvaluation(final Result result, final String name, final Rationale rationale,
                               final Cause cause) {
        this.result = requireNonNull(result);
        this.name = requireNonNull(name);
        this.rationale = requireNonNull(rationale);
        this.cause = cause;
    }

    public static TerminalEvaluation ofTrue(final String name, final Rationale rationale) {
        return new TerminalEvaluation(Result.TRUE, name, rationale, null);
    }

    public static TerminalEvaluation ofFalse(final String name, final Rationale rationale) {
        return new TerminalEvaluation(Result.FALSE, name, rationale, null);
    }

    public static TerminalEvaluation ofSkipped(final String name, final Cause cause) {
        return new TerminalEvaluation(Result.SKIPPED, name, SimpleRationale.skipped(), cause);
    }

    @Override
    public Result result() {
        return result;
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
    public Cause rootCause() {

        if (cause == null) {
            return new Cause(this);
        }
        else {
            return cause;
        }
    }

    @Override
    public void traverseDepthFirst(final Traverser traverser) {
        traverseDepthFirst(traverser, TraversalContext.create());
    }

    @Override
    public void traverseDepthFirst(final Traverser traverser, final TraversalContext context) {

        if (traverser != null) {
            traverser.on(this, context);
        }
    }
}
