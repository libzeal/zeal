package io.github.libzeal.zeal.expression.lang.evaluation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResultTest {

    @Test
    void givenPassed_whenCallIsMethods_thenValuesCorrect() {

        Result result = Result.TRUE;

        assertTrue(result.isTrue());
        assertFalse(result.isFalse());
        assertFalse(result.isSkipped());
    }

    @Test
    void givenFailed_whenCallIsMethods_thenValuesCorrect() {

        Result result = Result.FALSE;

        assertFalse(result.isTrue());
        assertTrue(result.isFalse());
        assertFalse(result.isSkipped());
    }

    @Test
    void givenSkipped_whenCallIsMethods_thenValuesCorrect() {

        Result result = Result.SKIPPED;

        assertFalse(result.isTrue());
        assertFalse(result.isFalse());
        assertTrue(result.isSkipped());
    }
}
