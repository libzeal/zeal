package io.github.libzeal.zeal.logic.format;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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

    @Test
    void givenEmptyCollection_whenStringifyAll_thenEmptyListReturned() {
        assertEquals(List.of(), ObjectFormatter.stringifyAll(List.of()));
    }

    @Test
    void givenMixedCollection_whenStringifyAll_thenEachElementStringifiedInOrder() {
        assertEquals(
                List.of("foo", "(null)", "1"),
                ObjectFormatter.stringifyAll(Arrays.asList("foo", null, 1))
        );
    }
}
