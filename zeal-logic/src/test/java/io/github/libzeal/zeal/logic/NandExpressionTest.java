package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.test.TestExpressions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.github.libzeal.zeal.logic.CompoundEvaluator.format;
import static io.github.libzeal.zeal.logic.test.Arguments.listWithSingleNull;
import static io.github.libzeal.zeal.logic.test.CauseAssertions.*;
import static io.github.libzeal.zeal.logic.test.NamedExpressions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NandExpressionTest {

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {

        final List<Expression> children = List.of();

        assertThrows(
            NullPointerException.class,
            () -> new NandExpression(null, children)
        );
    }

    @Test
    void givenNullChildren_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new NandExpression("foo", null)
        );
    }

    @Test
    void givenNullChildEntry_whenConstruct_thenExceptionThrown() {

        final List<Expression> children = listWithSingleNull();

        assertThrows(
            NullPointerException.class,
            () -> new NandExpression("foo", children)
        );
    }

    @Test
    void givenNullVarargsChildren_whenUnnamed_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> NandExpression.unnamed((Expression[]) null)
        );
    }

    @Test
    void whenUnnamed_thenDefaultNameUsed() {
        assertEquals(NandExpression.DEFAULT_NAME, NandExpression.unnamed(List.of()).name());
        assertEquals(NandExpression.DEFAULT_NAME, NandExpression.unnamed().name());
    }

    @Test
    void givenMutableChildren_whenConstruct_thenChildrenAreCopied() {

        final Expression child = TestExpressions.tautology();
        final List<Expression> children = new ArrayList<>(List.of(child));

        final NandExpression expression = new NandExpression("foo", children);

        children.clear();

        assertEquals(List.of(child), expression.children());
    }

    @Test
    void givenNoChildren_whenEvaluate_thenResultIsFalseAndCauseIsSelf() {

        final Evaluation evaluation = nand("NAND").evaluate();
        final Rationale rationale = evaluation.rationale();

        assertFalse(evaluation.isTrue());
        assertEquals("NAND", evaluation.name());
        assertEquals(NandExpression.EXPECTED, rationale.expected());
        assertEquals(format(0, 0, 0), rationale.actual());
        assertCauseIsSelf(evaluation);
        assertTraces(evaluation, "NAND");
    }

    @Test
    void givenAllTrue_whenEvaluate_thenResultIsFalseAndCauseIsAllChildren() {

        final Evaluation evaluation = nand("NAND", t("t1"), t("t2")).evaluate();
        final Rationale rationale = evaluation.rationale();

        assertFalse(evaluation.isTrue());
        assertEquals(NandExpression.EXPECTED, rationale.expected());
        assertEquals(format(2, 0, 0), rationale.actual());
        assertCause(evaluation, child(evaluation, 0), child(evaluation, 1));
        assertTraces(evaluation, "NAND -> t1", "NAND -> t2");
        assertRootCauses(evaluation, child(evaluation, 0), child(evaluation, 1));
    }

    @Test
    void givenFalseInMiddle_whenEvaluate_thenResultIsTrueAndRemainingSkipped() {

        final Evaluation evaluation = nand("NAND", t("t1"), f("f2"), t("t3")).evaluate();
        final Evaluation f2 = child(evaluation, 1);
        final Skipped t3 = skipped(evaluation, 2);

        assertTrue(evaluation.isTrue());
        assertEquals(format(1, 1, 1), evaluation.rationale().actual());
        assertCause(evaluation, f2);
        assertTraces(evaluation, "NAND -> f2");

        assertEquals("t3", t3.name());
        assertCause(t3, f2);
        assertTraces(t3, "f2");
    }

    @Test
    void givenFalseBeforeTrue_whenEvaluate_thenTrueIsNotEvaluated() {

        final Expression failing = TestExpressions.expression(false);
        final Expression passing = TestExpressions.expression(true);

        final Evaluation evaluation = new NandExpression("NAND", List.of(failing, passing)).evaluate();

        assertTrue(evaluation.isTrue());
        verify(failing, times(1)).evaluate();
        verify(passing, never()).evaluate();
    }

    @Test
    void givenSameChildren_whenEvaluate_thenResultIsNegationOfAnd() {

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
                !new AndExpression("AND", children).evaluate().isTrue(),
                new NandExpression("NAND", children).evaluate().isTrue()
            );
        }
    }
}
