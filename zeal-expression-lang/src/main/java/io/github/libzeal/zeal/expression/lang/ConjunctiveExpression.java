package io.github.libzeal.zeal.expression.lang;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.SimpleEvaluation;
import io.github.libzeal.zeal.expression.lang.rationale.Rationale;
import io.github.libzeal.zeal.expression.lang.rationale.SimpleRationale;

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

    private final String name;
    private final List<Expression> children;

    /**
     * Creates a new conjunctive expression.
     *
     * @param name
     *     The name of the expression.
     * @param children
     *     The expressions used initialize the compound expression.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public ConjunctiveExpression(String name, List<Expression> children) {
        this.name = requireNonNull(name);
        this.children = requireNonNull(children);
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

        final int total = children.size();
        final List<Evaluation> evaluated = new ArrayList<>(total);
        int passed = 0;
        int failed = 0;
        int skipped = 0;

        for (Expression expression : children) {

            if (failed > 0) {
                evaluated.add(expression.skip());
            }
            else {
                final Evaluation evaluation = expression.evaluate();
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
            return Result.PASSED;
        }
        else if (failed > 0) {
            return Result.FAILED;
        }
        else if (passed == total) {
            return Result.PASSED;
        }
        else {
            return Result.SKIPPED;
        }
    }

    private Rationale computeRationale(final int passed, final int failed, final int skipped) {

        final String expected = "All children must pass";
        final String actual =
            "Passed: " + passed + ", Failed: " + failed + ", Skipped: " + skipped;

        return new SimpleRationale(expected, actual);
    }
}
