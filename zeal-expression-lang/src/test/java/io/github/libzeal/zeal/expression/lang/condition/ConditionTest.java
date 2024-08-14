package io.github.libzeal.zeal.expression.lang.condition;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class ConditionTest {

    @Test
    void givenNullCondition_whenNot_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Condition.not(null)
        );
    }

    @Test
    void givenPassingCondition_whenNot_thenLogicalNegativeReturned() {

        final Object subject = new Object();
        final Condition<Object> condition = condition(true);

        final Condition<Object> logicalNegation = Condition.not(condition);

        assertEquals(!condition.test(subject), logicalNegation.test(subject));
        assertEquals("not[" + condition.name() + "]", logicalNegation.name());
    }

    @SuppressWarnings("unchecked")
    private static Condition<Object> condition(final boolean passed) {

        final Condition<Object> condition = mock(Condition.class);

        doReturn(passed).when(condition).test(any());
        doReturn("someName").when(condition).name();

        return condition;
    }

    @Test
    void givenFailingCondition_whenNot_thenLogicalNegativeReturned() {

        final Object subject = new Object();
        final Condition<Object> condition = condition(false);

        final Condition<Object> logicalNegation = Condition.not(condition);

        assertEquals(!condition.test(subject), logicalNegation.test(subject));
        assertEquals("not[" + condition.name() + "]", logicalNegation.name());
    }
}
