package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.PostconditionFailedException;
import io.github.libzeal.zeal.expression.lang.UnaryExpression;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.format.SimpleEvaluationFormatter;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;

/**
 * Responsible for evaluating postconditions.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class Assurance {

    static final String DEFAULT_MESSAGE = "Postcondition failed";
    private final AssertionExpressionEvaluator evaluator;

    private Assurance() {
        this.evaluator = new AssertionExpressionEvaluator(
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
     * Ensures that the supplied expression (postcondition) evaluates to true. A default message is used for any
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
     * @throws PostconditionFailedException
     *     The supplied expression evaluates to false.
     */
    public <T> T ensure(final UnaryExpression<T> expression) {
        return ensure(expression, DEFAULT_MESSAGE);
    }

    /**
     * Ensures that the supplied expression (postcondition) evaluates to true.
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
     * @throws PostconditionFailedException
     *     The supplied expression evaluates to false.
     */
    public <T> T ensure(final UnaryExpression<T> expression, final String message) {
        return evaluator.evaluate(expression, message);
    }
}
