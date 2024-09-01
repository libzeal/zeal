package io.github.libzeal.zeal.logic.evaluation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FlatteningTraverserTest {

    private FlatteningTraverser traverser;

    @BeforeEach
    void setUp() {
        traverser = new FlatteningTraverser();
    }

    @Test
    void givenNoEvaluations_whenFound_thenCorrectFound() {
        assertTrue(traverser.found().isEmpty());
    }

    @Test
    void givenOneEvaluation_whenFound_thenCorrectFound() {

        final Evaluation evaluation = mock(Evaluation.class);
        final TraversalContext context = mock(TraversalContext.class);

        traverser.on(evaluation, context);

        final List<Evaluation> found = traverser.found();

        assertEquals(1, found.size());
        assertEquals(evaluation, found.get(0));
    }

    @Test
    void givenTwoEvaluations_whenFound_thenCorrectFound() {

        final Evaluation evaluation1 = mock(Evaluation.class);
        final Evaluation evaluation2 = mock(Evaluation.class);
        final TraversalContext context = mock(TraversalContext.class);

        traverser.on(evaluation1, context);
        traverser.on(evaluation2, context);

        final List<Evaluation> found = traverser.found();

        assertEquals(2, found.size());
        assertEquals(evaluation1, found.get(0));
        assertEquals(evaluation2, found.get(1));
    }

    @Test
    void givenNoEvaluations_whenChildren_thenCorrectChildrenFound() {
        assertTrue(traverser.children().isEmpty());
    }

    @Test
    void givenOneEvaluation_whenChildren_thenCorrectChildrenFound() {

        final Evaluation evaluation = mock(Evaluation.class);
        final TraversalContext context = mock(TraversalContext.class);

        traverser.on(evaluation, context);

        final List<Evaluation> children = traverser.children();

        assertTrue(children.isEmpty());
    }

    @Test
    void givenTwoEvaluations_whenFound_thenCorrectChildrenFound() {

        final Evaluation evaluation1 = mock(Evaluation.class);
        final Evaluation evaluation2 = mock(Evaluation.class);
        final TraversalContext context = mock(TraversalContext.class);

        traverser.on(evaluation1, context);
        traverser.on(evaluation2, context);

        final List<Evaluation> children = traverser.children();

        assertEquals(1, children.size());
        assertEquals(evaluation2, children.get(0));
    }

    @Test
    void givenNoEvaluations_wheGet_thenCorrectChildrenFound() {
        assertFalse(traverser.get(-1).isPresent());
        assertFalse(traverser.get(0).isPresent());
        assertFalse(traverser.get(1).isPresent());
    }

    @Test
    void givenOneEvaluation_whenGet_thenCorrectChildrenFound() {

        final Evaluation evaluation = mock(Evaluation.class);
        final TraversalContext context = mock(TraversalContext.class);

        traverser.on(evaluation, context);

        assertFalse(traverser.get(-1).isPresent());
        assertTrue(traverser.get(0).isPresent());
        assertFalse(traverser.get(1).isPresent());
        assertEquals(evaluation, traverser.get(0).get());
    }

    @Test
    void givenTwoEvaluations_whenGet_thenCorrectChildrenFound() {

        final Evaluation evaluation1 = mock(Evaluation.class);
        final Evaluation evaluation2 = mock(Evaluation.class);
        final TraversalContext context = mock(TraversalContext.class);

        traverser.on(evaluation1, context);
        traverser.on(evaluation2, context);

        assertFalse(traverser.get(-1).isPresent());
        assertTrue(traverser.get(0).isPresent());
        assertTrue(traverser.get(1).isPresent());
        assertFalse(traverser.get(2).isPresent());
        assertEquals(evaluation1, traverser.get(0).get());
        assertEquals(evaluation2, traverser.get(1).get());
    }
}
