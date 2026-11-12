package io.github.libzeal.zeal.values.core.test;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import org.junit.jupiter.api.Assertions;

public class ValueAssertion<T> {

    private final Evaluation eval;

    public ValueAssertion(Evaluation eval) {
        this.eval = eval;
    }

    public void assertStateIs(boolean expected) {
        Assertions.assertEquals(expected, eval.isTrue(), "Result is incorrect (" + eval.name() + ")");
    }

    public void assertNameIs(String name) {
        Assertions.assertEquals(name, eval.name(), "Name is incorrect");
    }

    public void assertExpectedIs(String expected) {
        Assertions.assertEquals(expected, eval.rationale().expected(), "Expected value is incorrect (" + eval.name() + ")");
    }

    public void assertCompoundExpectedValue(final String expected) {
        assertExpectedIs(expected);
    }

    public void assertActualIs(String actual) {
        Assertions.assertEquals(actual, eval.rationale().actual(), "Actual value is incorrect (" + eval.name() + ")");
    }

    public void assertCompoundActualValueIs(final int trueCount, final int falseCount, final int skippedCount) {
        assertActualIs("True: " + trueCount + ", False: " + falseCount + ", Skipped: " + skippedCount);
    }

    public void assertHintIs(final String hint) {
        Assertions.assertEquals(hint, eval.rationale().hint().orElse(null));
    }
}
