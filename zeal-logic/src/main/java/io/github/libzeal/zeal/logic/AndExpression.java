package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.CompoundEvaluator.CompoundRationaleBuilder;
import io.github.libzeal.zeal.logic.CompoundEvaluator.Tally;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.github.libzeal.zeal.logic.util.ArgumentValidator.requireDoesNotContainNulls;
import static java.util.Objects.requireNonNull;

/**
 * A compound expression where all sub-expression must be pass for the compound expression to pass.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class AndExpression implements CompoundExpression {

    static final String DEFAULT_NAME = "AND";
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
     *     The supplied name is {@code null} or the children contains a {@code null} value.
     */
    public AndExpression(String name, List<Expression> children) {
        this.name = requireNonNull(name);
        this.children = requireDoesNotContainNulls(children);
    }

    /**
     * Creates a new conjunctive expression using a default name.
     *
     * @param children
     *     The expressions used to initialize the compound expression.
     *
     * @return A conjunctive expression with a default name.
     *
     * @throws NullPointerException
     *     The supplied name is {@code null} or the children contains a {@code null} value.
     */
    public static AndExpression unnamed(final List<Expression> children) {
        return new AndExpression(DEFAULT_NAME, children);
    }

    /**
     * Creates a new conjunctive expression using a default name.
     *
     * @param children
     *     The expressions used to initialize the compound expression.
     *
     * @return A conjunctive expression with a default name.
     *
     * @throws NullPointerException
     *     The supplied name is {@code null} or the children contains a {@code null} value.
     */
    public static AndExpression unnamed(final Expression... children) {
        return new AndExpression(DEFAULT_NAME, Arrays.asList(children));
    }

    /**
     * Creates a new conjunctive expression with an empty default sub-expression list.
     *
     * @param name
     *     The name of the expression.
     *
     * @throws NullPointerException
     *     The supplied name is {@code null}.
     */
    public AndExpression(String name) {
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
    public List<Expression> children() {
        return children;
    }

    @Override
    public Evaluation evaluate() {

        final CompoundRationaleBuilder builder = CompoundRationaleBuilder.withExpectedPassed(children.size());

        return new CompoundEvaluator(
            name,
            Tally::allPassed,
            Tally::anyFailed,
            builder
        ).evaluate(children);
    }
}
