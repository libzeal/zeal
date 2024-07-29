package io.github.libzeal.zeal.expression.evaluation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.github.libzeal.zeal.expression.evaluation.Result.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class ConjunctiveEvaluationTest {

    private String name;
    private List<Evaluation> children;
    private ConjunctiveEvaluation expression;

    @BeforeEach
    void setUp() {

        name = "foo";
        children = new ArrayList<>();

        expression = new ConjunctiveEvaluation(name, children);
    }

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new ConjunctiveEvaluation(null, children)
        );
    }

    @Test
    void givenNullChildren_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new ConjunctiveEvaluation(name, null)
        );
    }

    @Test
    void whenName_thenNameIsCorrect() {
        assertEquals(name, expression.name());
    }

    @Test
    void givenNonNullChildren_whenChildren_thenCorrectValueReturned() {
        assertEquals(children, expression.children());
    }

    @Test
    void givenEmptyChildren_whenResult_thenCorrectResult() {
        assertEquals(PASSED, expression.result());
    }

    @Test
    void givenOnePassingChild_whenResult_thenCorrectResult() {

        children.add(child(PASSED));

        assertEquals(PASSED, expression.result());
    }

    private static Evaluation child(Result result) {

        final Evaluation child = mock(Evaluation.class);

        doReturn(result).when(child).result();

        return child;
    }

    @Test
    void givenOneFailingChild_whenResult_thenCorrectResult() {

        children.add(child(FAILED));

        assertEquals(FAILED, expression.result());
    }

    @Test
    void givenOnePassingAndOneFailingChild_whenResult_thenCorrectResult() {

        children.add(child(PASSED));
        children.add(child(FAILED));

        assertEquals(FAILED, expression.result());
    }

    @Test
    void givenOnePassingAndOneSkippedChild_whenResult_thenCorrectResult() {

        children.add(child(PASSED));
        children.add(child(SKIPPED));

        assertEquals(SKIPPED, expression.result());
    }

    @Test
    void givenNoChildren_whenRationale_thenCorrectRationale() {

        Rationale rationale = expression.rationale();

        assertEquals("All children must pass", rationale.expected());
        assertEquals(expectedActual(0, 0, 0), rationale.actual());
    }

    private static String expectedActual(int passed, int failed, int skipped) {
        return "Passed: " + passed + ", Failed: " + failed + ", Skipped: " + skipped;
    }

    @Test
    void givenOnePassedChild_whenRationale_thenCorrectRationale() {

        children.add(child(PASSED));

        Rationale rationale = expression.rationale();

        assertEquals("All children must pass", rationale.expected());
        assertEquals(expectedActual(1, 0, 0), rationale.actual());
    }

    @Test
    void givenOneFailedChild_whenRationale_thenCorrectRationale() {

        children.add(child(FAILED));

        Rationale rationale = expression.rationale();

        assertEquals("All children must pass", rationale.expected());
        assertEquals(expectedActual(0, 1, 0), rationale.actual());
    }

    @Test
    void givenOneSkippedChild_whenRationale_thenCorrectRationale() {

        children.add(child(SKIPPED));

        Rationale rationale = expression.rationale();

        assertEquals("All children must pass", rationale.expected());
        assertEquals(expectedActual(0, 0, 1), rationale.actual());
    }
}
