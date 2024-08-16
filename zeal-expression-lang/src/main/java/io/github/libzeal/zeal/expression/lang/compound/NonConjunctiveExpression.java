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

import static java.util.Objects.requireNonNull;

public class NonConjunctiveExpression implements CompoundExpression {

    private final String name;
    private final List<Expression> children;

    public NonConjunctiveExpression(String name, List<Expression> children) {
        this.name = requireNonNull(name);
        this.children = requireNonNull(children);
    }

    /**
     * Creates a new non-conjunctive expression using a default name.
     *
     * @param children
     *     The expressions used to initialize the compound expression.
     *
     * @return A non-conjunctive expression with a default name.
     */
    public static NonConjunctiveExpression withDefaultName(final List<Expression> children) {
        return new NonConjunctiveExpression("Not all (NAND)", children);
    }

    public NonConjunctiveExpression(String name) {
        this(name, new ArrayList<>(1));
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void append(final Expression expression) {
        this.children.add(expression);
    }

    @Override
    public void prepend(final Expression expression) {
        this.children.add(0, expression);
    }

    @Override
    public Evaluation skip() {
        return new SkippedCompoundEvaluation(name, children);
    }

    @Override
    public Evaluation evaluate() {

        final CompoundEvaluator evaluator = CompoundEvaluator.skipAfter(Tally::atLeastOneFailed);
        final CompoundEvaluation result = evaluator.evaluate(children);

        final Result compountResult = computeResult(result.tally());
        final Rationale rationale =
            CompoundRationaleBuilder.withExpected("At least one child must fail").build(result.tally());

        return new SimpleEvaluation(name, compountResult, rationale, result.evaluations());
    }

    private Result computeResult(final Tally tally) {

        if (tally.total() == 0) {
            return Result.TRUE;
        }
        else if (tally.failed() > 0) {
            return Result.TRUE;
        }
        else if (tally.passed() == tally.total()) {
            return Result.FALSE;
        }
        else {
            return Result.SKIPPED;
        }
    }
}
