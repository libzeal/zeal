package io.github.libzeal.zeal.expression.lang.compound;

import io.github.libzeal.zeal.expression.lang.Expression;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.rationale.Rationale;
import io.github.libzeal.zeal.expression.lang.rationale.SimpleRationale;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A skipped {@link Evaluation} created by a {@link CompoundExpression}.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
class SkippedCompoundEvaluation implements Evaluation {

    private final String name;
    private final List<Expression> children;

    /**
     * Creates a new skipped evaluation.
     *
     * @param name
     *     The name of the expression that generated the evaluation.
     * @param children
     *     The children of the compound expression that generated the evaluation.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public SkippedCompoundEvaluation(final String name, final List<Expression> children) {
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
        return SimpleRationale.skipped();
    }

    @Override
    public List<Evaluation> children() {

        final List<Evaluation> evaluatedChildren = new ArrayList<>(children.size());

        for (Expression child : children) {
            evaluatedChildren.add(child.skip());
        }

        return evaluatedChildren;
    }
}
