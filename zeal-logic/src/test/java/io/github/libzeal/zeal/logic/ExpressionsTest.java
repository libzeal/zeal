package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.DirectCause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.test.TestExpressions;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.logic.test.Assertions.assertDepthFirstTraversalIsTerminal;
import static io.github.libzeal.zeal.logic.test.Assertions.assertRationaleEquals;
import static org.junit.jupiter.api.Assertions.*;

class ExpressionsTest {

    private static final String TRUE = "true";
    private static final String FALSE = "false";

    @Test
    void givenTautology_whenName_thenNameIsCorrect() {
        assertEquals(Tautology.NAME, Expressions.tautology().name());
    }

    @Test
    void givenTautology_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression expression = Expressions.tautology();
        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertTrue(evaluation.isTrue());
        assertEquals(Tautology.NAME, evaluation.name());
        assertRationaleEquals(rationale, TRUE, TRUE);
        assertEquals(DirectCause.self(), evaluation.cause().direct());
        assertDepthFirstTraversalIsTerminal(evaluation);
    }

    @Test
    void givenContradiction_whenName_thenNameIsCorrect() {
        assertEquals(Contradiction.NAME, Expressions.contradiction().name());
    }

    @Test
    void givenContradiction_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression expression = Expressions.contradiction();
        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertFalse(evaluation.isTrue());
        assertEquals(Contradiction.NAME, evaluation.name());
        assertRationaleEquals(rationale, FALSE, FALSE);
        assertEquals(DirectCause.self(), evaluation.cause().direct());
        assertDepthFirstTraversalIsTerminal(evaluation);
    }

    @Test
    void givenNullSupplier_whenOf_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Expressions.of(null)
        );
    }

    @Test
    void givenValidSupplier_whenOf_thenCorrectExpressionReturned() {

        final BooleanExpression expression = Expressions.of(() -> true);
        final Evaluation evaluation = expression.evaluate();

        assertEquals(Expression.DEFAULT_NAME, expression.name());
        assertTrue(evaluation.isTrue());
    }

    @Test
    void givenNullWrappedExpression_whenNot_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Expressions.not(null)
        );
    }

    @Test
    void givenValidWrappedExpression_whenNot_thenCorrectExpressionReturned() {

        final Expression wrappedExpression = TestExpressions.tautology();
        final NotExpression expression = Expressions.not(wrappedExpression);

        assertNotNull(expression);
        assertInstanceOf(NotExpression.class, expression);
    }

    @Test
    void givenNullExpression_whenAnd_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Expressions.and((Expression[]) null)
        );
    }

    @Test
    void givenValidWrappedExpressions_whenAnd_thenCorrectExpressionReturned() {

        final Expression wrappedExpression = TestExpressions.expression(true);
        final AndExpression expression = Expressions.and(wrappedExpression);

        assertNotNull(expression);
        assertInstanceOf(AndExpression.class, expression);
    }

    @Test
    void givenNullExpression_whenOr_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Expressions.or((Expression[]) null)
        );
    }

    @Test
    void givenValidWrappedExpressions_whenOr_thenCorrectExpressionReturned() {

        final Expression wrappedExpression = TestExpressions.tautology();
        final OrExpression expression = Expressions.or(wrappedExpression);

        assertNotNull(expression);
        assertInstanceOf(OrExpression.class, expression);
    }

    @Test
    void givenNullExpression_whenNand_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Expressions.nand((Expression[]) null)
        );
    }

    @Test
    void givenValidWrappedExpressions_whenNand_thenCorrectExpressionReturned() {

        final Expression wrappedExpression = TestExpressions.tautology();
        final Expression expression = Expressions.nand(wrappedExpression);

        assertNotNull(expression);
        assertInstanceOf(NandExpression.class, expression);
    }

    @Test
    void givenNullExpression_whenNor_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Expressions.nor((Expression[]) null)
        );
    }

    @Test
    void givenValidWrappedExpressions_whenNor_thenCorrectExpressionReturned() {

        final Expression wrappedExpression = TestExpressions.tautology();
        final Expression expression = Expressions.nor(wrappedExpression);

        assertNotNull(expression);
        assertInstanceOf(NorExpression.class, expression);
    }

    @Test
    void givenNullExpression_whenXor_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Expressions.xor((Expression[]) null)
        );
    }

    @Test
    void givenValidWrappedExpressions_whenXor_thenCorrectExpressionReturned() {

        final Expression wrappedExpression = TestExpressions.tautology();
        final Expression expression = Expressions.xor(wrappedExpression);

        assertNotNull(expression);
        assertInstanceOf(XorExpression.class, expression);
    }
}
