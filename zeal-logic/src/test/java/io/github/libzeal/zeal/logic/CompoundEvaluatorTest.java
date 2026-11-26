package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.CompoundEvaluator.Tally;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.logic.CompoundEvaluator.formatActual;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CompoundEvaluatorTest {

    @Test
    void givenNoSkippedChildren_whenFormatActual_thenSkippedCountOmitted() {
        assertEquals("1 passed, 2 failed", formatActual(new Tally(1, 2, 0)));
    }

    @Test
    void givenSkippedChildren_whenFormatActual_thenSkippedCountIncluded() {
        assertEquals("1 passed, 1 failed, 3 skipped", formatActual(new Tally(1, 1, 3)));
    }

    @Test
    void givenNoChildren_whenFormatActual_thenZeroCountsShown() {
        assertEquals("0 passed, 0 failed", formatActual(new Tally(0, 0, 0)));
    }
}
