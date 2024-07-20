package io.github.libzeal.zeal.expression.types;

import io.github.libzeal.zeal.expression.evaluation.EvaluatedExpression;
import io.github.libzeal.zeal.expression.evaluation.EvaluationState;

import static org.junit.jupiter.api.Assertions.*;

public class EvaluatedExpressionAssertion<T> {

    private final EvaluatedExpression eval;

    public EvaluatedExpressionAssertion(EvaluatedExpression eval) {
        this.eval = eval;
    }

    public void assertStateIs(EvaluationState expected) {
        assertEquals(expected, eval.state());
    }

    public void assertNameIs(String name) {
        assertEquals(name, eval.name());
    }

    public void assertNameMatches(String regex) {
        assertTrue(eval.name().matches(regex), "Expected: " + regex + ", actual: " + eval.name());
    }

    public void assertExpectedIs(String expected) {
        assertEquals(expected, eval.reason().expected());
    }

    public void assertCompoundExpectedValue() {
        assertExpectedIs("All children must pass");
    }

    public void assertActualIs(String actual) {
        assertEquals(actual, eval.reason().actual());
    }

    public void assertCompoundActualValueIs(int passed, int failed, int skipped) {
        assertActualIs("Passed: " + passed + ", Failed: " + failed + ", Skipped: " + skipped);
    }

    public void assertHasNoChildren() {
        assertTrue(eval.children().isEmpty());
    }
}
