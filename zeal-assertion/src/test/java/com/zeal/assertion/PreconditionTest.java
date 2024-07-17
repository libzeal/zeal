package com.zeal.assertion;

import com.zeal.assertion.api.Precondition;
import com.zeal.assertion.exception.PreconditionFailedException;
import com.zeal.expression.BooleanExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
            public boolean hasFailingNotNullCheck() {
                return hashFailingNotNullCheck;
            }
        };
    }

    @Test
    void givenFalseExpressionWithoutNotNullCheck_whenRequire_thenExceptionThrown() {
        assertThrows(
                PreconditionFailedException.class,
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
                PreconditionFailedException.class,
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
                () -> Precondition.require(condition, message)
        );

        assertMessageIsCorrect(message, e, condition);
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
