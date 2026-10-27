package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.logic.test.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class ContradictionTest {

    private static final String FALSE = "false";
    private Contradiction contradiction;

    @BeforeEach
    void setUp() {
        contradiction = new Contradiction();
    }

    @Test
    void givenDefaults_whenName_thenNameIsCorrect() {
        assertEquals(Contradiction.NAME, contradiction.name());
    }

    @Test
    void givenDefault_whenEvaluate_thenEvaluationIsCorrect() {

        final Evaluation evaluation = contradiction.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertFalse(evaluation.result());
        assertEquals(Contradiction.NAME, evaluation.name());
        assertRationaleEquals(rationale, FALSE, FALSE);
        assertEquals(Cause.self(), evaluation.cause());
        assertDepthFirstTraversalIsTerminal(evaluation);
    }

    @Test
    void givenDefault_whenEvaluateTwice_thenDistinctEvaluationsReturned() {
        assertNotSame(contradiction.evaluate(), contradiction.evaluate());
    }
}
