package io.github.libzeal.zeal.logic.evaluation.format.simple;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SimpleFormatterContextTest {

    private Evaluation evaluation;
    private Cause cause;
    private SimpleFormatterContext context;

    @BeforeEach
    void setUp() {

        evaluation = mock(Evaluation.class);
        cause = new Cause(evaluation);

        context = new SimpleFormatterContext(cause, 0);
    }

    @Test
    void givenNullCause_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new SimpleFormatterContext(null, 0)
        );
    }

    @Test
    void givenNegativeDepth_whenConstruct_thenExceptionThrown() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new SimpleFormatterContext(cause, -1)
        );
    }

    @Test
    void givenNullEvaluation_whenIsRootCause_thenFalse() {
        assertFalse(context.isRootCause(null));
    }

    @Test
    void givenNonMatchingEvaluation_whenIsRootCause_thenFalse() {
        assertFalse(context.isRootCause(mock(Evaluation.class)));
    }

    @Test
    void givenMatchingEvaluation_whenIsRootCause_thenTrue() {
        assertTrue(context.isRootCause(evaluation));
    }

    @Test
    void givenValidDepth_whenDepth_thenCorrectDepthReturned() {

        final int depth = 7;

        final SimpleFormatterContext validContext = new SimpleFormatterContext(cause, depth);

        assertEquals(depth, validContext.depth());
    }
}
