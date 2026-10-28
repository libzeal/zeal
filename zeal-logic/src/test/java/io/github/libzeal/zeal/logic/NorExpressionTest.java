package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.test.TestExpressions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.github.libzeal.zeal.logic.CompoundEvaluator.format;
import static io.github.libzeal.zeal.logic.CompoundEvaluator.formatFailed;
import static io.github.libzeal.zeal.logic.test.Arguments.listWithSingleNull;
import static io.github.libzeal.zeal.logic.test.CauseAssertions.*;
import static io.github.libzeal.zeal.logic.test.NamedExpressions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NorExpressionTest {

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {

        final List<Expression> children = List.of();

        assertThrows(
            NullPointerException.class,
            () -> new NorExpression(null, children)
        );
    }

    @Test
    void givenNullChildren_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new NorExpression("foo", null)
        );
    }

    @Test
    void givenNullChildEntry_whenConstruct_thenExceptionThrown() {

        final List<Expression> children = listWithSingleNull();

        assertThrows(
            NullPointerException.class,
            () -> new NorExpression("foo", children)
        );
    }

    @Test
    void givenNullVarargsChildren_whenUnnamed_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> NorExpression.unnamed((Expression[]) null)
        );
    }

    @Test
    void whenUnnamed_thenDefaultNameUsed() {
        assertEquals(NorExpression.DEFAULT_NAME, NorExpression.unnamed(List.of()).name());
        assertEquals(NorExpression.DEFAULT_NAME, NorExpression.unnamed().name());
    }

    @Test
    void givenMutableChildren_whenConstruct_thenChildrenAreCopied() {

        final Expression child = TestExpressions.tautology();
        final List<Expression> children = new ArrayList<>(List.of(child));

        final NorExpression expression = new NorExpression("foo", children);

        children.clear();

        assertEquals(List.of(child), expression.children());
    }

    @Test
    void givenNoChildren_whenEvaluate_thenResultIsTrueAndCauseIsSelf() {

        final Evaluation evaluation = nor("NOR").evaluate();
        final Rationale rationale = evaluation.rationale();

        assertTrue(evaluation.result());
        assertEquals("NOR", evaluation.name());
        assertEquals(formatFailed(0), rationale.expected());
        assertEquals(format(0, 0, 0), rationale.actual());
        assertCauseIsSelf(evaluation);
        assertTraces(evaluation, "NOR");
    }

    @Test
    void givenAllFalse_whenEvaluate_thenResultIsTrueAndCauseIsAllChildren() {

        final Evaluation evaluation = nor("NOR", f("f1"), f("f2")).evaluate();
        final Rationale rationale = evaluation.rationale();

        assertTrue(evaluation.result());
        assertEquals(formatFailed(2), rationale.expected());
        assertEquals(format(0, 2, 0), rationale.actual());
        assertCause(evaluation, child(evaluation, 0), child(evaluation, 1));
        assertTraces(evaluation, "NOR -> f1", "NOR -> f2");
        assertRootCauses(evaluation, child(evaluation, 0), child(evaluation, 1));
    }

    @Test
    void givenTrueInMiddle_whenEvaluate_thenResultIsFalseAndRemainingSkipped() {

        final Evaluation evaluation = nor("NOR", f("f1"), t("t2"), f("f3")).evaluate();
        final Evaluation t2 = child(evaluation, 1);
        final Skipped f3 = skipped(evaluation, 2);

        assertFalse(evaluation.result());
        assertEquals(format(1, 1, 1), evaluation.rationale().actual());
        assertCause(evaluation, t2);
        assertTraces(evaluation, "NOR -> t2");

        assertEquals("f3", f3.name());
        assertCause(f3, t2);
        assertTraces(f3, "t2");
    }

    @Test
    void givenTrueBeforeFalse_whenEvaluate_thenFalseIsNotEvaluated() {

        final Expression passing = TestExpressions.expression(true);
        final Expression failing = TestExpressions.expression(false);

        final Evaluation evaluation = new NorExpression("NOR", List.of(passing, failing)).evaluate();

        assertFalse(evaluation.result());
        verify(passing, times(1)).evaluate();
        verify(failing, never()).evaluate();
    }

    @Test
    void givenSameChildren_whenEvaluate_thenResultIsNegationOfOr() {

        final List<List<Expression>> cases = List.of(
            List.of(),
            List.of(t("a")),
            List.of(f("a")),
            List.of(t("a"), t("b")),
            List.of(t("a"), f("b")),
            List.of(f("a"), t("b")),
            List.of(f("a"), f("b"))
        );

        for (final List<Expression> children : cases) {
            assertEquals(
                !new OrExpression("OR", children).evaluate().result(),
                new NorExpression("NOR", children).evaluate().result()
            );
        }
    }
}
