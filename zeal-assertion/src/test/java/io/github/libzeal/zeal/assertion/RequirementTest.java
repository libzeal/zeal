package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.PreconditionFailedException;
import io.github.libzeal.zeal.logic.format.SimpleEvaluationFormatter;
import org.junit.jupiter.api.BeforeEach;
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

@SuppressWarnings("java:S2699")
class RequirementTest {

    private Requirement requirement;
    private AssertionTestCases helper;

    @BeforeEach
    void setUp() {
        requirement = Requirement.create();
        helper = new AssertionTestCases(new SimpleEvaluationFormatter());
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(RequirementTest.ExceptionThrownArgumentsProvider.class)
    void whenRequire(final AssertionTestCases.ExceptionTestCaseData testData) {
        helper.whenCall_thenExceptionThrown(
                testData,
                requirement::require,
                Requirement.DEFAULT_MESSAGE
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(RequirementTest.ExceptionThrownArgumentsProvider.class)
    void whenRequireWithMessage(final AssertionTestCases.ExceptionTestCaseData testData) {
        helper.whenCallWithMessage_thenExceptionThrown(
                testData,
                requirement::require
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(RequirementTest.ExceptionThrownArgumentsProvider.class)
    void whenRequireWithMissingMessage(final AssertionTestCases.ExceptionTestCaseData testData) {
        helper.whenCallWithMissingMessage_thenExceptionThrown(
                testData,
                requirement::require
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(SubjectReturnedArgumentsProvider.class)
    void whenRequire_thenSubjectReturned(final SubjectReturnedDataSet testData) {
        helper.whenCall_thenSubjectReturned(
                testData,
                requirement::require
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(SubjectReturnedArgumentsProvider.class)
    void whenRequireWithMessage_thenSubjectReturned(final SubjectReturnedDataSet testData) throws Throwable {
        helper.whenCallWithMessage_thenSubjectReturned(
                testData,
                requirement::require
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExpressionFailureArgumentsProvider.class)
    void whenRequireExpression(final ExpressionExceptionTestCaseData testData) {
        helper.whenCallExpression_thenExceptionThrown(
                testData,
                requirement::require,
                Requirement.DEFAULT_MESSAGE
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExpressionFailureArgumentsProvider.class)
    void whenRequireExpressionWithMessage(final ExpressionExceptionTestCaseData testData) {
        helper.whenCallExpressionWithMessage_thenExceptionThrown(
                testData,
                requirement::require
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExpressionFailureArgumentsProvider.class)
    void whenRequireExpressionWithMissingMessage(final ExpressionExceptionTestCaseData testData) {
        helper.whenCallExpressionWithMissingMessage_thenExceptionThrown(
                testData,
                requirement::require
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(PassingExpressionArgumentsProvider.class)
    void whenRequireExpression_thenNoExceptionThrown(final PassingExpressionTestCaseData testData) {
        helper.whenCallExpression_thenNoExceptionThrown(
                testData,
                requirement::require
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(PassingExpressionArgumentsProvider.class)
    void whenRequireExpressionWithMessage_thenNoExceptionThrown(final PassingExpressionTestCaseData testData) {
        helper.whenCallExpressionWithMessage_thenNoExceptionThrown(
                testData,
                requirement::require
        );
    }

    static final class ExpressionFailureArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
            return expressionFailureCases(NullPointerException.class, PreconditionFailedException.class);
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
                                    NullPointerException.class
                            )
                    ),
                    Arguments.of(
                            new ExceptionTestCaseData(
                                    "Failed evaluation and non-null subject",
                                    expression(false, new Object()),
                                    PreconditionFailedException.class
                            )
                    )
            );
        }
    }
}
