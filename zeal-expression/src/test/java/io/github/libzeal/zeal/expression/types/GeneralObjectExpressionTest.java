package io.github.libzeal.zeal.expression.types;

import io.github.libzeal.zeal.expression.evalulation.EvaluationState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GeneralObjectExpressionTest {

    private static final String OBJECT_EXPRESSION_NAME = "Object\\(.+\\) evaluation";

    @Test
    void givenNoEvaluations_whenConstruct_thenCorrectEvaluation() {

        Object value = new Object();

        GeneralObjectExpression<?> expression = new GeneralObjectExpression<>(value);
        EvaluatedExpressionAssertion<?> assertion = new EvaluatedExpressionAssertion<>(expression.evaluate());

        assertEquals(value, expression.subject());
        assertion.assertStateIs(EvaluationState.PASSED);
        assertion.assertNameMatches(OBJECT_EXPRESSION_NAME);
        assertion.assertCompoundExpectedValue();
        assertion.assertCompoundActualValueIs(0, 0, 0);
        assertion.assertDoesNotFailNotNullCheck();
    }

    @Test
    void givenNullValue_whenIsNotNull_thenCorrectEvaluation() {

        GeneralObjectExpression<?> expression = new GeneralObjectExpression<>(null);

        expression.isNotNull();

        EvaluatedExpressionAssertion<?> assertion = new EvaluatedExpressionAssertion<>(expression.evaluate());

        assertNull(expression.subject());
        assertion.assertStateIs(EvaluationState.FAILED);
        assertion.assertNameMatches(OBJECT_EXPRESSION_NAME);
        assertion.assertCompoundExpectedValue();
        assertion.assertCompoundActualValueIs(0, 1, 0);
        assertion.assertFailsNotNullCheck();
    }
}
