package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.format.EvaluationFormatter;
import io.github.libzeal.zeal.expression.lang.evaluation.format.SimpleEvaluationFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static io.github.libzeal.zeal.assertion.AssertionExpressionEvaluator.Messages.*;
import static io.github.libzeal.zeal.assertion.AssertionTestCases.*;
import static io.github.libzeal.zeal.assertion.test.Expressions.*;

@SuppressWarnings("java:S2699")
class AssertionExpressionEvaluatorTest {

    private AssertionExpressionEvaluator evaluator;
    private AssertionTestCases helper;

    @BeforeEach
    void setUp() {

        final EvaluationFormatter formatter = new SimpleEvaluationFormatter();

        evaluator = new AssertionExpressionEvaluator(formatter, TestRuntimeException::new, TestRuntimeException::new);
        helper = new AssertionTestCases(formatter);
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
    void whenEnsureWithMessage_thenSubjectReturned(final SubjectReturnedDataSet testData) {
        helper.whenCallWithMessage_thenSubjectReturned(
            testData,
            evaluator::evaluate
        );
    }

    static final class ExceptionThrownArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) throws Exception {
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
                        "Null result",
                        expressionWithNullResult(),
                        NullPointerException.class,
                        NULL_RESULT
                    )
                ),
                Arguments.of(
                    new ExceptionTestCaseData(
                        "Failed evaluation and null subject",
                        expression(Result.FAILED, null),
                        TestRuntimeException.class
                    )
                ),
                Arguments.of(
                    new ExceptionTestCaseData(
                        "Failed evaluation and non-null subject",
                        expression(Result.FAILED, new Object()),
                        TestRuntimeException.class
                    )
                )
            );
        }
    }
}
