package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.EvaluatedCompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * An expression that negates a wrapped expression (a logical <em>not</em>).
 * <p>
 * This expression evaluates to true if and only if the wrapped expression is false; otherwise (the wrapped expression
 * is true or skipped), the expression evaluates to false.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class NotExpression implements Expression {

    /**
     * The actual value for the rationale if the wrapped expression is true.
     */
    public static final String ACTUAL_VALUE_WRAPPED_TRUE = "Wrapped: true";

    /**
     * The actual value for the rationale if the wrapped expression is false.
     */
    public static final String ACTUAL_VALUE_WRAPPED_FALSE = "Wrapped: false";

    /**
     * The actual value for the rationale if the wrapped expression is skip.
     */
    public static final String ACTUAL_VALUE_WRAPPED_SKIPPED = "Wrapped: skipped";

    /**
     * The default name of a negated expression.
     */
    public static final String DEFAULT_NAME = "NOT";

    private final String name;
    private final Expression wrapped;

    /**
     * Creates a new expression that negates the supplied expression.
     *
     * @param wrapped
     *     The expression to negate.
     *
     * @throws NullPointerException
     *     The supplied wrapped expression is {@code null}.
     */
    public NotExpression(final String name, final Expression wrapped) {
        this.name = name;
        this.wrapped = requireNonNull(wrapped);
    }

    /**
     * Creates a new expression that negates the supplied expression.
     *
     * @param wrapped
     *     The expression to negate.
     *
     * @throws NullPointerException
     *     The supplied wrapped expression is {@code null}.
     */
    public static NotExpression unnamed(final Expression wrapped) {
        return new NotExpression(DEFAULT_NAME, wrapped);
    }

    @Override
    public Evaluation evaluate() {

        final StopWatch stopWatch = StopWatch.started();
        final Evaluation wrappedEvaluation = wrapped.evaluate();
        final Result result = result(wrappedEvaluation);
        final String actualValue = actualValue(wrappedEvaluation);
        final Rationale rationale = new SimpleRationale(ACTUAL_VALUE_WRAPPED_FALSE, actualValue);
        final Cause cause = wrappedEvaluation.cause();

        final List<Evaluation> children = new ArrayList<>(1);
        children.add(wrappedEvaluation);

        final Duration elapsedTime = stopWatch.stop();

        if (result.isFalse() || result.isSkipped()) {
            return EvaluatedCompoundEvaluation.ofFalse(name(), rationale, elapsedTime, CauseGenerator.withUnderlyingCause(cause),
                children);
        }
        else {
            return EvaluatedCompoundEvaluation.ofTrue(name(), rationale, elapsedTime, CauseGenerator.withUnderlyingCause(cause), children);
        }
    }

    private static Result result(final Evaluation wrappedEvaluation) {

        final Result result = wrappedEvaluation.result();

        if (result.isTrue()) {
            return Result.FALSE;
        }
        else if (result.isFalse()) {
            return Result.TRUE;
        }
        else {
            return Result.SKIPPED;
        }
    }

    private static String actualValue(final Evaluation wrappedEvaluation) {

        final Result result = wrappedEvaluation.result();

        if (result.isTrue()) {
            return ACTUAL_VALUE_WRAPPED_TRUE;
        }
        else if (result.isFalse()) {
            return ACTUAL_VALUE_WRAPPED_FALSE;
        }
        else {
            return ACTUAL_VALUE_WRAPPED_SKIPPED;
        }
    }

    @Override
    public String name() {
        return name;
    }

    public Expression wrapped() {
        return wrapped;
    }
}
