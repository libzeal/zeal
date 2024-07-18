package io.github.libzeal.zeal.expression.types;

import io.github.libzeal.zeal.expression.SubjectExpression;
import io.github.libzeal.zeal.expression.evalulation.EvaluatedExpression;
import io.github.libzeal.zeal.expression.evalulation.EvaluationState;

import static org.junit.jupiter.api.Assertions.*;

public class EvaluatedExpressionAssertion<T> {

    private final EvaluatedExpression eval;

    public EvaluatedExpressionAssertion(EvaluatedExpression eval) {
        this.eval = eval;
    }

    public void assertStateIs(EvaluationState expected) {
        assertEquals(expected, eval.state());
    }

    public void assertNameMatches(String regex) {
        assertTrue(eval.name().matches(regex));
    }

    public void assertExpectedIs(String expected) {
        assertEquals(expected, eval.expected());
    }

    public void assertCompoundExpectedValue() {
        assertExpectedIs("All children must pass");
    }

    public void assertActualIs(String actual) {
        assertEquals(actual, eval.actual());
    }

    public void assertCompoundActualValueIs(int passed, int failed, int skipped) {
        assertActualIs("Passed: " + passed + ", Failed: " + failed + ", Skipped: " + skipped);
    }

    public void assertHasNoChildren() {
        assertTrue(eval.children().isEmpty());
    }

    public void assertFailsNotNullCheck() {
        assertTrue(eval.failsNotNullCheck());
    }

    public void assertDoesNotFailNotNullCheck() {
        assertFalse(eval.failsNotNullCheck());
    }
}
