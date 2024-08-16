package io.github.libzeal.zeal.expression.lang.compound;

import io.github.libzeal.zeal.expression.lang.Expression;
import io.github.libzeal.zeal.expression.lang.compound.CompoundEvaluator.Tally;
import io.github.libzeal.zeal.expression.lang.evaluation.CompoundSkippedEvaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.SkippedEvaluation;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class NonConjunctiveExpression implements CompoundExpression {

    static final String DEFAULT_NAME = "Not all (NAND)";
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
        return new NonConjunctiveExpression(DEFAULT_NAME, children);
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
    public SkippedEvaluation skip() {
        return CompoundSkippedEvaluation.from(name, children);
    }

    @Override
    public Evaluation evaluate() {

        final CompoundRationaleBuilder builder = CompoundRationaleBuilder.withExpected("At least one child must fail");

        return new CompoundEvaluator(
            Tally::anyFailed,
            Tally::allPassed,
            builder
        ).evaluate(name, children);
    }
}
