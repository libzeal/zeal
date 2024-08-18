package io.github.libzeal.zeal.types.core.unary.test;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import org.junit.jupiter.api.Assertions;

public class EvaluatedExpressionAssertion<T> {

    private final Evaluation eval;

    public EvaluatedExpressionAssertion(Evaluation eval) {
        this.eval = eval;
    }

    public void assertStateIs(Result expected) {
        Assertions.assertEquals(expected, eval.result(), "Result is incorrect");
    }

    public void assertNameIs(String name) {
        Assertions.assertEquals(name, eval.name(), "Name is incorrect");
    }

    public void assertExpectedIs(String expected) {
        Assertions.assertEquals(expected, eval.rationale().expected(), "Expected value is incorrect");
    }

    public void assertCompoundExpectedValue() {
        assertExpectedIs("All children must pass");
    }

    public void assertActualIs(String actual) {
        Assertions.assertEquals(actual, eval.rationale().actual(), "Actual value is incorrect");
    }

    public void assertCompoundActualValueIs(int passed, int failed, int skipped) {
        assertActualIs("Passed: " + passed + ", Failed: " + failed + ", Skipped: " + skipped);
    }

    public void assertHintIs(final String hint) {
        Assertions.assertEquals(hint, eval.rationale().hint().orElse(null));
    }
}
