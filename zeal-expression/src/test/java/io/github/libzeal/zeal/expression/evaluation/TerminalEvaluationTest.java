package io.github.libzeal.zeal.expression.evaluation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TerminalEvaluationTest {

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
            () -> new TerminalEvaluation(null, "foo", rationale)
        );
    }

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new TerminalEvaluation(Result.PASSED, null, rationale)
        );
    }

    @Test
    void givenNullRationale_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new TerminalEvaluation(Result.PASSED, "foo", null)
        );
    }

    @Test
    void whenConstruct_thenValidDataReturned() {

        TerminalEvaluation expression = new TerminalEvaluation(result, name, rationale);

        assertEquals(result, expression.result());
        assertEquals(name, expression.name());
        assertEquals(rationale, expression.rationale());
    }
}
