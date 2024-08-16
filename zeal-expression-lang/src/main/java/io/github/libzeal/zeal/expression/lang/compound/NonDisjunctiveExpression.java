package io.github.libzeal.zeal.expression.lang.compound;

import io.github.libzeal.zeal.expression.lang.Expression;
import io.github.libzeal.zeal.expression.lang.compound.CompoundEvaluator.CompoundEvaluation;
import io.github.libzeal.zeal.expression.lang.compound.CompoundEvaluator.Tally;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.SimpleEvaluation;
import io.github.libzeal.zeal.expression.lang.rationale.Rationale;

import java.util.ArrayList;
import java.util.List;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.*;
import static java.util.Objects.requireNonNull;

public class NonDisjunctiveExpression implements CompoundExpression {

    private final String name;
    private final List<Expression> children;

    public NonDisjunctiveExpression(String name, List<Expression> children) {
        this.name = requireNonNull(name);
        this.children = requireNonNull(children);
    }

    /**
     * Creates a new non-disjunctive expression using a default name.
     *
     * @param children
     *     The expressions used to initialize the compound expression.
     *
     * @return A non-disjunctive expression with a default name.
     */
    public static NonDisjunctiveExpression withDefaultName(final List<Expression> children) {
        return new NonDisjunctiveExpression("None (NOR)", children);
    }

    public NonDisjunctiveExpression(String name) {
        this(name, new ArrayList<>(1));
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void append(Expression predicate) {
        this.children.add(predicate);
    }

    @Override
    public void prepend(Expression predicate) {
        this.children.add(0, predicate);
    }

    @Override
    public Evaluation skip() {
        return new SkippedCompoundEvaluation(name, children);
    }

    @Override
    public Evaluation evaluate() {

        final CompoundEvaluator evaluator = CompoundEvaluator.skipAfter(Tally::atLeastOnePassed);
        final CompoundEvaluation result = evaluator.evaluate(children);

        final Result compountResult = computeResult(result.tally());
        final Rationale rationale =
            CompoundRationaleBuilder.withExpected("No child passes").build(result.tally());

        return new SimpleEvaluation(name, compountResult, rationale, result.evaluations());
    }

    private Result computeResult(final Tally tally) {

        if (tally.total() == 0) {
            return TRUE;
        }
        else if (tally.passed() > 0) {
            return FALSE;
        }
        else if (tally.failed() > 0 && tally.failed() == tally.total()) {
            return TRUE;
        }
        else {
            return SKIPPED;
        }
    }
}
