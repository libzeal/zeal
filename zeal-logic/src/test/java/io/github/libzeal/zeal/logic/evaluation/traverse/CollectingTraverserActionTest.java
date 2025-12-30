package io.github.libzeal.zeal.logic.evaluation.traverse;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CollectingTraverserActionTest {

    private CollectingTraverserAction action;

    @BeforeEach
    void setUp() {
        action = new CollectingTraverserAction();
    }

    @Test
    void givenNoEvaluations_whenFound_thenCorrectFound() {
        assertTrue(action.found().isEmpty());
    }

    @Test
    void givenOneEvaluation_whenFound_thenCorrectFound() {

        final Evaluation evaluation = mock(Evaluation.class);
        final TraversalContext context = mock(TraversalContext.class);

        action.on(evaluation, context);

        final List<Evaluation> found = action.found();

        assertEquals(1, found.size());
        assertEquals(evaluation, found.get(0));
    }

    @Test
    void givenTwoEvaluations_whenFound_thenCorrectFound() {

        final Evaluation evaluation1 = mock(Evaluation.class);
        final Evaluation evaluation2 = mock(Evaluation.class);
        final TraversalContext context = mock(TraversalContext.class);

        action.on(evaluation1, context);
        action.on(evaluation2, context);

        final List<Evaluation> found = action.found();

        assertEquals(2, found.size());
        assertEquals(evaluation1, found.get(0));
        assertEquals(evaluation2, found.get(1));
    }

    @Test
    void givenNoEvaluations_whenChildren_thenCorrectChildrenFound() {
        assertTrue(action.children().isEmpty());
    }

    @Test
    void givenOneEvaluation_whenChildren_thenCorrectChildrenFound() {

        final Evaluation evaluation = mock(Evaluation.class);
        final TraversalContext context = mock(TraversalContext.class);

        action.on(evaluation, context);

        final List<Evaluation> children = action.children();

        assertTrue(children.isEmpty());
    }

    @Test
    void givenTwoEvaluations_whenFound_thenCorrectChildrenFound() {

        final Evaluation evaluation1 = mock(Evaluation.class);
        final Evaluation evaluation2 = mock(Evaluation.class);
        final TraversalContext context = mock(TraversalContext.class);

        action.on(evaluation1, context);
        action.on(evaluation2, context);

        final List<Evaluation> children = action.children();

        assertEquals(1, children.size());
        assertEquals(evaluation2, children.get(0));
    }

    @Test
    void givenNoEvaluations_wheGet_thenCorrectChildrenFound() {
        assertFalse(action.get(-1).isPresent());
        assertFalse(action.get(0).isPresent());
        assertFalse(action.get(1).isPresent());
    }

    @Test
    void givenOneEvaluation_whenGet_thenCorrectChildrenFound() {

        final Evaluation evaluation = mock(Evaluation.class);
        final TraversalContext context = mock(TraversalContext.class);

        action.on(evaluation, context);

        assertFalse(action.get(-1).isPresent());
        assertTrue(action.get(0).isPresent());
        assertFalse(action.get(1).isPresent());
        assertEquals(evaluation, action.get(0).get());
    }

    @Test
    void givenTwoEvaluations_whenGet_thenCorrectChildrenFound() {

        final Evaluation evaluation1 = mock(Evaluation.class);
        final Evaluation evaluation2 = mock(Evaluation.class);
        final TraversalContext context = mock(TraversalContext.class);

        action.on(evaluation1, context);
        action.on(evaluation2, context);

        assertFalse(action.get(-1).isPresent());
        assertTrue(action.get(0).isPresent());
        assertTrue(action.get(1).isPresent());
        assertFalse(action.get(2).isPresent());
        assertEquals(evaluation1, action.get(0).get());
        assertEquals(evaluation2, action.get(1).get());
    }
}
