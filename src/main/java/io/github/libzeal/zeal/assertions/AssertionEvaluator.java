package io.github.libzeal.zeal.assertions;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.format.EvaluationFormatter;
import io.github.libzeal.zeal.values.UnaryExpression;

class AssertionEvaluator<N extends Throwable, F extends Throwable> {

    interface Messages {
        String NULL_EXPRESSION = "Cannot evaluate null expression";
        String NULL_EVALUATION = "Cannot process null evaluation";
        String NULL_RESULT = "Cannot process evaluation with null result";
    }

    private final EvaluationFormatter formatter;
    private final Evaluator evaluator;
    private final ExceptionSupplier<N> onNullExceptionFunc;
    private final ExceptionSupplier<F> onFailExceptionFunc;

    AssertionEvaluator(final EvaluationFormatter formatter,
                       final Evaluator evaluator,
                       final ExceptionSupplier<N> onNullExceptionFunc,
                       final ExceptionSupplier<F> onFailExceptionFunc) {
        this.formatter = formatter;
        this.evaluator = evaluator;
        this.onNullExceptionFunc = onNullExceptionFunc;
        this.onFailExceptionFunc = onFailExceptionFunc;
    }

    public <T> T evaluate(final UnaryExpression<T> expression, final String message) throws N, F {

        if (expression == null) {
            throw new NullPointerException(Messages.NULL_EXPRESSION);
        }

        final Evaluation evaluation = evaluator.evaluate(expression.expression());

        if (evaluation == null) {
            throw new NullPointerException(Messages.NULL_EVALUATION);
        }

        final Result result = evaluation.result();

        if (result == null) {
            throw new NullPointerException(Messages.NULL_RESULT);
        }

        final T subject = expression.value();

        if (result.isFalse()) {

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

    @FunctionalInterface
    interface ExceptionSupplier<T extends Throwable> {

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
