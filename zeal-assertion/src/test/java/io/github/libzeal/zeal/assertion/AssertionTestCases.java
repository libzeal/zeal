package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.format.EvaluationFormatter;
import io.github.libzeal.zeal.logic.unary.Condition;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static io.github.libzeal.zeal.assertion.AssertionExpressionEvaluator.Messages.NULL_EVALUATION;
import static io.github.libzeal.zeal.assertion.AssertionExpressionEvaluator.Messages.NULL_EXPRESSION;
import static io.github.libzeal.zeal.assertion.test.Expressions.compoundExpression;
import static io.github.libzeal.zeal.assertion.test.Expressions.compoundUnaryExpression;
import static io.github.libzeal.zeal.assertion.test.Expressions.expression;
import static io.github.libzeal.zeal.assertion.test.Expressions.expressionWithNullEvaluation;
import static io.github.libzeal.zeal.assertion.test.Expressions.nonUnaryExpressionWithNullEvaluation;
import static io.github.libzeal.zeal.assertion.test.Expressions.terminalExpression;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssertionTestCases {

    private final EvaluationFormatter formatter;

    public AssertionTestCases(final EvaluationFormatter formatter) {
        this.formatter = formatter;
    }

    public void whenCall_thenExceptionThrown(
            final ExceptionTestCaseData testData,
            final Consumer<UnaryExpression<Object>> action,
            final String defaultMessage
    ) {

        final Throwable caught = assertThrows(
                testData.exception(),
                () -> action.accept(testData.expression())
        );

        final String expectedMessage = testData.message()
                .orElseGet(() -> AssertionExpressionEvaluator.formatMessage(
                                formatter,
                                defaultMessage,
                                testData.evaluation()
                        )
                );

        assertEquals(expectedMessage, caught.getMessage());
    }

    public void whenCallWithMessage_thenExceptionThrown(
            final ExceptionTestCaseData testData,
            final EvaluationWithMessageAction action
    ) {

        final String message = "foo";

        final Throwable caught = assertThrows(
                testData.exception(),
                () -> action.perform(testData.expression(), message)
        );

        final String expectedMessage = testData.message()
                .orElseGet(() -> AssertionExpressionEvaluator.formatMessage(
                                formatter,
                                message,
                                testData.evaluation()
                        )
                );

        assertEquals(expectedMessage, caught.getMessage());
    }

    public void whenCallWithMissingMessage_thenExceptionThrown(
            final ExceptionTestCaseData testData,
            final EvaluationWithMessageAction action
    ) {

        final Throwable caught = assertThrows(
                testData.exception(),
                () -> action.perform(testData.expression(), null)
        );

        final String expectedMessage = testData.message()
                .orElseGet(() -> AssertionExpressionEvaluator.formatMessage(
                                formatter,
                                "",
                                testData.evaluation()
                        )
                );

        assertEquals(expectedMessage, caught.getMessage());
    }

    public void whenCall_thenSubjectReturned(
            final SubjectReturnedDataSet testData,
            final Function<UnaryExpression<Object>, Object> action
    ) {
        final boolean result = testData.result();
        final Object subject = testData.subject();
        final UnaryExpression<Object> expression = expression(result, subject);

        Object returnedSubject = action.apply(expression);

        assertEquals(subject, returnedSubject);
    }

    public void whenCallWithMessage_thenSubjectReturned(
            final SubjectReturnedDataSet testData,
            final EvaluationWithMessageAction action
    ) throws Throwable {
        final String message = "foo";
        final boolean result = testData.result();
        final Object subject = testData.subject();
        final UnaryExpression<Object> expression = expression(result, subject);

        Object returnedSubject = action.perform(expression, message);

        assertEquals(subject, returnedSubject);
    }

    public void whenCallExpression_thenExceptionThrown(
            final ExpressionExceptionTestCaseData testData,
            final Consumer<Expression> action,
            final String defaultMessage
    ) {

        final Throwable caught = assertThrows(
                testData.exception(),
                () -> action.accept(testData.expression())
        );

        assertEquals(testData.expectedMessage(formatter, defaultMessage), caught.getMessage());
    }

    public void whenCallExpressionWithMessage_thenExceptionThrown(
            final ExpressionExceptionTestCaseData testData,
            final ExpressionWithMessageAction action
    ) {

        final String message = "foo";

        final Throwable caught = assertThrows(
                testData.exception(),
                () -> action.perform(testData.expression(), message)
        );

        assertEquals(testData.expectedMessage(formatter, message), caught.getMessage());
    }

    public void whenCallExpressionWithMissingMessage_thenExceptionThrown(
            final ExpressionExceptionTestCaseData testData,
            final ExpressionWithMessageAction action
    ) {

        final Throwable caught = assertThrows(
                testData.exception(),
                () -> action.perform(testData.expression(), null)
        );

        assertEquals(testData.expectedMessage(formatter, ""), caught.getMessage());
    }

    public void whenCallExpression_thenNoExceptionThrown(
            final PassingExpressionTestCaseData testData,
            final Consumer<Expression> action
    ) {
        assertDoesNotThrow(() -> action.accept(testData.expression()));
    }

    public void whenCallExpressionWithMessage_thenNoExceptionThrown(
            final PassingExpressionTestCaseData testData,
            final ExpressionWithMessageAction action
    ) {
        assertDoesNotThrow(() -> action.perform(testData.expression(), "foo"));
    }

    public static Stream<Arguments> expressionFailureCases(
            final Class<? extends Throwable> nullSubjectException,
            final Class<? extends Throwable> failedException
    ) {
        return Stream.of(
                Arguments.of(
                        new ExpressionExceptionTestCaseData(
                                "Null expression",
                                null,
                                NullPointerException.class,
                                NULL_EXPRESSION
                        )
                ),
                Arguments.of(
                        new ExpressionExceptionTestCaseData(
                                "Null evaluation from non-unary expression",
                                nonUnaryExpressionWithNullEvaluation(),
                                NullPointerException.class,
                                NULL_EVALUATION
                        )
                ),
                Arguments.of(
                        new ExpressionExceptionTestCaseData(
                                "Null evaluation from unary expression",
                                expressionWithNullEvaluation(),
                                NullPointerException.class,
                                NULL_EVALUATION
                        )
                ),
                Arguments.of(
                        new ExpressionExceptionTestCaseData(
                                "Failed terminal expression",
                                terminalExpression(false),
                                failedException
                        )
                ),
                Arguments.of(
                        new ExpressionExceptionTestCaseData(
                                "Failed compound expression",
                                compoundExpression(false),
                                failedException
                        )
                ),
                Arguments.of(
                        new ExpressionExceptionTestCaseData(
                                "Failed compound expression with null-subject child",
                                compoundExpression(Condition.<Object>contradiction().bind(null)),
                                failedException
                        )
                ),
                Arguments.of(
                        new ExpressionExceptionTestCaseData(
                                "Failed unary expression and null subject",
                                expression(false, null),
                                nullSubjectException
                        )
                ),
                Arguments.of(
                        new ExpressionExceptionTestCaseData(
                                "Failed unary expression and non-null subject",
                                expression(false, new Object()),
                                failedException
                        )
                ),
                Arguments.of(
                        new ExpressionExceptionTestCaseData(
                                "Failed compound unary expression and null subject",
                                compoundUnaryExpression(false, null),
                                nullSubjectException
                        )
                ),
                Arguments.of(
                        new ExpressionExceptionTestCaseData(
                                "Failed compound unary expression and non-null subject",
                                compoundUnaryExpression(false, new Object()),
                                failedException
                        )
                )
        );
    }

    public static final class PassingExpressionArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of(new PassingExpressionTestCaseData("Passing terminal expression", terminalExpression(true))),
                    Arguments.of(new PassingExpressionTestCaseData("Passing compound expression", compoundExpression(true))),
                    Arguments.of(new PassingExpressionTestCaseData("Passing unary expression and null subject", expression(true, null))),
                    Arguments.of(new PassingExpressionTestCaseData("Passing unary expression and non-null subject", expression(true, new Object()))),
                    Arguments.of(new PassingExpressionTestCaseData("Passing compound unary expression and null subject", compoundUnaryExpression(true, null))),
                    Arguments.of(new PassingExpressionTestCaseData("Passing compound unary expression and non-null subject", compoundUnaryExpression(true, new Object())))
            );
        }
    }

    public static final class SubjectReturnedArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of(new SubjectReturnedDataSet("Passing with null subject", true, null)),
                    Arguments.of(new SubjectReturnedDataSet("Passing with non-null subject", true, new Object()))
            );
        }
    }

    public static class ExceptionTestCaseData {

        private final String name;
        private final UnaryExpression<Object> expression;
        private final Class<? extends Throwable> exception;
        private final String message;

        public ExceptionTestCaseData(
                final String name, final UnaryExpression<Object> expression, final Class<?
                        extends Throwable> exception, String message
        ) {
            this.name = name;
            this.expression = expression;
            this.exception = exception;
            this.message = message;
        }

        public ExceptionTestCaseData(final String name, final UnaryExpression<Object> expression, final Class<? extends Throwable> exception) {
            this(name, expression, exception, null);
        }

        public String name() {
            return name;
        }

        public UnaryExpression<Object> expression() {
            return expression;
        }

        public Class<? extends Throwable> exception() {
            return exception;
        }

        public Optional<String> message() {
            return Optional.ofNullable(message);
        }

        public Evaluation evaluation() {
            return expression.evaluate();
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class SubjectReturnedDataSet {

        private final String name;
        private final boolean result;
        private final Object subject;

        public SubjectReturnedDataSet(final String name, final boolean result, final Object subject) {
            this.name = name;
            this.result = result;
            this.subject = subject;
        }

        public boolean result() {
            return result;
        }

        public Object subject() {
            return subject;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public record ExpressionExceptionTestCaseData(
            String name,
            Expression expression,
            Class<? extends Throwable> exception,
            String message
    ) {

        public ExpressionExceptionTestCaseData(
                final String name, final Expression expression, final Class<? extends Throwable> exception
        ) {
            this(name, expression, exception, null);
        }

        public String expectedMessage(final EvaluationFormatter formatter, final String suppliedMessage) {
            return message != null
                    ? message
                    : AssertionExpressionEvaluator.formatMessage(formatter, suppliedMessage, expression.evaluate());
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public record PassingExpressionTestCaseData(String name, Expression expression) {

        @Override
        public String toString() {
            return name;
        }
    }

    @FunctionalInterface
    public interface ExpressionWithMessageAction {
        void perform(Expression expression, String message) throws Throwable;
    }

    @FunctionalInterface
    public interface EvaluationWithMessageAction {
        Object perform(UnaryExpression<Object> expression, String message) throws Throwable;
    }
}
