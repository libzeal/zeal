package io.github.libzeal.zeal.logic.evaluation.format.simple;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.test.TestExpressions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleFormatterContextTest {

    private Evaluation rootCause;
    private SimpleFormatterContext context;

    @BeforeEach
    void setUp() {

        rootCause = TestExpressions.evaluation("foo", Result.FALSE);

        context = new SimpleFormatterContext(List.of(rootCause), 0);
    }

    @Test
    void givenNullRootCauses_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new SimpleFormatterContext(null, 0)
        );
    }

    @Test
    void givenNegativeDepth_whenConstruct_thenExceptionThrown() {

        final List<Evaluation> rootCauses = List.of(rootCause);

        assertThrows(
            IllegalArgumentException.class,
            () -> new SimpleFormatterContext(rootCauses, -1)
        );
    }

    @Test
    void givenNullEvaluation_whenIsRootCause_thenFalse() {
        assertFalse(context.isRootCause(null));
    }

    @Test
    void givenNonMatchingEvaluation_whenIsRootCause_thenFalse() {
        assertFalse(context.isRootCause(TestExpressions.evaluation("bar", Result.FALSE)));
    }

    @Test
    void givenEqualButDistinctEvaluation_whenIsRootCause_thenFalse() {

        final Evaluation equalEvaluation = TestExpressions.evaluation("foo", Result.FALSE);

        assertEquals(rootCause, equalEvaluation);
        assertFalse(context.isRootCause(equalEvaluation));
    }

    @Test
    void givenMatchingEvaluation_whenIsRootCause_thenTrue() {
        assertTrue(context.isRootCause(rootCause));
    }

    @Test
    void givenMultipleRootCauses_whenIsRootCause_thenEachIsRootCause() {

        final Evaluation otherRootCause = TestExpressions.evaluation("bar", Result.FALSE);
        final SimpleFormatterContext multipleContext = new SimpleFormatterContext(List.of(rootCause, otherRootCause),
            0);

        assertTrue(multipleContext.isRootCause(rootCause));
        assertTrue(multipleContext.isRootCause(otherRootCause));
    }

    @Test
    void givenValidDepth_whenDepth_thenCorrectDepthReturned() {

        final int depth = 7;

        final SimpleFormatterContext validContext = new SimpleFormatterContext(List.of(rootCause), depth);

        assertEquals(depth, validContext.depth());
    }
}
