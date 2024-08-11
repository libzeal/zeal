package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.AssertionFailedException;
import io.github.libzeal.zeal.assertion.test.CommonAssertionTestHelper;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static io.github.libzeal.zeal.assertion.AssertionExpressionEvaluator.Messages.*;
import static io.github.libzeal.zeal.assertion.test.CommonAssertionTestHelper.*;
import static io.github.libzeal.zeal.assertion.test.Expressions.*;

@SuppressWarnings("java:S2699")
class ConfirmationTest {

    private Confirmation confirmation;

    @BeforeEach
    void setUp() {
        confirmation = Confirmation.create();
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExceptionThrownArgumentsProvider.class)
    void whenConfirm(final CommonAssertionTestHelper.ExceptionTestCaseData testData) {
        whenCall_thenExceptionThrown(
            testData,
            confirmation::confirm,
            Confirmation.DEFAULT_MESSAGE
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExceptionThrownArgumentsProvider.class)
    void whenConfirmWithMessage(final CommonAssertionTestHelper.ExceptionTestCaseData testData) {
        whenCallWithMessage_thenExceptionThrown(
            testData,
            confirmation::confirm
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ExceptionThrownArgumentsProvider.class)
    void whenConfirmWithMissingMessage(final CommonAssertionTestHelper.ExceptionTestCaseData testData) {
        whenCallWithMissingMessage_thenExceptionThrown(
            testData,
            confirmation::confirm
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(SubjectReturnedArgumentsProvider.class)
    void whenConfirm_thenSubjectReturned(final SubjectReturnedDataSet testData) {
        whenCall_thenSubjectReturned(
            testData,
            confirmation::confirm
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(SubjectReturnedArgumentsProvider.class)
    void whenConfirmWithMessage_thenSubjectReturned(final SubjectReturnedDataSet testData) {
        whenCallWithMessage_thenSubjectReturned(
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
                        expression(Result.FAILED, null),
                        AssertionFailedException.class
                    )
                ),
                Arguments.of(
                    new ExceptionTestCaseData(
                        "Failed evaluation and non-null subject",
                        expression(Result.FAILED, new Object()),
                        AssertionFailedException.class
                    )
                )
            );
        }
    }
}
