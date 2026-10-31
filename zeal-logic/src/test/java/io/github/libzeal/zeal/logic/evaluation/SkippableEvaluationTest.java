package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.BooleanExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;
import io.github.libzeal.zeal.logic.test.TestExpressions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkippableEvaluationTest {

    private static final Evaluation DECIDER = TestExpressions.evaluation("decider", false);
    private static final Expression EXPRESSION = new BooleanExpression("skipped", () -> true);

    @Test
    void givenNullEvaluation_whenEvaluated_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new Evaluated(null)
        );
    }

    @Test
    void givenEvaluation_whenEvaluated_thenDelegatesToEvaluation() {

        final Evaluated evaluated = new Evaluated(DECIDER);

        assertEquals(DECIDER.name(), evaluated.name());
        assertSame(DECIDER, evaluated.evaluation());
    }

    @Test
    void givenNullExpression_whenSkipped_thenExceptionThrown() {

        final DirectCause.Delegated directCause = DirectCause.of(DECIDER);

        assertThrows(
            NullPointerException.class,
            () -> new Skipped(null, directCause)
        );
    }

    @Test
    void givenNullDirectCause_whenSkipped_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new Skipped(EXPRESSION, null)
        );
    }

    @Test
    void givenDecider_whenSkipped_thenNameIsExpressionNameAndDirectCauseIsDecider() {

        final Skipped skipped = new Skipped(EXPRESSION, DirectCause.of(DECIDER));

        assertEquals("skipped", skipped.name());
        assertEquals(List.of(DECIDER), skipped.directCause().evaluations());
    }
}
