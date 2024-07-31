package io.github.libzeal.zeal.expression.operation;

import io.github.libzeal.zeal.expression.evaluation.Rationale;
import io.github.libzeal.zeal.expression.evaluation.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvaluatedOperationTest {

    private Result result;
    private String name;
    private Rationale rationale;

    @BeforeEach
    void setUp() {

        result = Result.PASSED;
        name = "foo";
        rationale = Rationale.empty();
    }

    @Test
    void givenNullResult_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new EvaluatedOperation("foo", null, () -> rationale)
        );
    }

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new EvaluatedOperation(null, Result.PASSED, () -> rationale)
        );
    }

    @Test
    void givenNullRationale_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new EvaluatedOperation("foo", Result.PASSED, null)
        );
    }

    @Test
    void whenConstruct_thenValidDataReturned() {

        EvaluatedOperation expression = new EvaluatedOperation(name, result, () -> rationale);

        assertEquals(result, expression.result());
        assertEquals(name, expression.name());
        assertEquals(rationale, expression.rationale());
        assertTrue(expression.children().isEmpty());
    }
}
