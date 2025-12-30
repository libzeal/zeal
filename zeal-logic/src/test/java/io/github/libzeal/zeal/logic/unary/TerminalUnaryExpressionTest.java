package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.RationaleGenerator;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class TerminalUnaryExpressionTest {

    private static final String EXPECTED = "foo";
    private static final String ACTUAL = "bar";
    private static final String HINT = "baz";

    @Test
    void givenNullName_whenOf_thenExceptionThrown() {

        final Object subject = new Object();
        final RationaleGenerator<Object> generator = generator();

        assertThrows(
            NullPointerException.class,
            () -> TerminalUnaryExpression.of(null, subject, s -> true, generator)
        );
    }

    @SuppressWarnings("unchecked")
    private static RationaleGenerator<Object> generator() {

        final RationaleGenerator<Object> generator = mock(RationaleGenerator.class);
        final Rationale rationale = new SimpleRationale(EXPECTED, ACTUAL, HINT);

        doReturn(rationale).when(generator).generate(any(), anyBoolean());

        return generator;
    }

    @Test
    void givenNullSubject_whenOf_thenExceptionNotThrown() {
        assertDoesNotThrow(
            () -> TerminalUnaryExpression.of("someName", null, s -> true, generator())
        );
    }

    @Test
    void givenNullPredicate_whenOf_thenExceptionThrown() {

        final Object subject = new Object();
        final RationaleGenerator<Object> generator = generator();

        assertThrows(
            NullPointerException.class,
            () -> TerminalUnaryExpression.of("someName", subject, null, generator)
        );
    }

    @Test
    void givenNullGenerator_whenOf_thenExceptionThrown() {

        final Object subject = new Object();

        assertThrows(
            NullPointerException.class,
            () -> TerminalUnaryExpression.of("someName", subject, s -> true, null)
        );
    }

    @Test
    void givenNullableExpression_whenName_thenNameIsCorrect() {

        final String name = "someName";
        final TerminalUnaryExpression<Object> expression = TerminalUnaryExpression.of(name, new Object(),
            s -> true, generator());

        assertEquals(name, expression.name());
    }

    @Test
    void givenNullableExpression_whenSubject_thenSubjectIsCorrect() {

        final Object subject = new Object();
        final TerminalUnaryExpression<Object> expression = TerminalUnaryExpression.of("someName", subject,
            s -> true, generator());

        assertEquals(subject, expression.subject());
    }

    @Test
    void givenTrueNullableExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Object subject = new Object();
        final TerminalUnaryExpression<Object> expression = TerminalUnaryExpression.of(
            "someName",
            subject,
            s -> true,
            generator()
        );

        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.TRUE, evaluation.result());
        assertRationaleIsCorrect(evaluation.rationale());
    }

    private static void assertRationaleIsCorrect(final Rationale rationale) {
        assertEquals(EXPECTED, rationale.expected());
        assertEquals(ACTUAL, rationale.actual());
        assertTrue(rationale.hint().isPresent());
        assertEquals(HINT, rationale.hint().get());
    }

    @Test
    void givenFalseNullableExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Object subject = new Object();
        final TerminalUnaryExpression<Object> expression = TerminalUnaryExpression.of(
            "someName",
            subject,
            s -> false,
            generator()
        );

        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.FALSE, evaluation.result());
        assertRationaleIsCorrect(evaluation.rationale());
    }

    @Test
    void givenNullSubjectNullableExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final TerminalUnaryExpression<Object> expression = TerminalUnaryExpression.of(
            "someName",
            null,
            s -> true,
            generator()
        );

        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.TRUE, evaluation.result());
        assertRationaleIsCorrect(evaluation.rationale());
    }
}
