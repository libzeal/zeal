package io.github.libzeal.zeal.expression.lang.compound;

import io.github.libzeal.zeal.expression.lang.Expression;
import io.github.libzeal.zeal.expression.lang.compound.CompoundEvaluator.Tally;
import io.github.libzeal.zeal.expression.lang.evaluation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * A compound expression where at least one sub-expression must be pass for the compound expression to pass.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class DisjunctiveExpression implements CompoundExpression {

    static final String DEFAULT_NAME = "Any (OR)";
    private final String name;
    private final List<Expression> children;

    /**
     * Creates a new disjunctive expression.
     *
     * @param name
     *     The name of the expression.
     * @param children
     *     The expressions used initialize to the compound expression.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public DisjunctiveExpression(String name, List<Expression> children) {
        this.name = requireNonNull(name);
        this.children = requireNonNull(children);
    }

    /**
     * Creates a new disjunctive expression using a default name.
     *
     * @param children
     *     The expressions used to initialize the compound expression.
     *
     * @return A disjunctive expression with a default name.
     */
    public static DisjunctiveExpression withDefaultName(final List<Expression> children) {
        return new DisjunctiveExpression(DEFAULT_NAME, children);
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
    public Evaluation skip(final RootCause rootCause) {

        List<Evaluation> evaluations = children.stream()
            .map(child -> child.skip(rootCause))
            .collect(Collectors.toList());

        return CompoundEvaluation.ofSkipped(name, rootCause, evaluations);
    }

    @Override
    public Evaluation evaluate() {

        final CompoundRationaleBuilder builder = CompoundRationaleBuilder.withExpected("At least one child must pass");

        return new CompoundEvaluator(
            Tally::anyPassed,
            Tally::allFailed,
            builder
        ).evaluate(name, children);
    }
}
