package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.PostconditionFailedException;
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
class AssuranceTest {

    private Assurance assurance;
    private AssertionTestCases helper;

    @BeforeEach
    void setUp() {
        assurance = Assurance.create();
        helper = new AssertionTestCases(new SimpleEvaluationFormatter());
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExceptionThrownArgumentsProvider.class)
    void whenEnsure(final ExceptionTestCaseData testData) {
        helper.whenCall_thenExceptionThrown(
                testData,
                assurance::ensure,
                Assurance.DEFAULT_MESSAGE
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExceptionThrownArgumentsProvider.class)
    void whenEnsureWithMessage(final ExceptionTestCaseData testData) {
        helper.whenCallWithMessage_thenExceptionThrown(
                testData,
                assurance::ensure
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExceptionThrownArgumentsProvider.class)
    void whenEnsureWithMissingMessage(final ExceptionTestCaseData testData) {
        helper.whenCallWithMissingMessage_thenExceptionThrown(
                testData,
                assurance::ensure
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(SubjectReturnedArgumentsProvider.class)
    void whenEnsure_thenSubjectReturned(final SubjectReturnedDataSet testData) {
        helper.whenCall_thenSubjectReturned(
                testData,
                assurance::ensure
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(SubjectReturnedArgumentsProvider.class)
    void whenEnsureWithMessage_thenSubjectReturned(final SubjectReturnedDataSet testData) throws Throwable {
        helper.whenCallWithMessage_thenSubjectReturned(
                testData,
                assurance::ensure
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExpressionFailureArgumentsProvider.class)
    void whenEnsureExpression(final ExpressionExceptionTestCaseData testData) {
        helper.whenCallExpression_thenExceptionThrown(
                testData,
                assurance::ensure,
                Assurance.DEFAULT_MESSAGE
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExpressionFailureArgumentsProvider.class)
    void whenEnsureExpressionWithMessage(final ExpressionExceptionTestCaseData testData) {
        helper.whenCallExpressionWithMessage_thenExceptionThrown(
                testData,
                assurance::ensure
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExpressionFailureArgumentsProvider.class)
    void whenEnsureExpressionWithMissingMessage(final ExpressionExceptionTestCaseData testData) {
        helper.whenCallExpressionWithMissingMessage_thenExceptionThrown(
                testData,
                assurance::ensure
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(PassingExpressionArgumentsProvider.class)
    void whenEnsureExpression_thenNoExceptionThrown(final PassingExpressionTestCaseData testData) {
        helper.whenCallExpression_thenNoExceptionThrown(
                testData,
                assurance::ensure
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(PassingExpressionArgumentsProvider.class)
    void whenEnsureExpressionWithMessage_thenNoExceptionThrown(final PassingExpressionTestCaseData testData) {
        helper.whenCallExpressionWithMessage_thenNoExceptionThrown(
                testData,
                assurance::ensure
        );
    }

    static final class ExpressionFailureArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
            return expressionFailureCases(PostconditionFailedException.class, PostconditionFailedException.class);
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
                                    PostconditionFailedException.class
                            )
                    ),
                    Arguments.of(
                            new ExceptionTestCaseData(
                                    "Failed evaluation and non-null subject",
                                    expression(false, new Object()),
                                    PostconditionFailedException.class
                            )
                    )
            );
        }
    }
}
