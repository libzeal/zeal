package io.github.libzeal.zeal.values.sdk.sequence;

import io.github.libzeal.zeal.logic.unary.Observed;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BaseOrderedSequenceValueTest {

    @Test
    void givenNullOperations_whenConstruct_thenExceptionThrown() {
        assertThrows(NullPointerException.class, () -> new TestOrderedSequenceValue(List.of("a"), "Test", null));
    }

    @Test
    void givenNullName_whenConstruct_thenExceptionThrown() {
        assertThrows(
                NullPointerException.class,
                () -> new TestOrderedSequenceValue(List.of("a"), null, new TestOperations())
        );
    }

    @Test
    void givenNullSubject_whenConstruct_thenNoExceptionThrown() {
        assertDoesNotThrow(() -> new TestOrderedSequenceValue(null, "Test", new TestOperations()));
    }

    @Test
    void givenNonNullArguments_whenConstruct_thenNoExceptionThrown() {
        assertDoesNotThrow(() -> new TestOrderedSequenceValue(List.of("a"), "Test", new TestOperations()));
    }

    private static final class TestOrderedSequenceValue
            extends BaseOrderedSequenceValue<List<String>, String, TestOrderedSequenceValue> {

        private TestOrderedSequenceValue(
                final List<String> subject, final String name,
                final OrderedSequenceOperations<String, List<String>> operations
        ) {
            super(subject, name, operations);
        }
    }

    // Construction never uses the operations, so the operations do not need to be functional
    @NullMarked
    private static final class TestOperations implements OrderedSequenceOperations<String, List<String>> {

        @Override
        public int size(final List<String> haystack) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean includes(final List<String> haystack, final @Nullable String needle) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int occurrences(final List<String> haystack, final @Nullable String needle) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<String> findAllSatisfying(final List<String> haystack, final Predicate<? super String> predicate, final int limit) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Observed<String> lastElement(final List<String> haystack, final @Nullable String desired) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Observed<String> atIndex(final List<String> haystack, final int index, final @Nullable String desired) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(final List<String> haystack, final @Nullable String needle) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int lastIndexOf(final List<String> haystack, final @Nullable String needle) {
            throw new UnsupportedOperationException();
        }
    }
}
