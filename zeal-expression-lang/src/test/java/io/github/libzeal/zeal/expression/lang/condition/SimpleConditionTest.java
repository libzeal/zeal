package io.github.libzeal.zeal.expression.lang.condition;

import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleConditionTest {

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new SimpleCondition<>(null, s -> true)
        );
    }

    @Test
    void givenNullPredicate_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new SimpleCondition<>("foo", null)
        );
    }

    @Test
    void givenValidName_whenCreate_thenCorrectNameReturned() {

        final Object subject = new Object();
        final String name = "foo";

        Condition<Object> condition = new SimpleCondition<>(name, s -> true);

        assertEquals(name, condition.create(subject).name());
    }

    @Test
    void givenAlwaysTruePredicate_whenTest_thenConditionResolvesTrue() {

        final Object subject = new Object();
        final Condition<Object> condition = new SimpleCondition<>("foo", s -> true);

        assertEquals(Result.PASSED, condition.create(subject).evaluate().result());
    }

    @Test
    void givenAlwaysFalsePredicate_whenTest_thenConditionResolvesTrue() {

        final Object subject = new Object();
        final Condition<Object> condition = new SimpleCondition<>("foo", s -> false);

        assertEquals(Result.FAILED, condition.create(subject).evaluate().result());
    }
}
