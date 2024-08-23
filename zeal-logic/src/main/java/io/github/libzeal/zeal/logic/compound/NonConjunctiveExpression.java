package io.github.libzeal.zeal.logic.compound;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.compound.CompoundEvaluator.CompoundRationaleBuilder;
import io.github.libzeal.zeal.logic.compound.CompoundEvaluator.Tally;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.libzeal.zeal.logic.util.ArgumentValidator.requireDoesNotContainNulls;
import static java.util.Objects.requireNonNull;

public class NonConjunctiveExpression implements CompoundExpression {

    public static final String DEFAULT_NAME = "Not all (NAND)";
    private final String name;
    private final List<Expression> children;

    public NonConjunctiveExpression(String name, List<Expression> children) {
        this.name = requireNonNull(name);
        this.children = requireDoesNotContainNulls(children);
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
    public Evaluation skip(final Cause cause) {

        List<Evaluation> evaluations = children.stream()
            .map(child -> child.skip(cause))
            .collect(Collectors.toList());

        return CompoundEvaluation.ofSkipped(name, CauseGenerator.withUnderlyingCause(cause), evaluations);
    }

    @Override
    public Evaluation evaluate() {

        final int expectedFailed = children.isEmpty() ? 0 : 1;
        final CompoundRationaleBuilder builder = CompoundRationaleBuilder.withExpectedFailed(expectedFailed);

        return new CompoundEvaluator(
            name,
            Tally::anyFailed,
            Tally::allPassed,
            builder
        ).evaluate(children);
    }
}
