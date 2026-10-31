package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;
import java.util.function.BooleanSupplier;

import static java.util.Objects.requireNonNull;

/**
 * An expression that wraps a {@link BooleanSupplier}. This allows any lambda
 * that returns a boolean value to be wrapped as an expression.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
public final class BooleanExpression implements TerminalExpression {

    private final String name;
    private final BooleanSupplier supplier;

    /**
     * Creates a boolean expression.
     *
     * @param name
     *         The name of the expression.
     * @param supplier
     *         The boolean supplier to wrap.
     *
     * @throws NullPointerException
     *         The supplied name or supplier is {@code null}.
     */
    public BooleanExpression(final String name, final BooleanSupplier supplier) {
        this.name = requireNonNull(name);
        this.supplier = requireNonNull(supplier);
    }

    /**
     * Creates a boolean expression with a default name.
     *
     * @param supplier
     *         The boolean supplier to wrap.
     *
     * @throws NullPointerException
     *         The supplied supplier is {@code null}.
     */
    public BooleanExpression(final BooleanSupplier supplier) {
        this(DEFAULT_NAME, supplier);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public TerminalEvaluation evaluate() {

        final StopWatch stopWatch = StopWatch.started();
        final boolean passed = supplier.getAsBoolean();
        final Rationale rationale = new SimpleRationale("true", String.valueOf(passed));
        final Duration elapsedTime = stopWatch.stop();

        return new TerminalEvaluation(passed, name, rationale, elapsedTime);
    }
}
