package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.PostconditionFailedException;
import io.github.libzeal.zeal.assertion.error.PreconditionFailedException;
import io.github.libzeal.zeal.expression.lang.UnaryExpression;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.format.SimpleEvaluationFormatter;

public class Requirement {

    static final String DEFAULT_MESSAGE = "Precondition failed";
    private final AssertionExpressionEvaluator evaluator;

    public Requirement() {
        this.evaluator = new AssertionExpressionEvaluator(
            new SimpleEvaluationFormatter(),
            NullPointerException::new,
            PreconditionFailedException::new
        );
    }

    public static Requirement create() {
        return new Requirement();
    }


    /**
     * Ensures that the supplied expression (precondition) evaluates to true. A default message is used for any
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
     *         <li>The supplied expression evaluates to false and the subject of the expression is {@code null}</li>
     *     </ol>
     * @throws PreconditionFailedException
     *     The supplied expression evaluates to false and the subject of the expression is not {@code null}.
     */
    public <T> T require(final UnaryExpression<T> expression) {
        return require(expression, DEFAULT_MESSAGE);
    }

    /**
     * Ensures that the supplied expression (precondition) evaluates to true.
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
     *         <li>The supplied expression evaluates to false and the subject of the expression is {@code null}</li>
     *     </ol>
     * @throws PostconditionFailedException
     *     The supplied expression evaluates to false and the subject of the expression is not {@code null}.
     */
    public <T> T require(final UnaryExpression<T> expression, final String message) {
        return evaluator.evaluate(expression, message);
    }
}
