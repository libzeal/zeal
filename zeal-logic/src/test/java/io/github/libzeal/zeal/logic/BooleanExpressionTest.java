package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.logic.test.CauseAssertions.*;
import static io.github.libzeal.zeal.logic.test.NamedExpressions.*;
import static org.junit.jupiter.api.Assertions.*;

class BooleanExpressionTest {

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {
        assertThrows(
                NullPointerException.class,
                () -> new BooleanExpression(null, () -> true)
        );
    }

    @Test
    void givenNullSupplier_whenConstruct_thenExceptionThrown() {
        assertThrows(
                NullPointerException.class,
                () -> new BooleanExpression("foo", null)
        );
    }

    @Test
    void givenNullSupplier_whenConstructWithDefaultName_thenExceptionThrown() {
        assertThrows(
                NullPointerException.class,
                () -> new BooleanExpression(null)
        );
    }

    @Test
    void givenValidSupplier_whenConstructWithDefaultName_thenNameIsDefault() {
        assertEquals(Expression.DEFAULT_NAME, new BooleanExpression(() -> true).name());
    }

    @Test
    void givenName_whenName_thenNameIsCorrect() {
        assertEquals("foo", new BooleanExpression("foo", () -> true).name());
    }

    @Test
    void givenTrueTerminal_whenEvaluate_thenCauseIsSelf() {

        final Evaluation evaluation = t("t").evaluate();

        assertTrue(evaluation.isTrue());
        assertCauseIsSelf(evaluation);
        assertTraces(evaluation, "t");
        assertRootCauses(evaluation, evaluation);
    }

    @Test
    void givenFalseTerminal_whenEvaluate_thenCauseIsSelf() {

        final Evaluation evaluation = f("f").evaluate();

        assertFalse(evaluation.isTrue());
        assertCauseIsSelf(evaluation);
        assertTraces(evaluation, "f");
        assertRootCauses(evaluation, evaluation);
    }
}
