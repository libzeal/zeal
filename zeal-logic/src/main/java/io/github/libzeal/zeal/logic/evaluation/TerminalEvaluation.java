package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class TerminalEvaluation implements Evaluation {

    private final Result result;
    private final String name;
    private final Rationale rationale;
    private final CauseGenerator causeGenerator;

    private TerminalEvaluation(final Result result, final String name, final Rationale rationale,
                               final CauseGenerator causeGenerator) {
        this.result = requireNonNull(result);
        this.name = requireNonNull(name);
        this.rationale = requireNonNull(rationale);
        this.causeGenerator = requireNonNull(causeGenerator);
    }

    public static TerminalEvaluation ofTrue(final String name, final Rationale rationale) {
        return new TerminalEvaluation(Result.TRUE, name, rationale, CauseGenerator.self());
    }

    public static TerminalEvaluation ofFalse(final String name, final Rationale rationale) {
        return new TerminalEvaluation(Result.FALSE, name, rationale, CauseGenerator.self());
    }

    public static TerminalEvaluation ofSkipped(final String name, final CauseGenerator causeGenerator) {
        return new TerminalEvaluation(Result.SKIPPED, name, SimpleRationale.skipped(), causeGenerator);
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
    public Cause cause() {
        return causeGenerator.generate(this);
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof TerminalEvaluation)) return false;
        final TerminalEvaluation that = (TerminalEvaluation) o;
        return result == that.result && Objects.equals(name, that.name) && Objects.equals(rationale, that.rationale) && Objects.equals(causeGenerator, that.causeGenerator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, name, rationale, causeGenerator);
    }
}
