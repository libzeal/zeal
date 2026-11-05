package io.github.libzeal.zeal.logic.format;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EvaluationFormatterTest {

    @Test
    void givenNull_whenStringify_thenNullStringReturned() {
        assertEquals("(null)", ObjectFormatter.stringify(null));
    }

    @Test
    void givenNonNull_whenStringify_thenToStringReturned() {
        assertEquals("1", ObjectFormatter.stringify(1));
    }
}
