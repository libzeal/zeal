package io.github.libzeal.zeal.values.primitive;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrimitiveArrayOperationsTest {

    private final PrimitiveArrayOperations<Integer, int[]> operations =
            new PrimitiveArrayOperations<>(array -> array.length, (array, index) -> array[index]);

    @Test
    void givenNullLength_whenConstruct_thenExceptionThrown() {
        assertThrows(
                NullPointerException.class,
                () -> new PrimitiveArrayOperations<Integer, int[]>(
                        null,
                        (array, index) -> array[index]
                )
        );
    }

    @Test
    void givenNullElementAt_whenConstruct_thenExceptionThrown() {
        assertThrows(
                NullPointerException.class,
                () -> new PrimitiveArrayOperations<Integer, int[]>(
                        array -> array.length,
                        null
                )
        );
    }

    @Test
    void givenArray_whenSize_thenLengthReturned() {
        assertEquals(0, operations.size(new int[]{}));
        assertEquals(3, operations.size(new int[]{1, 2, 3}));
    }

    @Test
    void givenArray_whenIncludes_thenOnlyPresentElementsIncluded() {

        final int[] array = {1, 2, 3};

        assertTrue(operations.includes(array, 2));
        assertFalse(operations.includes(array, 4));
        assertFalse(operations.includes(array, null));
    }

    @Test
    void givenDuplicates_whenOccurrences_thenAllCounted() {

        final int[] array = {1, 2, 1, 1};

        assertEquals(3, operations.occurrences(array, 1));
        assertEquals(0, operations.occurrences(array, 4));
        assertEquals(0, operations.occurrences(array, null));
    }

    @Test
    void givenDuplicates_whenIndexOf_thenFirstIndexReturned() {

        final int[] array = {1, 2, 1};

        assertEquals(0, operations.indexOf(array, 1));
        assertEquals(1, operations.indexOf(array, 2));
        assertEquals(-1, operations.indexOf(array, 4));
        assertEquals(-1, operations.indexOf(array, null));
    }

    @Test
    void givenDuplicates_whenLastIndexOf_thenLastIndexReturned() {

        final int[] array = {1, 2, 1};

        assertEquals(2, operations.lastIndexOf(array, 1));
        assertEquals(1, operations.lastIndexOf(array, 2));
        assertEquals(-1, operations.lastIndexOf(array, 4));
        assertEquals(-1, operations.lastIndexOf(array, null));
    }

    @Test
    void givenIndexInBounds_whenAtIndex_thenElementFound() {
        assertTrue(operations.atIndex(new int[]{1, 2}, 1, null).matches(2));
    }

    @Test
    void givenIndexOutOfBounds_whenAtIndex_thenMissing() {

        final int[] array = {1, 2};

        assertTrue(operations.atIndex(array, 2, null).isMissing());
        assertTrue(operations.atIndex(array, -1, null).isMissing());
    }

    @Test
    void givenNonEmpty_whenLastElement_thenLastElementFound() {
        assertTrue(operations.lastElement(new int[]{1, 2, 3}, null).matches(3));
    }

    @Test
    void givenEmpty_whenLastElement_thenMissing() {
        assertTrue(operations.lastElement(new int[]{}, null).isMissing());
    }

    @Test
    void givenMatches_whenFindAllSatisfying_thenMatchesReturnedInOrder() {
        assertEquals(List.of(2, 4), operations.findAllSatisfying(new int[]{1, 2, 3, 4}, i -> i % 2 == 0, 10));
    }

    @Test
    void givenMoreMatchesThanLimit_whenFindAllSatisfying_thenFirstMatchesUpToLimitReturned() {
        assertEquals(List.of(1, 2), operations.findAllSatisfying(new int[]{1, 2, 3, 4}, i -> true, 2));
    }

    @Test
    void givenNoMatches_whenFindAllSatisfying_thenEmptyListReturned() {
        assertEquals(List.of(), operations.findAllSatisfying(new int[]{1, 2}, i -> i > 5, 10));
    }
}
