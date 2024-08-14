package io.github.libzeal.zeal.expression.lang.unary;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.rationale.Rationale;
import io.github.libzeal.zeal.expression.lang.rationale.RationaleGenerator;
import io.github.libzeal.zeal.expression.lang.rationale.SimpleRationale;
import org.junit.jupiter.api.Test;

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
    void givenNonNullableExpression_whenName_thenNameIsCorrect() {

        final String name = "someName";
        final TerminalUnaryExpression<Object> expression = TerminalUnaryExpression.of(name, new Object(),
            s -> true, generator());

        assertEquals(name, expression.name());
    }

    @Test
    void givenNonNullableExpression_whenSubject_thenSubjectIsCorrect() {

        final Object subject = new Object();
        final TerminalUnaryExpression<Object> expression = TerminalUnaryExpression.of("someName", subject,
            s -> true, generator());

        assertEquals(subject, expression.subject());
    }

    @Test
    void givenTrueNonNullableExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Object subject = new Object();
        final TerminalUnaryExpression<Object> expression = TerminalUnaryExpression.of(
            "someName",
            subject,
            s -> true,
            generator()
        );

        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.PASSED, evaluation.result());
        assertRationaleIsCorrect(evaluation.rationale());
    }

    private static void assertRationaleIsCorrect(final Rationale rationale) {
        assertEquals(EXPECTED, rationale.expected());
        assertEquals(ACTUAL, rationale.actual());
        assertTrue(rationale.hint().isPresent());
        assertEquals(HINT, rationale.hint().get());
    }

    @Test
    void givenFalseNonNullableExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Object subject = new Object();
        final TerminalUnaryExpression<Object> expression = TerminalUnaryExpression.of(
            "someName",
            subject,
            s -> false,
            generator()
        );

        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.FAILED, evaluation.result());
        assertRationaleIsCorrect(evaluation.rationale());
    }

    @Test
    void givenNullSubjectNonNullableExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final TerminalUnaryExpression<Object> expression = TerminalUnaryExpression.of(
            "someName",
            null,
            s -> true,
            generator()
        );

        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.FAILED, evaluation.result());
        assertRationaleIsCorrect(evaluation.rationale());
    }

    @Test
    void givenNullName_whenOfNullable_thenExceptionThrown() {

        final Object subject = new Object();
        final RationaleGenerator<Object> generator = generator();

        assertThrows(
            NullPointerException.class,
            () -> TerminalUnaryExpression.ofNullable(null, subject, s -> true, generator)
        );
    }

    @Test
    void givenNullSubject_whenOfNullable_thenExceptionNotThrown() {
        assertDoesNotThrow(
            () -> TerminalUnaryExpression.ofNullable("someName", null, s -> true, generator())
        );
    }

    @Test
    void givenNullPredicate_whenOfNullable_thenExceptionThrown() {

        final Object subject = new Object();
        final RationaleGenerator<Object> generator = generator();

        assertThrows(
            NullPointerException.class,
            () -> TerminalUnaryExpression.ofNullable("someName", subject, null, generator)
        );
    }

    @Test
    void givenNullGenerator_whenOfNullable_thenExceptionThrown() {

        final Object subject = new Object();

        assertThrows(
            NullPointerException.class,
            () -> TerminalUnaryExpression.ofNullable("someName", subject, s -> true, null)
        );
    }

    @Test
    void givenNullableExpression_whenName_thenNameIsCorrect() {

        final String name = "someName";
        final TerminalUnaryExpression<Object> expression = TerminalUnaryExpression.ofNullable(name, new Object(),
            s -> true, generator());

        assertEquals(name, expression.name());
    }

    @Test
    void givenNullableExpression_whenSubject_thenSubjectIsCorrect() {

        final Object subject = new Object();
        final TerminalUnaryExpression<Object> expression = TerminalUnaryExpression.ofNullable("someName", subject,
            s -> true, generator());

        assertEquals(subject, expression.subject());
    }

    @Test
    void givenTrueNullableExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Object subject = new Object();
        final TerminalUnaryExpression<Object> expression = TerminalUnaryExpression.ofNullable(
            "someName",
            subject,
            s -> true,
            generator()
        );

        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.PASSED, evaluation.result());
        assertRationaleIsCorrect(evaluation.rationale());
    }

    @Test
    void givenFalseNullableExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final Object subject = new Object();
        final TerminalUnaryExpression<Object> expression = TerminalUnaryExpression.ofNullable(
            "someName",
            subject,
            s -> false,
            generator()
        );

        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.FAILED, evaluation.result());
        assertRationaleIsCorrect(evaluation.rationale());
    }

    @Test
    void givenNullSubjectNullableExpression_whenEvaluate_thenEvaluationIsCorrect() {

        final TerminalUnaryExpression<Object> expression = TerminalUnaryExpression.ofNullable(
            "someName",
            null,
            s -> true,
            generator()
        );

        final Evaluation evaluation = expression.evaluate();

        assertEquals(Result.PASSED, evaluation.result());
        assertRationaleIsCorrect(evaluation.rationale());
    }
}
