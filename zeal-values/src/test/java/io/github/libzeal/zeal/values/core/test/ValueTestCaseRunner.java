package io.github.libzeal.zeal.values.core.test;

import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.values.core.ObjectValue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ValueTestCaseRunner {

    public static <S, V extends ObjectValue<S, V>> void run(
            final ValueTestCase<S, V> testCase) {

        final V expression = testCase.expressionFactory().apply(testCase.subject());
        final V returned = testCase.apply().apply(expression);

        assertNotNull(returned);
        assertEquals(expression.getClass(), returned.getClass());

        final Evaluation evaluation = expression.evaluate();
        final Evaluation terminal = terminalOf(evaluation);

        final var assertion = new ValueAssertion<>(terminal);

        assertion.assertStateIs(testCase.expectedResult());
        assertion.assertNameIs(testCase.expectedName());
        assertion.assertExpectedIs(testCase.expectedExpected());
        assertion.assertActualIs(testCase.expectedActual());

        if (testCase.expectedHint() != null) {
            assertion.assertHintIs(testCase.expectedHint());
        }
    }

    private static Evaluation terminalOf(Evaluation evaluation) {

        if (evaluation instanceof CompoundEvaluation compound
                && !compound.children().isEmpty()) {
            return compound.children().getFirst();
        }
        return evaluation;
    }
}
