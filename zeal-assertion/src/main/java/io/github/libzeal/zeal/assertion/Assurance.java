package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.PostconditionFailedException;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.format.SimpleEvaluationFormatter;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;
import org.jspecify.annotations.Nullable;

/**
 * An assertion evaluator responsible for evaluating assertions as
 * post-conditions.
 *
 * @author Justin Albano
 */
public final class Assurance {

    static final String DEFAULT_MESSAGE = "Postcondition failed";
    private final AssertionExpressionEvaluator<PostconditionFailedException, PostconditionFailedException> evaluator;

    private Assurance() {
        this.evaluator = new AssertionExpressionEvaluator<>(
                new SimpleEvaluationFormatter(),
                PostconditionFailedException::new,
                PostconditionFailedException::new
        );
    }

    /**
     * Creates a new assurance with a default configuration.
     *
     * @return An assurance with a default configuration.
     */
    public static Assurance create() {
        return new Assurance();
    }

    /**
     * Ensures that the supplied expression (postcondition) evaluates to true.
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
     *         </ol>
     * @throws PostconditionFailedException
     *         The supplied expression evaluates to false.
     */
    public <T> T ensure(final UnaryExpression<T> expression) {
        return ensure(expression, DEFAULT_MESSAGE);
    }

    /**
     * Ensures that the supplied expression (postcondition) evaluates to true.
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
     * @throws PostconditionFailedException
     *         The supplied expression evaluates to false.
     */
    public <T> T ensure(final UnaryExpression<T> expression, final @Nullable String message) {
        return evaluator.evaluate(expression, message);
    }

    /**
     * Ensures that the supplied expression (postcondition) evaluates to true.
     * If the expression evaluates to false, the exception message starts with a
     * default message.
     * <p>
     * If a {@link UnaryExpression} is passed to this method (even if declared
     * as an {@link Expression}), it is evaluated in the same way as
     * {@link #ensure(UnaryExpression)}.
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
     * @throws PostconditionFailedException
     *         The supplied expression evaluates to false.
     */
    public void ensure(final Expression expression) {
        evaluator.evaluate(expression, DEFAULT_MESSAGE);
    }

    /**
     * Ensures that the supplied expression (postcondition) evaluates to true.
     * <p>
     * If a {@link UnaryExpression} is passed to this method (even if declared
     * as an {@link Expression}), it is evaluated in the same way as
     * {@link #ensure(UnaryExpression, String)}.
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
     * @throws PostconditionFailedException
     *         The supplied expression evaluates to false.
     */
    public void ensure(final Expression expression, final @Nullable String message) {
        evaluator.evaluate(expression, message);
    }
}
