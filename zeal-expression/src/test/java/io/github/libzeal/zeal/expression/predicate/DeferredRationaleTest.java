package io.github.libzeal.zeal.expression.predicate;

import io.github.libzeal.zeal.expression.evaluation.Rationale;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeferredRationaleTest {

    private static final ValueSupplier<Object> EMPTY = s -> "";

    @Test
    void givenNullSubject_whenConstruct_thenExceptionNotThrown() {
        assertDoesNotThrow(
            () -> new DeferredRationale<>(null, EMPTY, EMPTY, EMPTY)
        );
    }

    @Test
    void givenNullExpected_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new DeferredRationale<>("foo",  null, EMPTY, EMPTY)
        );
    }

    @Test
    void givenNullActual_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new DeferredRationale<>("foo", EMPTY, null, EMPTY)
        );
    }

    @Test
    void givenNullHint_whenConstruct_thenExceptionThrown() {
        assertDoesNotThrow(
            () -> new DeferredRationale<>("foo", EMPTY, EMPTY, null)
        );
    }

    @Test
    void givenNullSubject_whenConstructWithoutHint_thenExceptionNotThrown() {
        assertDoesNotThrow(
            () -> new DeferredRationale<>(null, EMPTY, EMPTY)
        );
    }

    @Test
    void givenNullExpected_whenConstructWithoutHint_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new DeferredRationale<>("foo",  null, EMPTY)
        );
    }

    @Test
    void givenNullActual_whenConstructWithoutHint_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new DeferredRationale<>("foo", EMPTY, null)
        );
    }

    @Test
    void givenNoHint_whenGetValues_thenCorrectValuesReturned() {

        final String expected = "foo";
        final String actual = "bar";
        final Rationale rationale = new DeferredRationale<>("foo", s -> expected, s -> actual);

        assertEquals(expected, rationale.expected());
        assertEquals(actual, rationale.actual());
        assertFalse(rationale.hint().isPresent());
    }

    @Test
    void givenHint_whenGetValues_thenCorrectValuesReturned() {

        final String expected = "foo";
        final String actual = "bar";
        final String hint = "baz";
        final Rationale rationale = new DeferredRationale<>("foo", s -> expected, s -> actual, s -> hint);

        assertEquals(expected, rationale.expected());
        assertEquals(actual, rationale.actual());
        assertTrue(rationale.hint().isPresent());
        assertEquals(hint, rationale.hint().get());
    }
}
