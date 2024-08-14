package io.github.libzeal.zeal.expression.lang.rationale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleRationaleGeneratorTest {

    private static final String ACTUAL = "foo";
    private static final String EXPECTED = "bar";
    private static final String HINT = "baz";

    @Test
    void givenNullExpected_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new SimpleRationaleGenerator<>(null, (s, p) -> ACTUAL, (s, p) -> HINT)
        );
    }

    @Test
    void givenNullActual_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new SimpleRationaleGenerator<>((s, p) -> EXPECTED, null, (s, p) -> HINT)
        );
    }

    @Test
    void givenNullHint_whenConstruct_thenExceptionNotThrown() {
        assertDoesNotThrow(
            () -> new SimpleRationaleGenerator<>((s, p) -> EXPECTED, (s, p) -> ACTUAL, null)
        );
    }

    @Test
    void givenExpectedActualAndHint_whenGenerate_thenAllFieldsCorrect() {

        final Object subject = new Object();
        final SimpleRationaleGenerator<Object> generator = new SimpleRationaleGenerator<>((s, p) -> EXPECTED,
            (s, p) -> ACTUAL, (s, p) -> HINT);
        final Rationale generated = generator.generate(subject, true);

        assertEquals(EXPECTED, generated.expected());
        assertEquals(ACTUAL, generated.actual());
        assertTrue(generated.hint().isPresent());
        assertEquals(HINT, generated.hint().get());
    }

    @Test
    void givenExpectedActualButNoHint_whenGenerate_thenAllFieldsCorrect() {

        final Object subject = new Object();
        final SimpleRationaleGenerator<Object> generator = new SimpleRationaleGenerator<>((s, p) -> EXPECTED,
            (s, p) -> ACTUAL, null);
        final Rationale generated = generator.generate(subject, true);

        assertEquals(EXPECTED, generated.expected());
        assertEquals(ACTUAL, generated.actual());
        assertFalse(generated.hint().isPresent());
    }
}
