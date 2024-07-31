package io.github.libzeal.zeal.expression.predicate.unary;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.evaluation.Rationale;
import io.github.libzeal.zeal.expression.evaluation.Result;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class SkippedCompoundUnaryPredicateEvaluation<T> implements Evaluation {

    private final String name;
    private final List<UnaryPredicate<T>> children;

    public SkippedCompoundUnaryPredicateEvaluation(final String name, final List<UnaryPredicate<T>> children) {
        this.name = requireNonNull(name);
        this.children = requireNonNull(children);
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
        return Rationale.skipped();
    }

    @Override
    public List<Evaluation> children() {

        final List<Evaluation> evaluatedChildren = new ArrayList<>(children.size());

        for (UnaryPredicate<?> child : children) {
            evaluatedChildren.add(child.skip());
        }

        return evaluatedChildren;
    }
}
