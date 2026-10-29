package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * An expression whose wrapped expression is negated. The NOT expression
 * evaluates to true only if the wrapped expression evaluates to false.
 *
 * @param name
 *         The name of the expression.
 * @param wrapped
 *         The expression to negate.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
public record NotExpression(
        String name,
        Expression wrapped
) implements CompoundExpression {

    static final String ACTUAL_VALUE_WRAPPED_TRUE = "Wrapped: true";
    static final String ACTUAL_VALUE_WRAPPED_FALSE = "Wrapped: false";
    static final String DEFAULT_NAME = "NOT";

    /**
     * Creates a new expression that negates the supplied expression.
     *
     * @param name
     *         The name of the expression.
     * @param wrapped
     *         The expression to negate.
     *
     * @throws NullPointerException
     *         The supplied name or wrapped expression is {@code null}.
     */
    public NotExpression {
        requireNonNull(name);
        requireNonNull(wrapped);
    }

    /**
     * Creates a NOT expression with a default name.
     *
     * @param wrapped
     *         The expression to negate.
     *
     * @return A negated expression that evaluates to the negative of the
     * supplied expression.
     *
     * @throws NullPointerException
     *         The supplied wrapped expression is {@code null}.
     */
    public static NotExpression unnamed(final Expression wrapped) {
        return new NotExpression(DEFAULT_NAME, wrapped);
    }

    @Override
    public List<Expression> children() {
        return List.of(wrapped);
    }

    @Override
    public CompoundEvaluation evaluate() {

        final StopWatch stopWatch = StopWatch.started();
        final Evaluation wrappedEvaluation = wrapped.evaluate();
        final String actualValue = wrappedEvaluation.isTrue() ? ACTUAL_VALUE_WRAPPED_TRUE : ACTUAL_VALUE_WRAPPED_FALSE;
        final Rationale rationale = new SimpleRationale(ACTUAL_VALUE_WRAPPED_FALSE, actualValue);
        final Duration elapsedTime = stopWatch.stop();

        return CompoundEvaluation.of(
                this, !wrappedEvaluation.isTrue(), rationale, elapsedTime,
                Cause.of(wrappedEvaluation), List.of(new Evaluated(wrappedEvaluation))
        );
    }
}
