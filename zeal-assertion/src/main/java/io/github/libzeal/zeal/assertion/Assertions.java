package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.AssertionFailedError;
import io.github.libzeal.zeal.assertion.error.PostconditionFailedException;
import io.github.libzeal.zeal.assertion.error.PreconditionFailedException;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;
import org.jspecify.annotations.Nullable;

/**
 * Basic assertions that verify expressions.
 *
 * @author Justin Albano
 */
public final class Assertions {

    private Assertions() {
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
     * @see Requirement#require(UnaryExpression)
     */
    public static <T> T require(final UnaryExpression<T> expression) {
        return Requirement.create().require(expression);
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
     * @see Requirement#require(UnaryExpression, String)
     */
    public static <T> T require(
            final UnaryExpression<T> expression,
            final @Nullable String message
    ) {
        return Requirement.create().require(expression, message);
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
     * @see Requirement#require(Expression)
     */
    public static void require(final Expression expression) {
        Requirement.create().require(expression);
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
     * @see Requirement#require(Expression, String)
     */
    public static void require(
            final Expression expression,
            final @Nullable String message
    ) {
        Requirement.create().require(expression, message);
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
     * @see Confirmation#confirm(UnaryExpression)
     */
    public static <T> T confirm(final UnaryExpression<T> expression) {
        return Confirmation.create().confirm(expression);
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
     * @see Confirmation#confirm(UnaryExpression, String)
     */
    public static <T> T confirm(
            final UnaryExpression<T> expression,
            final @Nullable String message
    ) {
        return Confirmation.create().confirm(expression, message);
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
     * @see Confirmation#confirm(Expression)
     */
    public static void confirm(final Expression expression) {
        Confirmation.create().confirm(expression);
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
     * @see Confirmation#confirm(Expression, String)
     */
    public static void confirm(
            final Expression expression,
            final @Nullable String message
    ) {
        Confirmation.create().confirm(expression, message);
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
     * @see Assurance#ensure(UnaryExpression)
     */
    public static <T> T ensure(final UnaryExpression<T> expression) {
        return Assurance.create().ensure(expression);
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
     * @see Assurance#ensure(UnaryExpression, String)
     */
    public static <T> T ensure(
            final UnaryExpression<T> expression,
            final @Nullable String message
    ) {
        return Assurance.create().ensure(expression, message);
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
     * @see Assurance#ensure(Expression)
     */
    public static void ensure(final Expression expression) {
        Assurance.create().ensure(expression);
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
     * @see Assurance#ensure(Expression, String)
     */
    public static void ensure(
            final Expression expression,
            final @Nullable String message
    ) {
        Assurance.create().ensure(expression, message);
    }
}
