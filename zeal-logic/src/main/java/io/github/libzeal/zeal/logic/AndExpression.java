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
 * A compound expression where all sub-expression must be pass for the compound
 * expression to pass.
 *
 * @param name
 *     The name of the expression.
 * @param children
 *     The expressions used to initialize the compound expression.
 *
 * @author Justin Albano
 */
public record AndExpression(String name,
                            List<Expression> children) implements CompoundExpression {

    static final String DEFAULT_NAME = "AND";

    /**
     * Creates a new conjunctive expression.
     *
     * @param name
     *     The name of the expression.
     * @param children
     *     The expressions used to initialize the compound expression.
     *
     * @throws NullPointerException
     *     The supplied name is {@code null} or the children contains a
     *     {@code null} value.
     */
    public AndExpression {
        requireNonNull(name);
        requireDoesNotContainNulls(children);
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
     *     The supplied name is {@code null} or the children contains a
     *     {@code null} value.
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
     *     The supplied name is {@code null} or the children contains a
     *     {@code null} value.
     */
    public static AndExpression unnamed(final Expression... children) {
        return new AndExpression(DEFAULT_NAME, Arrays.asList(children));
    }

    /**
     * Creates a new conjunctive expression with an empty default sub-expression
     * list.
     *
     * @param name
     *     The name of the expression.
     *
     * @throws NullPointerException
     *     The supplied name is {@code null}.
     */
    public AndExpression(final String name) {
        this(name, new ArrayList<>(1));
    }

    @Override
    public void append(final Expression expression) {
        this.children.add(expression);
    }

    @Override
    public void prepend(final Expression expression) {
        this.children.addFirst(expression);
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
