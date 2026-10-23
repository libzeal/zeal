package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.CompoundEvaluator.CompoundRationaleBuilder;
import io.github.libzeal.zeal.logic.CompoundEvaluator.Tally;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A compound expression where at least one sub-expression must be pass for the
 * compound expression to pass.
 *
 * @author Justin Albano
 */
public record OrExpression(String name,
                           List<Expression> children) implements CompoundExpression {

    static final String DEFAULT_NAME = "OR";

    /**
     * Creates a new disjunctive expression.
     *
     * @param name
     *     The name of the expression.
     * @param children
     *     The expressions used initialize to the compound expression.
     *
     * @throws NullPointerException
     *     The supplied name is {@code null} or the children contains a
     *     {@code null} value.
     */
    public OrExpression {
        requireNonNull(name);
        children = List.copyOf(children);
    }

    /**
     * Creates a new disjunctive expression using a default name.
     *
     * @param children
     *     The expressions used to initialize the compound expression.
     *
     * @return A disjunctive expression with a default name.
     *
     * @throws NullPointerException
     *     The supplied name is {@code null} or the children contains a
     *     {@code null} value.
     */
    public static OrExpression unnamed(final List<Expression> children) {
        return new OrExpression(DEFAULT_NAME, children);
    }

    /**
     * Creates a new disjunctive expression using a default name.
     *
     * @param children
     *     The expressions used to initialize the compound expression.
     *
     * @return A disjunctive expression with a default name.
     *
     * @throws NullPointerException
     *     The supplied name is {@code null} or the children contains a
     *     {@code null} value.
     */
    public static OrExpression unnamed(final Expression... children) {
        return new OrExpression(DEFAULT_NAME, List.of(children));
    }

    @Override
    public Evaluation evaluate() {

        final int expectedPassed = children.isEmpty() ? 0 : 1;
        final CompoundRationaleBuilder builder = CompoundRationaleBuilder.withExpectedPassed(expectedPassed);

        return new CompoundEvaluator(
            name,
            Result.TRUE,
            Tally::anyPassed,
            builder
        ).evaluate(children);
    }
}
