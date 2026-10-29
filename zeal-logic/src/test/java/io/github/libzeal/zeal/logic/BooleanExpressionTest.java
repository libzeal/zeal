package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.logic.test.CauseAssertions.*;
import static io.github.libzeal.zeal.logic.test.NamedExpressions.*;
import static org.junit.jupiter.api.Assertions.*;

class BooleanExpressionTest {

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
