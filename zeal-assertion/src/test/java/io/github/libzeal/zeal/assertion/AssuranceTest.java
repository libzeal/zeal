package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.assertion.error.PostconditionFailedException;
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
    void whenEnsureWithMessage_thenSubjectReturned(final SubjectReturnedDataSet testData) {
        helper.whenCallWithMessage_thenSubjectReturned(
            testData,
            assurance::ensure
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
                        PostconditionFailedException.class
                    )
                ),
                Arguments.of(
                    new ExceptionTestCaseData(
                        "Failed evaluation and non-null subject",
                        expression(Result.FAILED, new Object()),
                        PostconditionFailedException.class
                    )
                )
            );
        }
    }
}
