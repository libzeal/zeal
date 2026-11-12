package io.github.libzeal.zeal.values.sdk.sequence;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BaseSequenceValueTest {

    @Test
    void givenNullOperations_whenConstruct_thenExceptionThrown() {
        assertThrows(NullPointerException.class, () -> new TestSequenceValue(List.of("a"), "Test", null));
    }

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {
        assertThrows(NullPointerException.class, () -> new TestSequenceValue(List.of("a"), null, new TestOperations()));
    }

    @Test
    void givenNullSubject_whenConstruct_thenNoExceptionThrown() {
        assertDoesNotThrow(() -> new TestSequenceValue(null, "Test", new TestOperations()));
    }

    @Test
    void givenNonNullArguments_whenConstruct_thenNoExceptionThrown() {
        assertDoesNotThrow(() -> new TestSequenceValue(List.of("a"), "Test", new TestOperations()));
    }

    private static final class TestSequenceValue extends BaseSequenceValue<List<String>, String, TestSequenceValue> {

        private TestSequenceValue(final List<String> subject, final String name,
                                  final SequenceOperations<String, List<String>> operations) {
            super(subject, name, operations);
        }
    }

    // Construction never uses the operations, so the operations do not need to be functional
    private static final class TestOperations implements SequenceOperations<String, List<String>> {

        @Override
        public int size(final List<String> haystack) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean includes(final List<String> haystack, final String needle) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int occurrences(final List<String> haystack, final String needle) {
            throw new UnsupportedOperationException();
        }
    }
}
