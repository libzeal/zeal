package io.github.libzeal.zeal.expression.lang.evaluation;

import io.github.libzeal.zeal.expression.lang.Expression;
import io.github.libzeal.zeal.expression.lang.rationale.Rationale;
import io.github.libzeal.zeal.expression.lang.rationale.SimpleRationale;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class CompoundSkippedEvaluation implements SkippedEvaluation {

    private final String name;
    private final List<Evaluation> children;

    public CompoundSkippedEvaluation(final String name, final List<Evaluation> children) {
        this.name = requireNonNull(name);
        this.children = requireNonNull(children);
    }

    public static CompoundSkippedEvaluation from(final String name, final List<Expression> expressions) {

        requireNonNull(expressions, "Cannot create evaluation from null expression list");

        List<Evaluation> evaluations = expressions.stream()
            .map(Expression::skip)
            .collect(Collectors.toList());

        return new CompoundSkippedEvaluation(name, evaluations);
    }

    @Override
    public Result result() {
        return Result.SKIPPED;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Rationale rationale() {
        return SimpleRationale.skipped();
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

        for (Evaluation child : children) {
            child.traverseDepthFirst(traverser);
        }
    }
}
