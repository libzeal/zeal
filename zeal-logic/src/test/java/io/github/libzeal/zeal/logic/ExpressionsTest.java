package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.logic.test.Assertions.assertDepthFirstTraversalIsTerminal;
import static io.github.libzeal.zeal.logic.test.Assertions.assertRationaleEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ExpressionsTest {

    private static final String TRUE = "true";
    private static final String FALSE = "false";

    @Test
    void givenContradiction_whenName_thenNameIsCorrect() {
        assertEquals(Contradiction.NAME, Expressions.contradiction().name());
    }

    @Test
    void givenContradiction_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression expression = Expressions.contradiction();
        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.FALSE, evaluation.result());
        assertEquals(Contradiction.NAME, evaluation.name());
        assertRationaleEquals(rationale, FALSE, FALSE);
        assertEquals(evaluation, evaluation.cause().evaluation());
        assertDepthFirstTraversalIsTerminal(evaluation);
    }

    @Test
    void givenTautology_whenName_thenNameIsCorrect() {
        assertEquals(Tautology.NAME, Expressions.tautology().name());
    }

    @Test
    void givenTautology_whenEvaluate_thenEvaluationIsCorrect() {

        final Expression expression = Expressions.tautology();
        final Evaluation evaluation = expression.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertEquals(Result.TRUE, evaluation.result());
        assertEquals(Tautology.NAME, evaluation.name());
        assertRationaleEquals(rationale, TRUE, TRUE);
        assertEquals(evaluation, evaluation.cause().evaluation());
        assertDepthFirstTraversalIsTerminal(evaluation);
    }

    @Test
    void givenNullWrappedExpression_whenNot_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> Expressions.not((Expression) null)
        );
    }

    @Test
    void givenValidWrappedExpression_whenNot_thenCorrectExpressionReturned() {

        final Expression wrappedExpression = mock(Expression.class);
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

        final Expression wrappedExpression = mock(Expression.class);
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

        final Expression wrappedExpression = mock(Expression.class);
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

        final Expression wrappedExpression = mock(Expression.class);
        final Expression expression = Expressions.nand(wrappedExpression);

        assertNotNull(expression);
        assertInstanceOf(NotExpression.class, expression);
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

        final Expression wrappedExpression = mock(Expression.class);
        final Expression expression = Expressions.nor(wrappedExpression);

        assertNotNull(expression);
        assertInstanceOf(NotExpression.class, expression);
    }
}
