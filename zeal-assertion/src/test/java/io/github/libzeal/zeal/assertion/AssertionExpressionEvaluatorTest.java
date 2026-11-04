package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.format.EvaluationFormatter;
import io.github.libzeal.zeal.logic.format.SimpleEvaluationFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static io.github.libzeal.zeal.assertion.AssertionExpressionEvaluator.Messages.NULL_EVALUATION;
import static io.github.libzeal.zeal.assertion.AssertionExpressionEvaluator.Messages.NULL_EXPRESSION;
import static io.github.libzeal.zeal.assertion.AssertionTestCases.*;
import static io.github.libzeal.zeal.assertion.test.Expressions.expression;
import static io.github.libzeal.zeal.assertion.test.Expressions.expressionWithNullEvaluation;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SuppressWarnings("java:S2699")
class AssertionExpressionEvaluatorTest {

    private AssertionExpressionEvaluator<Throwable, Throwable> evaluator;
    private AssertionExpressionEvaluator<NullSubjectException, FailedException> distinctExceptionsEvaluator;
    private AssertionTestCases helper;

    @BeforeEach
    void setUp() {

        final EvaluationFormatter formatter = new SimpleEvaluationFormatter();

        evaluator = new AssertionExpressionEvaluator<>(formatter, TestRuntimeException::new, TestRuntimeException::new);
        distinctExceptionsEvaluator = new AssertionExpressionEvaluator<>(formatter, NullSubjectException::new, FailedException::new);
        helper = new AssertionTestCases(formatter);
    }

    private static final class NullSubjectException extends RuntimeException {

        public NullSubjectException(final String message) {
            super(message);
        }
    }

    private static final class FailedException extends RuntimeException {

        public FailedException(final String message) {
            super(message);
        }
    }

    private static final class TestRuntimeException extends RuntimeException {

        public TestRuntimeException(final String message) {
            super(message);
        }
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExceptionThrownArgumentsProvider.class)
    void whenEnsureWithMessage(final ExceptionTestCaseData testData) {
        helper.whenCallWithMessage_thenExceptionThrown(
                testData,
                evaluator::evaluate
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExceptionThrownArgumentsProvider.class)
    void whenEnsureWithMissingMessage(final ExceptionTestCaseData testData) {
        helper.whenCallWithMissingMessage_thenExceptionThrown(
                testData,
                evaluator::evaluate
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(SubjectReturnedArgumentsProvider.class)
    void whenEnsureWithMessage_thenSubjectReturned(final SubjectReturnedDataSet testData) throws Throwable {
        helper.whenCallWithMessage_thenSubjectReturned(
                testData,
                evaluator::evaluate
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExpressionFailureArgumentsProvider.class)
    void whenEvaluateExpressionWithMessage(final ExpressionExceptionTestCaseData testData) {
        helper.whenCallExpressionWithMessage_thenExceptionThrown(
                testData,
                distinctExceptionsEvaluator::evaluate
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExpressionFailureArgumentsProvider.class)
    void whenEvaluateExpressionWithMissingMessage(final ExpressionExceptionTestCaseData testData) {
        helper.whenCallExpressionWithMissingMessage_thenExceptionThrown(
                testData,
                distinctExceptionsEvaluator::evaluate
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(PassingExpressionArgumentsProvider.class)
    void whenEvaluateExpressionWithMessage_thenNoExceptionThrown(final PassingExpressionTestCaseData testData) {
        helper.whenCallExpressionWithMessage_thenNoExceptionThrown(
                testData,
                distinctExceptionsEvaluator::evaluate
        );
    }

    @Test
    void givenUnaryExpressionDeclaredAsExpression_whenEvaluate_thenEvaluatedOnce() {

        final Expression expression = expression(true, new Object());

        distinctExceptionsEvaluator.evaluate(expression, "foo");

        verify(expression, times(1)).evaluate();
    }

    static final class ExpressionFailureArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
            return expressionFailureCases(NullSubjectException.class, FailedException.class);
        }
    }

    static final class ExceptionThrownArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of(
                            new ExceptionTestCaseData(
                                    "Null expression",
                                    null,
                                    NullPointerException.class,
                                    NULL_EXPRESSION
                            )
                    ),
                    Arguments.of(
                            new ExceptionTestCaseData(
                                    "Null evaluation",
                                    expressionWithNullEvaluation(),
                                    NullPointerException.class,
                                    NULL_EVALUATION
                            )
                    ),
                    Arguments.of(
                            new ExceptionTestCaseData(
                                    "Failed evaluation and null subject",
                                    expression(false, null),
                                    TestRuntimeException.class
                            )
                    ),
                    Arguments.of(
                            new ExceptionTestCaseData(
                                    "Failed evaluation and non-null subject",
                                    expression(false, new Object()),
                                    TestRuntimeException.class
                            )
                    )
            );
        }
    }
}
