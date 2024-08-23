package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class CompoundEvaluation implements Evaluation {

    private final Result result;
    private final String name;
    private final Rationale rationale;
    private final CauseGenerator causeGenerator;
    private final List<Evaluation> children;

    public CompoundEvaluation(final Result result, final String name, final Rationale rationale,
                              final CauseGenerator causeGenerator, final List<Evaluation> children) {
        this.result = requireNonNull(result);
        this.name = requireNonNull(name);
        this.rationale = requireNonNull(rationale);
        this.causeGenerator = requireNonNull(causeGenerator);
        this.children = requireNonNull(children);
    }

    public static CompoundEvaluation ofTrue(final String name, final Rationale rationale,
                                            final CauseGenerator causeGenerator, final List<Evaluation> children) {
        return new CompoundEvaluation(Result.TRUE, name, rationale, causeGenerator, children);
    }

    public static CompoundEvaluation ofFalse(final String name, final Rationale rationale,
                                             final CauseGenerator causeGenerator, final List<Evaluation> children) {
        return new CompoundEvaluation(Result.FALSE, name, rationale, causeGenerator, children);
    }

    public static CompoundEvaluation ofSkipped(final String name, final CauseGenerator causeGenerator,
                                               final List<Evaluation> children) {
        return new CompoundEvaluation(Result.SKIPPED, name, SimpleRationale.skipped(), causeGenerator, children);
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

        if (traverser != null) {
            traverseDepthFirst(traverser, TraversalContext.create());
        }
    }

    @Override
    public void traverseDepthFirst(final Traverser traverser, final TraversalContext context) {

        traverser.on(this, context);

        final TraversalContext childContext = context.withIncrementedDepth();

        for (Evaluation child : children) {
            child.traverseDepthFirst(traverser, childContext);
        }
    }
}
