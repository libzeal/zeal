package io.github.libzeal.zeal.expression.lang;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.SimpleEvaluation;
import io.github.libzeal.zeal.expression.lang.rationale.Rationale;
import io.github.libzeal.zeal.expression.lang.rationale.SimpleRationale;

import java.util.ArrayList;
import java.util.List;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.*;
import static java.util.Objects.requireNonNull;

/**
 * A compound expression where at least one sub-expression must be pass for the compound expression to pass.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class DisjunctiveExpression implements CompoundExpression {

    private final String name;
    private final List<Expression> children;

    /**
     * Creates a new disjunctive expression.
     *
     * @param name
     *     The name of the expression.
     * @param children
     *     The expressions used initialize the compound expression.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public DisjunctiveExpression(String name, List<Expression> children) {
        this.name = requireNonNull(name);
        this.children = requireNonNull(children);
    }

    /**
     * Creates a new disjunctive expression with an empty default sub-expression list.
     *
     * @param name
     *     The name of the expression.
     *
     * @implNote The default sub-expression list has an initial capacity of 1.
     */
    public DisjunctiveExpression(String name) {
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

        final int total = children.size();
        final List<Evaluation> evaluated = new ArrayList<>(total);
        int passed = 0;
        int failed = 0;
        int skipped = 0;

        for (Expression predicate : children) {

            if (passed > 0) {
                evaluated.add(predicate.skip());
            }
            else {
                final Evaluation evaluation = predicate.evaluate();
                final Result result = evaluation.result();

                switch (result) {
                    case PASSED:
                        passed++;
                        break;
                    case FAILED:
                        failed++;
                        break;
                    case SKIPPED:
                        skipped++;
                        break;
                }

                evaluated.add(evaluation);
            }
        }

        final Result compountResult = computeResult(passed, failed, total);
        final Rationale rationale = computeRationale(passed, failed, skipped);

        return new SimpleEvaluation(name, compountResult, rationale, evaluated);
    }

    private Result computeResult(final int passed, final int failed, final int total) {

        if (total == 0) {
            return PASSED;
        }
        else if (passed > 0) {
            return PASSED;
        }
        else if (failed > 0 && failed == total) {
            return FAILED;
        }
        else {
            return SKIPPED;
        }
    }

    private Rationale computeRationale(final int passed, final int failed, final int skipped) {

        final String expected = "At least one child must pass";
        final String actual =
            "Passed: " + passed + ", Failed: " + failed + ", Skipped: " + skipped;

        return new SimpleRationale(expected, actual);
    }
}
