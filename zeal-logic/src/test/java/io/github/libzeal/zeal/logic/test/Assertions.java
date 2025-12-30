package io.github.libzeal.zeal.logic.test;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.evaluation.traverse.TraversalContext;
import io.github.libzeal.zeal.logic.evaluation.traverse.DepthFirstTraverser;
import io.github.libzeal.zeal.logic.evaluation.traverse.TraverserAction;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class Assertions {

    public static void assertRationaleEquals(final Rationale rationale, final String expected, final String actual) {
        assertEquals(expected, rationale.expected());
        assertEquals(actual, rationale.actual());
        assertFalse(rationale.hint().isPresent());
    }

    public static void assertRationaleEquals(final Rationale rationale, final String expected, final String actual,
                                              final String hint) {

        final Optional<String> actualHint = rationale.hint();

        assertEquals(expected, rationale.expected());
        assertEquals(actual, rationale.actual());
        assertTrue(actualHint.isPresent());
        assertEquals(hint, actualHint.get());
    }

    public static void assertDepthFirstTraversalIsTerminal(final Evaluation evaluation) {
        assertTrue(
            evaluation instanceof TerminalEvaluation,
            "Expected evaluation to be terminal. Was: " + evaluation.getClass()
        );
    }

    public static void assertRationaleIsSkipped(final Rationale rationale) {

        final Rationale expectedSkippedRationale = SimpleRationale.skipped();
        final String expected = expectedSkippedRationale.expected();
        final String actual = expectedSkippedRationale.actual();
        final Optional<String> hint = expectedSkippedRationale.hint();

        if (hint.isPresent()) {
            assertRationaleEquals(rationale, expected, actual, hint.get());
        }
        else {
            assertRationaleEquals(rationale, expected, actual);
        }
    }
}
