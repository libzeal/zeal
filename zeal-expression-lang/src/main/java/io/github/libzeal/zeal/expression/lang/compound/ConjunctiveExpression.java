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

/**
 * A compound expression where all sub-expression must be pass for the compound expression to pass.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class ConjunctiveExpression implements CompoundExpression {

    static final String DEFAULT_NAME = "All (AND)";
    private final String name;
    private final List<Expression> children;

    /**
     * Creates a new conjunctive expression.
     *
     * @param name
     *     The name of the expression.
     * @param children
     *     The expressions used to initialize the compound expression.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public ConjunctiveExpression(String name, List<Expression> children) {
        this.name = requireNonNull(name);
        this.children = requireNonNull(children);
    }

    /**
     * Creates a new conjunctive expression using a default name.
     *
     * @param children
     *     The expressions used to initialize the compound expression.
     *
     * @return A conjunctive expression with a default name.
     */
    public static ConjunctiveExpression withDefaultName(final List<Expression> children) {
        return new ConjunctiveExpression(DEFAULT_NAME, children);
    }

    /**
     * Creates a new conjunctive expression with an empty default sub-expression list.
     *
     * @param name
     *     The name of the expression.
     *
     * @implNote The default sub-expression list has an initial capacity of 1.
     */
    public ConjunctiveExpression(String name) {
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
            CompoundRationaleBuilder.withExpected("All children must pass").build(result.tally());

        return new SimpleEvaluation(name, compountResult, rationale, result.evaluations());
    }

    private Result computeResult(final Tally tally) {

        if (tally.total() == 0) {
            return Result.TRUE;
        }
        else if (tally.failed() > 0) {
            return Result.FALSE;
        }
        else if (tally.passed() == tally.total()) {
            return Result.TRUE;
        }
        else {
            return Result.SKIPPED;
        }
    }
}
