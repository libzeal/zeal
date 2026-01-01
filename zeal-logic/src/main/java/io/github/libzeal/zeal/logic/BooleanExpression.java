package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.EvaluatedTerminalEvaluation;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;
import java.util.function.BooleanSupplier;

import static java.util.Objects.requireNonNull;

/**
 * An expression that wraps a {@link BooleanSupplier}. This allows any lambda
 * that returns a boolean value to be wrapped
 * as an expression.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
public class BooleanExpression implements Expression {

    private final String name;
    private final BooleanSupplier predicate;

    /**
     * Creates a boolean expression.
     *
     * @param name
     *         The name of the expression.
     * @param predicate
     *         The boolean predicate to wrap.
     *
     * @throws NullPointerException
     *         The supplied predicate is {@code null}.
     */
    public BooleanExpression(final String name, final BooleanSupplier predicate) {
        this.name = name;
        this.predicate = requireNonNull(predicate);
    }

    /**
     * Creates an unnamed boolean expression.
     *
     * @param predicate
     *         The boolean predicate to wrap.
     *
     * @throws NullPointerException
     *         The supplied predicate is {@code null}.
     */
    public BooleanExpression(final BooleanSupplier predicate) {
        this("(unnamed)", predicate);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Evaluation evaluate() {

        final StopWatch stopWatch = StopWatch.started();
        final boolean passed = predicate.getAsBoolean();
        final Rationale rationale = new SimpleRationale("true", String.valueOf(passed));
        final Duration elapsedTime = stopWatch.stop();

        if (passed) {
            return EvaluatedTerminalEvaluation.ofTrue(name, rationale, elapsedTime);
        }
        else {
            return EvaluatedTerminalEvaluation.ofFalse(name, rationale, elapsedTime);
        }
    }
}
