package com.zeal.assertion;

import com.zeal.assertion.api.Precondition;
import com.zeal.assertion.exception.PreconditionIllegalArgumentException;
import com.zeal.expression.BooleanExpression;
import com.zeal.expression.Explanation;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.zeal.expression.api.Evaluators.that;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PreconditionTest {

    @Test
    void givenNullExpression_whenRequire_thenExceptionThrown() {
        assertThrows(
                NullPointerException.class,
                () -> Precondition.require(null)
        );
    }

    @Test
    void givenFalseExpressionWithNotNullCheck_whenRequire_thenExceptionThrown() {
        assertThrows(
                NullPointerException.class,
                () -> Precondition.require(falseExpressionWithNotNullCheck())
        );
    }

    private static BooleanExpression falseExpressionWithNotNullCheck() {
        return expression(false, true);
    }

    private static BooleanExpression expression(boolean isTrue, boolean hashFailingNotNullCheck) {
        return new BooleanExpression() {

            @Override
            public boolean isTrue() {
                return isTrue;
            }

            @Override
            public Optional<Explanation> failureExplanation() {

                Explanation explanation = mock(Explanation.class);

                when(explanation.checksForNotNull()).thenReturn(hashFailingNotNullCheck);

                return Optional.of(explanation);
            }
        };
    }

    @Test
    void givenFalseExpressionWithoutNotNullCheck_whenRequire_thenExceptionThrown() {
        assertThrows(
                PreconditionIllegalArgumentException.class,
                () -> Precondition.require(falseExpressionWithoutNotNullCheck())
        );
    }

    private static BooleanExpression falseExpressionWithoutNotNullCheck() {
        return expression(false, false);
    }

    @Test
    void givenTrueCondition_whenRequire_thenNoExceptionThrown() {
        Precondition.require(() -> true);
    }

    @Test
    void givenNullExpression_whenRequireWithMessage_thenExceptionThrown() {

        String message = "foo";

        Exception e = assertThrows(
                NullPointerException.class,
                () -> Precondition.require(null, message)
        );

        assertMessageIsCorrect(message, e, null);
    }

    private static void assertMessageIsCorrect(String message, Exception e, BooleanExpression condition) {

        String exceptionMessage = e.getMessage();

        assertFalse(exceptionMessage.isEmpty());

        if (message != null && !message.trim().isEmpty() && condition != null) {
            assertTrue(exceptionMessage.endsWith(message));
        }
    }

    @Test
    void givenFalseExpressionWithNotNullCheck_whenRequireWithMessage_thenExceptionThrown() {

        String message = "foo";
        BooleanExpression condition = falseExpressionWithNotNullCheck();

        Exception e = assertThrows(
                NullPointerException.class,
                () -> Precondition.require(condition, message)
        );

        assertMessageIsCorrect(message, e, condition);
    }

    @Test
    void givenFalseExpressionWithoutNotNullCheck_whenRequireWithMessage_thenExceptionThrown() {

        String message = "foo";
        BooleanExpression condition = falseExpressionWithoutNotNullCheck();

        Exception e = assertThrows(
                PreconditionIllegalArgumentException.class,
                () -> Precondition.require(condition, message)
        );

        assertMessageIsCorrect(message, e, condition);
    }

    @Test
    void givenNullExpressionAndMessage_whenRequireWithMessage_thenExceptionThrown() {

        Exception e = assertThrows(
                NullPointerException.class,
                () -> Precondition.require(null, null)
        );

        assertMessageIsCorrect(null, e, null);
    }

    @Test
    void givenNullExpressionAndEmptyMessage_whenRequireWithMessage_thenExceptionThrown() {

        String message = "";
        Exception e = assertThrows(
                NullPointerException.class,
                () -> Precondition.require(null, message)
        );

        assertMessageIsCorrect(message, e, null);
    }

    @Test
    void givenNullExpressionAndBlankMessage_whenRequireWithMessage_thenExceptionThrown() {

        String message = " ";
        Exception e = assertThrows(
                NullPointerException.class,
                () -> Precondition.require(null, message)
        );

        assertMessageIsCorrect(message, e, null);
    }

    @Test
    void givenFalseExpressionWithNotNullCheckAndNullMessage_whenRequireWithMessage_thenExceptionThrown() {

        BooleanExpression condition = falseExpressionWithNotNullCheck();

        Exception e = assertThrows(
                NullPointerException.class,
                () -> Precondition.require(condition, null)
        );

        assertMessageIsCorrect(null, e, condition);
    }

    @Test
    void givenFalseExpressionWithNotNullCheckAndEmptyMessage_whenRequireWithMessage_thenExceptionThrown() {

        String message = "";
        BooleanExpression condition = falseExpressionWithNotNullCheck();

        Exception e = assertThrows(
                NullPointerException.class,
                () -> Precondition.require(condition, message)
        );

        assertMessageIsCorrect(message, e, condition);
    }

    @Test
    void givenFalseExpressionWithNotNullCheckAndBlankMessage_whenRequireWithMessage_thenExceptionThrown() {

        String message = " ";
        BooleanExpression condition = falseExpressionWithNotNullCheck();

        Exception e = assertThrows(
                NullPointerException.class,
                () -> Precondition.require(that((Object) null).isNotNull(), message)
        );

        assertMessageIsCorrect(message, e, condition);

        System.out.println(e);
    }

    @Test
    void givenTrueCondition_whenRequireWithMessage_thenNoExceptionThrown() {
        Precondition.require(() -> true, "foo");
    }

    @Test
    void givenTrueConditionWithNullMessage_whenRequireWithMessage_thenNoExceptionThrown() {
        Precondition.require(() -> true, null);
    }

    @Test
    void givenTrueConditionWithEmptyMessage_whenRequireWithMessage_thenNoExceptionThrown() {
        Precondition.require(() -> true, "");
    }

    @Test
    void givenTrueConditionWithBlank_whenRequireWithMessage_thenNoExceptionThrown() {
        Precondition.require(() -> true, " ");
    }
}
