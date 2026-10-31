package io.github.libzeal.zeal.logic.test;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.rationale.Rationale;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
}
