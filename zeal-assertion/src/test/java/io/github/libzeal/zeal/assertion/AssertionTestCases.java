package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.expression.lang.UnaryExpression;
import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.format.EvaluationFormatter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static io.github.libzeal.zeal.assertion.test.Expressions.expression;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssertionTestCases {

    private final EvaluationFormatter formatter;

    public AssertionTestCases(final EvaluationFormatter formatter) {
        this.formatter = formatter;
    }

    public void whenCall_thenExceptionThrown(final ExceptionTestCaseData testData,
                                             final Consumer<UnaryExpression<Object>> action,
                                             final String defaultMessage) {

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

    public void whenCallWithMessage_thenExceptionThrown(final ExceptionTestCaseData testData,
                                                               final BiConsumer<UnaryExpression<Object>, String> action) {

        final String message = "foo";

        final Throwable caught = assertThrows(
            testData.exception(),
            () -> action.accept(testData.expression(), message)
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

    public void whenCallWithMissingMessage_thenExceptionThrown(final ExceptionTestCaseData testData,
                                                                final BiConsumer<UnaryExpression<Object>, String> action) {

        final Throwable caught = assertThrows(
            testData.exception(),
            () -> action.accept(testData.expression(), null)
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

    public void whenCall_thenSubjectReturned(final SubjectReturnedDataSet testData,
                                                    final Function<UnaryExpression<Object>, Object> action) {
        final Result result = testData.result();
        final Object subject = testData.subject();
        final UnaryExpression<Object> expression = expression(result, subject);

        Object returnedSubject = action.apply(expression);

        assertEquals(subject, returnedSubject);
    }

    public void whenCallWithMessage_thenSubjectReturned(final SubjectReturnedDataSet testData,
                                                               final BiFunction<UnaryExpression<Object>, String, Object> action) {
        final String message = "foo";
        final Result result = testData.result();
        final Object subject = testData.subject();
        final UnaryExpression<Object> expression = expression(result, subject);

        Object returnedSubject = action.apply(expression, message);

        assertEquals(subject, returnedSubject);
    }

    public static final class SubjectReturnedArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                Arguments.of( new SubjectReturnedDataSet("Passing with null subject", Result.PASSED, null)),
                Arguments.of(new SubjectReturnedDataSet("Passing with non-null subject", Result.PASSED, new Object())),
                Arguments.of(new SubjectReturnedDataSet("Skipped with null subject", Result.SKIPPED, null)),
                Arguments.of(new SubjectReturnedDataSet("Skipped with non-null subject", Result.SKIPPED, new Object()))
            );
        }
    }

    public static class ExceptionTestCaseData {

        private final String name;
        private final UnaryExpression<Object> expression;
        private final Class<? extends Throwable> exception;
        private final String message;

        public ExceptionTestCaseData(final String name, final UnaryExpression<Object> expression, final Class<?
            extends Throwable> exception, String message) {
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
        private final Result result;
        private final Object subject;

        public SubjectReturnedDataSet(final String name, final Result result, final Object subject) {
            this.name = name;
            this.result = result;
            this.subject = subject;
        }

        public Result result() {
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
}
