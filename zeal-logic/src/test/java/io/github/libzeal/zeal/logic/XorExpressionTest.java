package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.test.TestExpressions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.github.libzeal.zeal.logic.CompoundEvaluator.EXPECTED_ODD_PASSED;
import static io.github.libzeal.zeal.logic.CompoundEvaluator.format;
import static io.github.libzeal.zeal.logic.test.Arguments.listWithSingleNull;
import static io.github.libzeal.zeal.logic.test.CauseAssertions.*;
import static io.github.libzeal.zeal.logic.test.NamedExpressions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class XorExpressionTest {

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {

        final List<Expression> children = List.of();

        assertThrows(
            NullPointerException.class,
            () -> new XorExpression(null, children)
        );
    }

    @Test
    void givenNullChildren_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new XorExpression("foo", null)
        );
    }

    @Test
    void givenNullChildEntry_whenConstruct_thenExceptionThrown() {

        final List<Expression> children = listWithSingleNull();

        assertThrows(
            NullPointerException.class,
            () -> new XorExpression("foo", children)
        );
    }

    @Test
    void givenNullVarargsChildren_whenUnnamed_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> XorExpression.unnamed((Expression[]) null)
        );
    }

    @Test
    void whenUnnamed_thenDefaultNameUsed() {
        assertEquals(XorExpression.DEFAULT_NAME, XorExpression.unnamed(List.of()).name());
        assertEquals(XorExpression.DEFAULT_NAME, XorExpression.unnamed().name());
    }

    @Test
    void givenMutableChildren_whenConstruct_thenChildrenAreCopied() {

        final Expression child = TestExpressions.tautology();
        final List<Expression> children = new ArrayList<>(List.of(child));

        final XorExpression expression = new XorExpression("foo", children);

        children.clear();

        assertEquals(List.of(child), expression.children());
    }

    @Test
    void givenNoChildren_whenEvaluate_thenResultIsFalseAndCauseIsSelf() {

        final Evaluation evaluation = xor("XOR").evaluate();
        final Rationale rationale = evaluation.rationale();

        assertFalse(evaluation.result());
        assertEquals("XOR", evaluation.name());
        assertEquals(EXPECTED_ODD_PASSED, rationale.expected());
        assertEquals(format(0, 0, 0), rationale.actual());
        assertCauseIsSelf(evaluation);
        assertTraces(evaluation, "XOR");
    }

    @Test
    void givenTwoChildren_whenEvaluate_thenTrueOnlyWhenExactlyOneIsTrue() {
        assertFalse(xor("XOR", t("a"), t("b")).evaluate().result());
        assertTrue(xor("XOR", t("a"), f("b")).evaluate().result());
        assertTrue(xor("XOR", f("a"), t("b")).evaluate().result());
        assertFalse(xor("XOR", f("a"), f("b")).evaluate().result());
    }

    @Test
    void givenOneChild_whenEvaluate_thenResultIsChildResult() {
        assertTrue(xor("XOR", t("a")).evaluate().result());
        assertFalse(xor("XOR", f("a")).evaluate().result());
    }

    @Test
    void givenThreeTrueChildren_whenEvaluate_thenResultIsTrueBecauseCountIsOdd() {

        final Evaluation evaluation = xor("XOR", t("t1"), t("t2"), t("t3")).evaluate();

        assertTrue(evaluation.result());
        assertEquals(format(3, 0, 0), evaluation.rationale().actual());
    }

    @Test
    void givenNestedXor_whenEvaluate_thenResultMatchesFlatXor() {

        final boolean nested = xor("XOR", xor("XOR", t("a"), t("b")), t("c")).evaluate().result();
        final boolean flat = xor("XOR", t("a"), t("b"), t("c")).evaluate().result();

        assertEquals(flat, nested);
    }

    @Test
    void givenChildren_whenEvaluate_thenEveryChildIsEvaluated() {

        final Expression first = TestExpressions.expression(true);
        final Expression second = TestExpressions.expression(true);
        final Expression third = TestExpressions.expression(false);

        final CompoundEvaluation evaluation = new XorExpression("XOR", List.of(first, second, third)).evaluate();

        assertFalse(evaluation.result());
        verify(first, times(1)).evaluate();
        verify(second, times(1)).evaluate();
        verify(third, times(1)).evaluate();
        assertTrue(evaluation.children().stream().allMatch(Evaluated.class::isInstance));
    }

    @Test
    void givenChildren_whenEvaluate_thenCauseIsAllChildren() {

        final Evaluation evaluation = xor("XOR", t("t1"), f("f2")).evaluate();

        assertTrue(evaluation.result());
        assertEquals(EXPECTED_ODD_PASSED, evaluation.rationale().expected());
        assertEquals(format(1, 1, 0), evaluation.rationale().actual());
        assertCause(evaluation, child(evaluation, 0), child(evaluation, 1));
        assertTraces(evaluation, "XOR -> t1", "XOR -> f2");
        assertRootCauses(evaluation, child(evaluation, 0), child(evaluation, 1));
    }
}
