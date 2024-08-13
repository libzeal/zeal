package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.AssertionFailedError;
import io.github.libzeal.zeal.assertion.error.PostconditionFailedException;
import io.github.libzeal.zeal.assertion.error.PreconditionFailedException;
import io.github.libzeal.zeal.expression.lang.UnaryExpression;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;

/**
 * Basic assertions that verify expressions.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class Assertions {

    private Assertions() {
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
     * @see Requirement#require(UnaryExpression)
     */
    public static <T> T require(final UnaryExpression<T> expression) {
        return Requirement.create().require(expression);
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
     * @see Requirement#require(UnaryExpression, String)
     */
    public static <T> T require(final UnaryExpression<T> expression, final String message) {
        return Requirement.create().require(expression, message);
    }

    /**
     * Ensures that the supplied expression (assertion) evaluates to true. A default message is used for any exceptions
     * thrown during this evaluation.
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
     * @see Confirmation#confirm(UnaryExpression)
     */
    public static <T> T confirm(final UnaryExpression<T> expression) {
        return Confirmation.create().confirm(expression);
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
     * @see Confirmation#confirm(UnaryExpression, String)
     */
    public static <T> T confirm(final UnaryExpression<T> expression, final String message) {
        return Confirmation.create().confirm(expression, message);
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
     * @see Assurance#ensure(UnaryExpression)
     */
    public static <T> T ensure(final UnaryExpression<T> expression) {
        return Assurance.create().ensure(expression);
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
     * @see Assurance#ensure(UnaryExpression, String)
     */
    public static <T> T ensure(final UnaryExpression<T> expression, final String message) {
        return Assurance.create().ensure(expression, message);
    }
}
