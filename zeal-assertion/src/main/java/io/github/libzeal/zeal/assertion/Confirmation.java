package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.AssertionFailedError;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.format.SimpleEvaluationFormatter;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;
import org.jspecify.annotations.Nullable;

/**
 * An assertion evaluator responsible for evaluating assertions as invariants
 * (rather than as a pre- or post-condition).
 *
 * @author Justin Albano
 */
public final class Confirmation {

    static final String DEFAULT_MESSAGE = "Assertion failed";
    private final AssertionExpressionEvaluator<AssertionFailedError, AssertionFailedError> evaluator;

    private Confirmation() {
        this.evaluator = new AssertionExpressionEvaluator<>(
                new SimpleEvaluationFormatter(),
                AssertionFailedError::new,
                AssertionFailedError::new
        );
    }

    /**
     * Creates a new confirmation with a default configuration.
     *
     * @return A confirmation with a default configuration.
     */
    public static Confirmation create() {
        return new Confirmation();
    }

    /**
     * Ensures that the supplied expression (assertion) evaluates to true. If
     * the expression evaluates to false, the exception message starts with a
     * default message.
     *
     * @param expression
     *         The expression to evaluate.
     * @param <T>
     *         The type of the subject of the expression.
     *
     * @return The subject of the expression if the expression evaluates to
     * true. This value may be {@code null} if the subject of the
     * {@link UnaryExpression} is {@code null}.
     *
     * @throws NullPointerException
     *         One of the following conditions occurred:
     *         <ol>
     *             <li>The supplied expression was {@code null}</li>
     *             <li>The supplied expression evaluates to {@code null} (a
     *             {@code null} {@link Evaluation})</li>
     *         </ol>
     * @throws AssertionFailedError
     *         The supplied expression evaluates to false.
     */
    public <T> T confirm(final UnaryExpression<T> expression) {
        return confirm(expression, DEFAULT_MESSAGE);
    }

    /**
     * Ensures that the supplied expression (assertion) evaluates to true.
     *
     * @param expression
     *         The expression to evaluate.
     * @param message
     *         The message to include in any exception thrown if the supplied
     *         evaluation fails. If the supplied message is {@code null}, no
     *         message is included before the formatted evaluation.
     * @param <T>
     *         The type of the subject of the expression.
     *
     * @return The subject of the expression if the expression evaluates to
     * true. This value may be {@code null} if the subject of the
     * {@link UnaryExpression} is {@code null}.
     *
     * @throws NullPointerException
     *         One of the following conditions occurred:
     *         <ol>
     *             <li>The supplied expression was {@code null}</li>
     *             <li>The supplied expression evaluates to {@code null} (a
     *             {@code null} {@link Evaluation})</li>
     *         </ol>
     * @throws AssertionFailedError
     *         The supplied expression evaluates to false.
     */
    public <T> T confirm(final UnaryExpression<T> expression, final @Nullable String message) {
        return evaluator.evaluate(expression, message);
    }

    /**
     * Ensures that the supplied expression (assertion) evaluates to true. If
     * the expression evaluates to false, the exception message starts with a
     * default message.
     * <p>
     * If a {@link UnaryExpression} is passed to this method (even if declared
     * as an {@link Expression}), it is evaluated in the same way as
     * {@link #confirm(UnaryExpression)}.
     *
     * @param expression
     *         The expression to evaluate.
     *
     * @throws NullPointerException
     *         One of the following conditions occurred:
     *         <ol>
     *             <li>The supplied expression was {@code null}</li>
     *             <li>The supplied expression evaluates to {@code null} (a
     *             {@code null} {@link Evaluation})</li>
     *         </ol>
     * @throws AssertionFailedError
     *         The supplied expression evaluates to false.
     */
    public void confirm(final Expression expression) {
        evaluator.evaluate(expression, DEFAULT_MESSAGE);
    }

    /**
     * Ensures that the supplied expression (assertion) evaluates to true.
     * <p>
     * If a {@link UnaryExpression} is passed to this method (even if declared
     * as an {@link Expression}), it is evaluated in the same way as
     * {@link #confirm(UnaryExpression, String)}.
     *
     * @param expression
     *         The expression to evaluate.
     * @param message
     *         The message to include in any exception thrown if the supplied
     *         evaluation fails. If the supplied message is {@code null}, no
     *         message is included before the formatted evaluation.
     *
     * @throws NullPointerException
     *         One of the following conditions occurred:
     *         <ol>
     *             <li>The supplied expression was {@code null}</li>
     *             <li>The supplied expression evaluates to {@code null} (a
     *             {@code null} {@link Evaluation})</li>
     *         </ol>
     * @throws AssertionFailedError
     *         The supplied expression evaluates to false.
     */
    public void confirm(final Expression expression, final @Nullable String message) {
        evaluator.evaluate(expression, message);
    }
}
