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

public class NonDisjunctiveExpression implements CompoundExpression {

    public static final String DEFAULT_NAME = "None (NOR)";
    private final String name;
    private final List<Expression> children;

    public NonDisjunctiveExpression(String name, List<Expression> children) {
        this.name = requireNonNull(name);
        this.children = requireDoesNotContainNulls(children);
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
        return new NonDisjunctiveExpression(DEFAULT_NAME, children);
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
    public Evaluation skip(final Cause cause) {

        List<Evaluation> evaluations = children.stream()
            .map(child -> child.skip(cause))
            .collect(Collectors.toList());

        return CompoundEvaluation.ofSkipped(name, CauseGenerator.withUnderlyingCause(cause), evaluations);
    }

    @Override
    public Evaluation evaluate() {

        final CompoundRationaleBuilder builder = CompoundRationaleBuilder.withExpectedFailed(children.size());

        return new CompoundEvaluator(
            name,
            Tally::allFailed,
            Tally::anyPassed,
            builder
        ).evaluate(children);
    }
}
