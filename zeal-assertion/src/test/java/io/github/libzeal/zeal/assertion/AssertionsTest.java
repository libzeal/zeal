package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.expression.lang.evaluation.format.SimpleEvaluationFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static io.github.libzeal.zeal.assertion.AssertionTestCases.*;

@SuppressWarnings("java:S2699")
class AssertionsTest {

    private AssertionTestCases helper;

    @BeforeEach
    void setUp() {
        helper = new AssertionTestCases(new SimpleEvaluationFormatter());
    }

    // ------------------------------------------------------------------------
    // require
    // ------------------------------------------------------------------------

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(RequirementTest.ExceptionThrownArgumentsProvider.class)
    void whenRequire(final AssertionTestCases.ExceptionTestCaseData testData) {
        helper.whenCall_thenExceptionThrown(
            testData,
            Assertions::require,
            Requirement.DEFAULT_MESSAGE
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(RequirementTest.ExceptionThrownArgumentsProvider.class)
    void whenRequireWithMessage(final AssertionTestCases.ExceptionTestCaseData testData) {
        helper.whenCallWithMessage_thenExceptionThrown(
            testData,
            Assertions::require
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(RequirementTest.ExceptionThrownArgumentsProvider.class)
    void whenRequireWithMissingMessage(final AssertionTestCases.ExceptionTestCaseData testData) {
        helper.whenCallWithMissingMessage_thenExceptionThrown(
            testData,
            Assertions::require
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(SubjectReturnedArgumentsProvider.class)
    void whenRequire_thenSubjectReturned(final SubjectReturnedDataSet testData) {
        helper.whenCall_thenSubjectReturned(
            testData,
            Assertions::require
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(SubjectReturnedArgumentsProvider.class)
    void whenRequireWithMessage_thenSubjectReturned(final SubjectReturnedDataSet testData) throws Throwable {
        helper.whenCallWithMessage_thenSubjectReturned(
            testData,
            Assertions::require
        );
    }

    // ------------------------------------------------------------------------
    // confirm
    // ------------------------------------------------------------------------

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ConfirmationTest.ExceptionThrownArgumentsProvider.class)
    void whenConfirm(final AssertionTestCases.ExceptionTestCaseData testData) {
        helper.whenCall_thenExceptionThrown(
            testData,
            Assertions::confirm,
            Confirmation.DEFAULT_MESSAGE
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ConfirmationTest.ExceptionThrownArgumentsProvider.class)
    void whenConfirmWithMessage(final AssertionTestCases.ExceptionTestCaseData testData) {
        helper.whenCallWithMessage_thenExceptionThrown(
            testData,
            Assertions::confirm
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ConfirmationTest.ExceptionThrownArgumentsProvider.class)
    void whenConfirmWithMissingMessage(final AssertionTestCases.ExceptionTestCaseData testData) {
        helper.whenCallWithMissingMessage_thenExceptionThrown(
            testData,
            Assertions::confirm
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(SubjectReturnedArgumentsProvider.class)
    void whenConfirm_thenSubjectReturned(final SubjectReturnedDataSet testData) {
        helper.whenCall_thenSubjectReturned(
            testData,
            Assertions::confirm
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(SubjectReturnedArgumentsProvider.class)
    void whenConfirmWithMessage_thenSubjectReturned(final SubjectReturnedDataSet testData) throws Throwable {
        helper.whenCallWithMessage_thenSubjectReturned(
            testData,
            Assertions::confirm
        );
    }

    // ------------------------------------------------------------------------
    // ensure
    // ------------------------------------------------------------------------

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(AssuranceTest.ExceptionThrownArgumentsProvider.class)
    void whenEnsure_thenExceptionThrown(final ExceptionTestCaseData testData) {
        helper.whenCall_thenExceptionThrown(
            testData,
            Assertions::ensure,
            Assurance.DEFAULT_MESSAGE
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(AssuranceTest.ExceptionThrownArgumentsProvider.class)
    void whenEnsureWithMessage_thenExceptionThrown(final ExceptionTestCaseData testData) {
        helper.whenCallWithMessage_thenExceptionThrown(
            testData,
            Assertions::ensure
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(AssuranceTest.ExceptionThrownArgumentsProvider.class)
    void whenEnsureWithMissingMessage_thenExceptionThrown(final ExceptionTestCaseData testData) {
        helper.whenCallWithMissingMessage_thenExceptionThrown(
            testData,
            Assertions::ensure
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(SubjectReturnedArgumentsProvider.class)
    void whenEnsure_thenSubjectReturned(final SubjectReturnedDataSet testData) {
        helper.whenCall_thenSubjectReturned(
            testData,
            Assertions::ensure
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(SubjectReturnedArgumentsProvider.class)
    void whenEnsureWithMessage_thenSubjectReturned(final SubjectReturnedDataSet testData) throws Throwable {
        helper.whenCallWithMessage_thenSubjectReturned(
            testData,
            Assertions::ensure
        );
    }
}
