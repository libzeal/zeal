package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.AssertionFailedError;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.format.simple.SimpleFormatter;
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
class ConfirmationTest {

    private Confirmation confirmation;
    private AssertionTestCases helper;

    @BeforeEach
    void setUp() {
        confirmation = Confirmation.create();
        helper = new AssertionTestCases(new SimpleFormatter());
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExceptionThrownArgumentsProvider.class)
    void whenConfirm(final AssertionTestCases.ExceptionTestCaseData testData) {
        helper.whenCall_thenExceptionThrown(
            testData,
            confirmation::confirm,
            Confirmation.DEFAULT_MESSAGE
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExceptionThrownArgumentsProvider.class)
    void whenConfirmWithMessage(final AssertionTestCases.ExceptionTestCaseData testData) {
        helper.whenCallWithMessage_thenExceptionThrown(
            testData,
            confirmation::confirm
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExceptionThrownArgumentsProvider.class)
    void whenConfirmWithMissingMessage(final AssertionTestCases.ExceptionTestCaseData testData) {
        helper.whenCallWithMissingMessage_thenExceptionThrown(
            testData,
            confirmation::confirm
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(SubjectReturnedArgumentsProvider.class)
    void whenConfirm_thenSubjectReturned(final SubjectReturnedDataSet testData) {
        helper.whenCall_thenSubjectReturned(
            testData,
            confirmation::confirm
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(SubjectReturnedArgumentsProvider.class)
    void whenConfirmWithMessage_thenSubjectReturned(final SubjectReturnedDataSet testData) throws Throwable {
        helper.whenCallWithMessage_thenSubjectReturned(
            testData,
            confirmation::confirm
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
                        expression(Result.FALSE, null),
                        AssertionFailedError.class
                    )
                ),
                Arguments.of(
                    new ExceptionTestCaseData(
                        "Failed evaluation and non-null subject",
                        expression(Result.FALSE, new Object()),
                        AssertionFailedError.class
                    )
                )
            );
        }
    }
}
