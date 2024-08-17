package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.AssertionFailedError;
import io.github.libzeal.zeal.expression.lang.evaluation.format.Formatters;
import io.github.libzeal.zeal.expression.lang.unary.UnaryExpression;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.format.SimpleEvaluationFormatter;

/**
 * Responsible for evaluating assertions.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class Confirmation {

    static final String DEFAULT_MESSAGE = "Assertion failed";
    private final AssertionExpressionEvaluator<AssertionFailedError, AssertionFailedError> evaluator;

    public Confirmation() {
        this.evaluator = new AssertionExpressionEvaluator<>(
            Formatters.defaultFormatter(),
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
     * Ensures that the supplied expression (assertion) evaluates to true. A default message is used for any
     * exceptions thrown during this evaluation.
     *
     * @param expression
     *     The expression to evaluate.
     * @param <T>
     *     The type of the subject of the expression.
     *
     * @return The subject of the expression if the expression evaluates to true.
     *
     * @throws NullPointerException
     *     One of the following conditions occurred:
     *     <ol>
     *         <li>The supplied expression was {@code null}</li>
     *         <li>The supplied expression provides a {@code null} {@link Evaluation}</li>
     *         <li>The supplied expression provides a {@code null} evaluation {@link Result}</li>
     *     </ol>
     * @throws AssertionFailedError
     *     The supplied expression evaluates to false.
     */
    public <T> T confirm(final UnaryExpression<T> expression) {
        return confirm(expression, DEFAULT_MESSAGE);
    }

    /**
     * Ensures that the supplied expression (assertion) evaluates to true.
     *
     * @param expression
     *     The expression to evaluate.
     * @param message
     *     The message to include in any exception thrown if the supplied evaluation fails.
     * @param <T>
     *     The type of the subject of the expression.
     *
     * @return The subject of the expression if the expression evaluates to true.
     *
     * @throws NullPointerException
     *     One of the following conditions occurred:
     *     <ol>
     *         <li>The supplied expression was {@code null}</li>
     *         <li>The supplied expression provides a {@code null} {@link Evaluation}</li>
     *         <li>The supplied expression provides a {@code null} evaluation {@link Result}</li>
     *     </ol>
     * @throws AssertionFailedError
     *     The supplied expression evaluates to false.
     */
    public <T> T confirm(final UnaryExpression<T> expression, final String message) {
        return evaluator.evaluate(expression, message);
    }
}
