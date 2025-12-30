package io.github.libzeal.zeal.logic.evaluation.traverse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TraversalContextTest {

    @Test
    void givenNegativeDepth_whenWithDepth_thenExceptionThrown() {
        assertThrows(
            IllegalArgumentException.class,
            () -> TraversalContext.withDepth(-1)
        );
    }

    @Test
    void givenDepth_whenDepth_thenDepthIsCorrect() {

        final int depth = 1;

        final TraversalContext context = TraversalContext.withDepth(depth);

        assertEquals(depth, context.depth());
    }

    @Test
    void whenCreate_thenDepthIsCorrect() {

        final TraversalContext context = TraversalContext.create();

        assertEquals(0, context.depth());
    }

    @Test
    void givenDepth_whenWithIncrementedDepth_thenDepthIsCorrect() {

        final int depth = 1;

        final TraversalContext context = TraversalContext.withDepth(depth);
        final TraversalContext updatedContext = context.withIncrementedDepth();

        assertEquals(depth + 1, updatedContext.depth());
    }
}
