package io.github.libzeal.zeal.logic.compound;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.compound.CompoundEvaluator.CompoundRationaleBuilder;
import io.github.libzeal.zeal.logic.compound.CompoundEvaluator.Tally;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.libzeal.zeal.logic.util.ArgumentValidator.requireDoesNotContainNulls;
import static java.util.Objects.requireNonNull;

/**
 * A compound expression where at least one sub-expression must fail for the compound expression to pass.
 *
 * @author Justin Albano
 * @since 0.4.0
 */
public class NonConjunctiveExpression implements CompoundExpression {

    static final String DEFAULT_NAME = "Not all (NAND)";
    private final String name;
    private final List<Expression> children;

    /**
     * Creates a new non-conjunctive expression.
     *
     * @param name
     *     The name of the expression.
     * @param children
     *     The children of the expression.
     *
     * @throws NullPointerException
     *     The supplied name is {@code null} or the children contains a {@code null} value.
     */
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
     *
     * @throws NullPointerException
     *     The supplied name is {@code null} or the children contains a {@code null} value.
     */
    public static NonConjunctiveExpression withDefaultName(final List<Expression> children) {
        return new NonConjunctiveExpression(DEFAULT_NAME, children);
    }

    /**
     * Creates a new non-conjunctive expression with an empty default sub-expression list.
     *
     * @param name
     *     The name of the expression.
     *
     * @throws NullPointerException
     *     The supplied name is {@code null}.
     */
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

        final StopWatch stopWatch = StopWatch.started();
        final List<Evaluation> evaluations = children.stream()
            .map(child -> child.skip(cause))
            .collect(Collectors.toList());

        final Duration elapsedTime = stopWatch.stop();

        return CompoundEvaluation.ofSkipped(name, elapsedTime, CauseGenerator.withUnderlyingCause(cause), evaluations);
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
