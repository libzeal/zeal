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
     *     The supplied name is {@code null} or the children contains a {@code null} value.
     */
    public DisjunctiveExpression(String name, List<Expression> children) {
        this.name = requireNonNull(name);
        this.children = requireDoesNotContainNulls(children);
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
     *     The supplied name is {@code null} or the children contains a {@code null} value.
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
     * @throws NullPointerException
     *     The supplied name is {@code null}.
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

        final int expectedPassed = children.isEmpty() ? 0 : 1;
        final CompoundRationaleBuilder builder = CompoundRationaleBuilder.withExpectedPassed(expectedPassed);

        return new CompoundEvaluator(
            name,
            Tally::anyPassed,
            Tally::allFailed,
            builder
        ).evaluate(children);
    }
}
