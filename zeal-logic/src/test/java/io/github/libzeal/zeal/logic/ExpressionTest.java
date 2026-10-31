package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpressionTest {

    @Test
    void givenNameNotOverridden_whenName_thenNameIsDefault() {

        final TerminalExpression expression = () -> new TerminalEvaluation(
                true, Expression.DEFAULT_NAME, SimpleRationale.empty(), Duration.ZERO
        );

        assertEquals(Expression.DEFAULT_NAME, expression.name());
    }
}
