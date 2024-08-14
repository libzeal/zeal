package io.github.libzeal.zeal.expression.lang.evaluation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResultTest {

    @Test
    void givenPassed_whenCallIsMethods_thenValuesCorrect() {

        Result result = Result.PASSED;

        assertTrue(result.isPassed());
        assertFalse(result.isFailed());
        assertFalse(result.isSkipped());
    }

    @Test
    void givenFailed_whenCallIsMethods_thenValuesCorrect() {

        Result result = Result.FAILED;

        assertFalse(result.isPassed());
        assertTrue(result.isFailed());
        assertFalse(result.isSkipped());
    }

    @Test
    void givenSkipped_whenCallIsMethods_thenValuesCorrect() {

        Result result = Result.SKIPPED;

        assertFalse(result.isPassed());
        assertFalse(result.isFailed());
        assertTrue(result.isSkipped());
    }
}
