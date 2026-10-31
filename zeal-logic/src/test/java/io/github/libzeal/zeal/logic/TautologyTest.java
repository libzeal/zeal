package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.DirectCause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.logic.test.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

        assertTrue(evaluation.isTrue());
        assertEquals(Tautology.NAME, evaluation.name());
        assertRationaleEquals(rationale, TRUE, TRUE);
        assertEquals(DirectCause.self(), evaluation.cause().direct());
        assertDepthFirstTraversalIsTerminal(evaluation);
    }

    @Test
    void givenDefault_whenEvaluateTwice_thenDistinctEvaluationsReturned() {
        assertNotSame(tautology.evaluate(), tautology.evaluate());
    }

    @Test
    void givenTwoTautologies_whenEquals_thenEqual() {

        final Tautology other = new Tautology();

        assertNotSame(tautology, other);
        assertEquals(tautology, other);
        assertEquals(tautology.hashCode(), other.hashCode());
    }

    @Test
    void givenContradiction_whenEquals_thenNotEqual() {
        assertNotEquals(tautology, new Contradiction());
    }

    @Test
    void givenCompoundsOfTautologies_whenEquals_thenEqual() {
        assertEquals(
            AndExpression.unnamed(new Tautology()),
            AndExpression.unnamed(new Tautology())
        );
    }
}
