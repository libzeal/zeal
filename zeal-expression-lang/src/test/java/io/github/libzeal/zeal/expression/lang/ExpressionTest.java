package io.github.libzeal.zeal.expression.lang;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpressionTest {

    @Test
    void whenConstruct_thenDefaultValuesAreCorrect() {

        Expression expression = new SimpleExpression();
        Evaluation skippedEvaluation = expression.skip();

        assertEquals("unnamed", expression.name());
        assertEquals(Result.SKIPPED, skippedEvaluation.result());
    }

    private static final class SimpleExpression implements Expression {

        @Override
        public Evaluation evaluate() {
            return null;
        }
    }
}
