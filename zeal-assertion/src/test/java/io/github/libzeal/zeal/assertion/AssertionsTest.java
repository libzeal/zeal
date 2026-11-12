package io.github.libzeal.zeal.assertion;

import io.github.libzeal.zeal.logic.format.SimpleEvaluationFormatter;
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
    // require (expression)
    // ------------------------------------------------------------------------

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(RequirementTest.ExpressionFailureArgumentsProvider.class)
    void whenRequireExpression(final ExpressionExceptionTestCaseData testData) {
        helper.whenCallExpression_thenExceptionThrown(
                testData,
                Assertions::require,
                Requirement.DEFAULT_MESSAGE
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(RequirementTest.ExpressionFailureArgumentsProvider.class)
    void whenRequireExpressionWithMessage(final ExpressionExceptionTestCaseData testData) {
        helper.whenCallExpressionWithMessage_thenExceptionThrown(
                testData,
                Assertions::require
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(RequirementTest.ExpressionFailureArgumentsProvider.class)
    void whenRequireExpressionWithMissingMessage(final ExpressionExceptionTestCaseData testData) {
        helper.whenCallExpressionWithMissingMessage_thenExceptionThrown(
                testData,
                Assertions::require
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(PassingExpressionArgumentsProvider.class)
    void whenRequireExpression_thenNoExceptionThrown(final PassingExpressionTestCaseData testData) {
        helper.whenCallExpression_thenNoExceptionThrown(
                testData,
                Assertions::require
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(PassingExpressionArgumentsProvider.class)
    void whenRequireExpressionWithMessage_thenNoExceptionThrown(final PassingExpressionTestCaseData testData) {
        helper.whenCallExpressionWithMessage_thenNoExceptionThrown(
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
    // confirm (expression)
    // ------------------------------------------------------------------------

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ConfirmationTest.ExpressionFailureArgumentsProvider.class)
    void whenConfirmExpression(final ExpressionExceptionTestCaseData testData) {
        helper.whenCallExpression_thenExceptionThrown(
                testData,
                Assertions::confirm,
                Confirmation.DEFAULT_MESSAGE
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ConfirmationTest.ExpressionFailureArgumentsProvider.class)
    void whenConfirmExpressionWithMessage(final ExpressionExceptionTestCaseData testData) {
        helper.whenCallExpressionWithMessage_thenExceptionThrown(
                testData,
                Assertions::confirm
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(ConfirmationTest.ExpressionFailureArgumentsProvider.class)
    void whenConfirmExpressionWithMissingMessage(final ExpressionExceptionTestCaseData testData) {
        helper.whenCallExpressionWithMissingMessage_thenExceptionThrown(
                testData,
                Assertions::confirm
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(PassingExpressionArgumentsProvider.class)
    void whenConfirmExpression_thenNoExceptionThrown(final PassingExpressionTestCaseData testData) {
        helper.whenCallExpression_thenNoExceptionThrown(
                testData,
                Assertions::confirm
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(PassingExpressionArgumentsProvider.class)
    void whenConfirmExpressionWithMessage_thenNoExceptionThrown(final PassingExpressionTestCaseData testData) {
        helper.whenCallExpressionWithMessage_thenNoExceptionThrown(
                testData,
                Assertions::confirm
        );
    }

    // ------------------------------------------------------------------------
    // ensure (expression)
    // ------------------------------------------------------------------------

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(AssuranceTest.ExpressionFailureArgumentsProvider.class)
    void whenEnsureExpression(final ExpressionExceptionTestCaseData testData) {
        helper.whenCallExpression_thenExceptionThrown(
                testData,
                Assertions::ensure,
                Assurance.DEFAULT_MESSAGE
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(AssuranceTest.ExpressionFailureArgumentsProvider.class)
    void whenEnsureExpressionWithMessage(final ExpressionExceptionTestCaseData testData) {
        helper.whenCallExpressionWithMessage_thenExceptionThrown(
                testData,
                Assertions::ensure
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(AssuranceTest.ExpressionFailureArgumentsProvider.class)
    void whenEnsureExpressionWithMissingMessage(final ExpressionExceptionTestCaseData testData) {
        helper.whenCallExpressionWithMissingMessage_thenExceptionThrown(
                testData,
                Assertions::ensure
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(PassingExpressionArgumentsProvider.class)
    void whenEnsureExpression_thenNoExceptionThrown(final PassingExpressionTestCaseData testData) {
        helper.whenCallExpression_thenNoExceptionThrown(
                testData,
                Assertions::ensure
        );
    }

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(PassingExpressionArgumentsProvider.class)
    void whenEnsureExpressionWithMessage_thenNoExceptionThrown(final PassingExpressionTestCaseData testData) {
        helper.whenCallExpressionWithMessage_thenNoExceptionThrown(
                testData,
                Assertions::ensure
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
