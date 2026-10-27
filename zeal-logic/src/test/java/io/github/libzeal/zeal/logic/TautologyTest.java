package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.logic.test.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class TautologyTest {

    private static final String TRUE = "true";
    private Tautology tautology;

    @BeforeEach
    void setUp() {
        tautology = new Tautology();
    }

    @Test
    void givenDefaults_whenName_thenNameIsCorrect() {
        assertEquals(Tautology.NAME, tautology.name());
    }

    @Test
    void givenDefault_whenEvaluate_thenEvaluationIsCorrect() {

        final Evaluation evaluation = tautology.evaluate();
        final Rationale rationale = evaluation.rationale();

        assertTrue(evaluation.result());
        assertEquals(Tautology.NAME, evaluation.name());
        assertRationaleEquals(rationale, TRUE, TRUE);
        assertEquals(Cause.self(), evaluation.cause());
        assertDepthFirstTraversalIsTerminal(evaluation);
    }

    @Test
    void givenDefault_whenEvaluateTwice_thenDistinctEvaluationsReturned() {
        assertNotSame(tautology.evaluate(), tautology.evaluate());
    }
}
