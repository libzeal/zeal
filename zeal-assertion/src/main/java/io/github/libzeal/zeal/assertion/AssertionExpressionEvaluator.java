package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.expression.lang.UnaryExpression;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.format.EvaluationFormatter;

import static java.util.Objects.requireNonNull;

/**
 * An evaluator responsible for handling the core logic of asserting an expression, such as when to throw an exception
 * and how to handle error cases.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
final class AssertionExpressionEvaluator<N extends Throwable, F extends Throwable> {

    @SuppressWarnings("java:S1214")
    interface Messages {
        String NULL_EXPRESSION = "Cannot evaluate null expression";
        String NULL_EVALUATION = "Cannot process null evaluation";
        String NULL_RESULT = "Cannot process evaluation with null result";
    }

    private final EvaluationFormatter formatter;
    private final ExceptionSupplier<N> onNullExceptionFunc;
    private final ExceptionSupplier<F> onFailExceptionFunc;

    /**
     * Creates a new evaluator.
     *
     * @param formatter
     *     The formatter to use when creating exception messages.
     * @param onNullExceptionFunc
     *     A function that consumes a message and returns the appropriate exception to throw when an evaluation fails
     *     and the subject of the evaluated expression is {@code null}.
     * @param onFailExceptionFunc
     *     A function that consumes a message and returns the appropriate exception to throw when an evaluation fails
     *     and the subject of the evaluated expression is not {@code null}.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    AssertionExpressionEvaluator(final EvaluationFormatter formatter, final ExceptionSupplier<N> onNullExceptionFunc,
                                 final ExceptionSupplier<F> onFailExceptionFunc) {
        this.formatter = requireNonNull(formatter);
        this.onNullExceptionFunc = requireNonNull(onNullExceptionFunc);
        this.onFailExceptionFunc = requireNonNull(onFailExceptionFunc);
    }

    /**
     * Evaluates the supplied expression and throws the configured exceptions if the evaluation fails.
     *
     * @param expression
     *     The expression to evaluate.
     * @param message
     *     The message to use for the exceptions thrown if the evaluation fails. This message is prepended to the
     *     message generated by the configured formatter. If a {@code null} message is supplied, an empty message is
     *     prepended to the formatted message.
     * @param <T>
     *     The type of the subject.
     *
     * @return The subject of the expression if the evaluation succeeds.
     *
     * @throws NullPointerException
     *     The supplied expression is {@code null}, the evaluation from the supplied expression (when calling
     *     {@link UnaryExpression#evaluate()}) is {@code null}, or the result of the evaluation (when calling
     *     {@link Evaluation#result()} is {@code null}.
     */
    public <T> T evaluate(final UnaryExpression<T> expression, final String message) throws N, F {

        if (expression == null) {
            throw new NullPointerException(Messages.NULL_EXPRESSION);
        }

        final Evaluation evaluation = expression.evaluate();

        if (evaluation == null) {
            throw new NullPointerException(Messages.NULL_EVALUATION);
        }

        final Result result = evaluation.result();

        if (result == null) {
            throw new NullPointerException(Messages.NULL_RESULT);
        }

        final T subject = expression.subject();

        if (result.isFailed()) {

            String formattedMessage = formatMessage(formatter, message, evaluation);

            if (subject == null) {
                throw onNullExceptionFunc.create(formattedMessage);
            }
            else {
                throw onFailExceptionFunc.create(formattedMessage);
            }
        }
        else {
            return subject;
        }
    }

    static String formatMessage(final EvaluationFormatter formatter, final String message,
                                final Evaluation evaluation) {

        final String formattedSuppliedMessage = message == null ? "" : message;

        return formattedSuppliedMessage + "\n\n" + formatter.format(evaluation) + "\nStack trace:";
    }

    /**
     * A function that consumes a message and creates a {@link RuntimeException}.
     *
     * @author Justin Albano
     * @since 0.2.0
     */
    @FunctionalInterface
    public interface ExceptionSupplier<T extends Throwable> {

        /**
         * Creates a new {@link RuntimeException} from the supplied message.
         *
         * @param message
         *     The message to use for the exception.
         *
         * @return The created exception.
         */
        T create(String message);
    }
}
