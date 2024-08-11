package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.PreconditionFailedException;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
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
    void whenRequireWithMessage_thenSubjectReturned(final SubjectReturnedDataSet testData) {
        helper.whenCallWithMessage_thenSubjectReturned(
            testData,
            requirement::require
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
                        NullPointerException.class
                    )
                ),
                Arguments.of(
                    new ExceptionTestCaseData(
                        "Failed evaluation and non-null subject",
                        expression(Result.FAILED, new Object()),
                        PreconditionFailedException.class
                    )
                )
            );
        }
    }
}
