package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.PreconditionFailedException;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.format.SimpleEvaluationFormatter;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;
import org.jspecify.annotations.Nullable;

/**
 * An assertion evaluator responsible for evaluating assertions as
 * pre-conditions.
 *
 * @author Justin Albano
 */
public final class Requirement {

    static final String DEFAULT_MESSAGE = "Precondition failed";
    private final AssertionExpressionEvaluator<NullPointerException, PreconditionFailedException> evaluator;

    private Requirement() {
        this.evaluator = new AssertionExpressionEvaluator<>(
                new SimpleEvaluationFormatter(),
                NullPointerException::new,
                PreconditionFailedException::new
        );
    }

    /**
     * Creates a new requirement with a default configuration.
     *
     * @return A requirement with a default configuration.
     */
    public static Requirement create() {
        return new Requirement();
    }

    /**
     * Requires that the supplied expression (precondition) evaluates to true.
     * If the expression evaluates to false, the exception message starts with a
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
     *             <li>The supplied expression evaluates to false and the
     *             subject of the expression is {@code null}</li>
     *         </ol>
     * @throws PreconditionFailedException
     *         The supplied expression evaluates to false and the subject of the
     *         expression is not {@code null}.
     */
    public <T> T require(final UnaryExpression<T> expression) {
        return require(expression, DEFAULT_MESSAGE);
    }

    /**
     * Requires that the supplied expression (precondition) evaluates to true.
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
     *             <li>The supplied expression evaluates to false and the
     *             subject of the expression is {@code null}</li>
     *         </ol>
     * @throws PreconditionFailedException
     *         The supplied expression evaluates to false and the subject of the
     *         expression is not {@code null}.
     */
    public <T> T require(final UnaryExpression<T> expression, final @Nullable String message) {
        return evaluator.evaluate(expression, message);
    }

    /**
     * Requires that the supplied expression (precondition) evaluates to true.
     * If the expression evaluates to false, the exception message starts with a
     * default message.
     * <p>
     * If a {@link UnaryExpression} is passed to this method (even if declared
     * as an {@link Expression}), it is evaluated in the same way as
     * {@link #require(UnaryExpression)}.
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
     *             <li>The supplied expression is a {@link UnaryExpression} that
     *             evaluates to false and the subject of the expression is
     *             {@code null}</li>
     *         </ol>
     * @throws PreconditionFailedException
     *         The supplied expression evaluates to false and is either:
     *         <ol>
     *             <li>Not a {@link UnaryExpression}</li>
     *             <li>A {@link UnaryExpression} with a subject that is not
     *             {@code null}</li>
     *         </ol>
     */
    public void require(final Expression expression) {
        evaluator.evaluate(expression, DEFAULT_MESSAGE);
    }

    /**
     * Requires that the supplied expression (precondition) evaluates to true.
     * <p>
     * If a {@link UnaryExpression} is passed to this method (even if declared
     * as an {@link Expression}), it is evaluated in the same way as
     * {@link #require(UnaryExpression, String)}.
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
     *             <li>The supplied expression is a {@link UnaryExpression} that
     *             evaluates to false and the subject of the expression is
     *             {@code null}</li>
     *         </ol>
     * @throws PreconditionFailedException
     *         The supplied expression evaluates to false and is either:
     *         <ol>
     *             <li>Not a {@link UnaryExpression}</li>
     *             <li>A {@link UnaryExpression} with a subject that is not
     *             {@code null}</li>
     *         </ol>
     */
    public void require(final Expression expression, final @Nullable String message) {
        evaluator.evaluate(expression, message);
    }
}
