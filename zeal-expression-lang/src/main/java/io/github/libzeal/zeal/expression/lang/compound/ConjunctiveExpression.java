package io.github.libzeal.zeal.expression.lang.compound;

import io.github.libzeal.zeal.expression.lang.Expression;
import io.github.libzeal.zeal.expression.lang.compound.CompoundEvaluator.Tally;
import io.github.libzeal.zeal.expression.lang.evaluation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public Evaluation skip(final Cause cause) {

        List<Evaluation> evaluations = children.stream()
            .map(child -> child.skip(cause))
            .collect(Collectors.toList());

        return CompoundEvaluation.ofSkipped(name, cause, evaluations);
    }

    @Override
    public Evaluation evaluate() {

        final CompoundRationaleBuilder builder = CompoundRationaleBuilder.withExpected("All children must pass");

        return new CompoundEvaluator(
            Tally::allPassed,
            Tally::anyFailed,
            builder
        ).evaluate(name, children);
    }
}
