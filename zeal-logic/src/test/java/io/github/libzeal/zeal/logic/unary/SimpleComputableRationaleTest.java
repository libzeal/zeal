package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.unary.future.rationale.SimpleComputableRationale;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleComputableRationaleTest {

    private static final String ACTUAL = "foo";
    private static final String EXPECTED = "bar";
    private static final String HINT = "baz";

    @Test
    void givenNullExpected_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new SimpleComputableRationale<>(null, (s, p) -> ACTUAL, (s, p) -> HINT)
        );
    }

    @Test
    void givenNullActual_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new SimpleComputableRationale<>((s, p) -> EXPECTED, null, (s, p) -> HINT)
        );
    }

    @Test
    void givenNullHint_whenConstruct_thenExceptionNotThrown() {
        assertDoesNotThrow(
            () -> new SimpleComputableRationale<>((s, p) -> EXPECTED, (s, p) -> ACTUAL, null)
        );
    }

    @Test
    void givenExpectedActualAndHint_whenGenerate_thenAllFieldsCorrect() {

        final Object subject = new Object();
        final SimpleComputableRationale<Object> generator = new SimpleComputableRationale<>((s, p) -> EXPECTED,
            (s, p) -> ACTUAL, (s, p) -> HINT);
        final Rationale generated = generator.compute(subject, true);

        assertEquals(EXPECTED, generated.expected());
        assertEquals(ACTUAL, generated.actual());
        assertTrue(generated.hint().isPresent());
        assertEquals(HINT, generated.hint().get());
    }

    @Test
    void givenExpectedActualButNoHint_whenGenerate_thenAllFieldsCorrect() {

        final Object subject = new Object();
        final SimpleComputableRationale<Object> generator = new SimpleComputableRationale<>((s, p) -> EXPECTED,
            (s, p) -> ACTUAL, null);
        final Rationale generated = generator.compute(subject, true);

        assertEquals(EXPECTED, generated.expected());
        assertEquals(ACTUAL, generated.actual());
        assertFalse(generated.hint().isPresent());
    }
}
