package com.zeal.assertion.api;

import com.zeal.assertion.exception.PreconditionFailedException;
import com.zeal.expression.BooleanExpression;

import java.util.Objects;

public class Precondition {

    private static final String CANNOT_EVALUATE_NULL_MESSAGE = "Cannot evaluate null expression";
    private static final String PRECONDITION_FAILED_MESSAGE = "Precondition failed";
    private static final String PRECONDITION_FAILED_FOR_NULL_MESSAGE = "Precondition Failed. Expected a non-null " +
            "value but found a null value";

    private Precondition() {}

    /**
     * Requires that a condition be true or throws an exception otherwise. Two exception can be thrown:
     * <ol>
     *     <li>{@link NullPointerException}: The supplied condition includes an expression that checks for a
     *     {@code null} value. This includes any chained or nested checks (examples included below). Additionally, a
     *     {@link NullPointerException} is thrown if the supplied condition is {@code null}.
     *     </li>
     *     <li>{@link IllegalArgumentException}: The supplied condition fails for any other reason.</li>
     * </ol>
     * <p>
     * For example, any of the following would result in a {@code NullPointerException} is {@code value} is
     * {@code null}:
     *
     * <pre><code>
     *     require(that(value).isNotNull());
     *     require(that(value).isLessThan(7).isNotNull());
     *     require(that(value).isNotNull().isGreaterThan(4));
     * </code></pre>
     * <p>
     * An {@link IllegalArgumentException} will be thrown if any supplied condition without a check for "not null" is
     * evaluated to {@code false}. For example:
     *
     * <pre><code>
     *     require(that(value).isLessThan(7));
     *     require(that(value).isGreaterThan(4).isEven());
     * </code></pre>
     * <p>
     * This special behavior for a "not null" check is included to align with the behavior of the JDK code, which
     * throws a {@link NullPointerException} when an argument is passed to a function that expects the argument to be
     * not {@code null}. This is in keeping with the behavior of the {@link Objects#requireNonNull(Object)} method.
     * All of other cases of a failed precondition result in an {@link IllegalArgumentException}.
     *
     * @param condition
     *         The precondition to require.
     *
     * @throws NullPointerException
     *         The supplied condition includes a "not null" check and that check fails or the supplied condition is
     *         {@code null}.
     * @throws IllegalArgumentException
     *         The supplied condition fails for any other reason.
     */
    public static void require(BooleanExpression condition) {

        if (condition == null) {
            throw new NullPointerException(CANNOT_EVALUATE_NULL_MESSAGE);
        }

        if (condition.isFalse()) {

            if (condition.hasFailingNotNullCheck()) {
                throw new NullPointerException(PRECONDITION_FAILED_FOR_NULL_MESSAGE);
            }

            throw new PreconditionFailedException(PRECONDITION_FAILED_MESSAGE);
        }
    }

    /**
     * Requires that a condition be true or throws an exception otherwise. Two exception can be thrown:
     * <ol>
     *     <li>{@link NullPointerException}: The supplied condition includes an expression that checks for a
     *     {@code null} value. This includes any chained or nested checks (examples included below). Additionally, a
     *     {@link NullPointerException} is thrown if the supplied condition is {@code null}.
     *     </li>
     *     <li>{@link IllegalArgumentException}: The supplied condition fails for any other reason.</li>
     * </ol>
     * <p>
     * For example, any of the following would result in a {@code NullPointerException} is {@code value} is
     * {@code null}:
     *
     * <pre><code>
     *     require(that(value).isNotNull());
     *     require(that(value).isLessThan(7).isNotNull());
     *     require(that(value).isNotNull().isGreaterThan(4));
     * </code></pre>
     * <p>
     * An {@link IllegalArgumentException} will be thrown if any supplied condition without a check for "not null" is
     * evaluated to {@code false}. For example:
     *
     * <pre><code>
     *     require(that(value).isLessThan(7));
     *     require(that(value).isGreaterThan(4).isEven());
     * </code></pre>
     * <p>
     * This special behavior for a "not null" check is included to align with the behavior of the JDK code, which
     * throws a {@link NullPointerException} when an argument is passed to a function that expects the argument to be
     * not {@code null}. This is in keeping with the behavior of the {@link Objects#requireNonNull(Object)} method.
     * All of other cases of a failed precondition result in an {@link IllegalArgumentException}.
     *
     * @param condition
     *         The precondition to require.
     * @param message
     *         The message to include in any exceptions thrown if the supplied condition is @{code false}.
     *
     * @throws NullPointerException
     *         The supplied condition includes a "not null" check and that check fails or the supplied condition is
     *         {@code null}.
     * @throws IllegalArgumentException
     *         The supplied condition fails for any other reason.
     */
    public static void require(BooleanExpression condition, String message) {

        if (condition == null) {
            throw new NullPointerException(CANNOT_EVALUATE_NULL_MESSAGE);
        }

        if (condition.isFalse()) {

            if (condition.hasFailingNotNullCheck()) {
                throw new NullPointerException(appendMessage(PRECONDITION_FAILED_FOR_NULL_MESSAGE, message));
            }

            throw new PreconditionFailedException(appendMessage(PRECONDITION_FAILED_MESSAGE, message));
        }
    }

    private static String appendMessage(String prefixMessage, String message) {

        if (message == null || message.trim().isEmpty()) {
            return prefixMessage;
        }
        else {
            return prefixMessage + ": " + message;
        }
    }
}