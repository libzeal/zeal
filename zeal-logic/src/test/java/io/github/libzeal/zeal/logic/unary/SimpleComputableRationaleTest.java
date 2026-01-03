package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.unary.future.rationale.ComputableRationaleContext;
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
            () -> new SimpleComputableRationale<>(null, context -> ACTUAL, context -> HINT)
        );
    }

    @Test
    void givenNullActual_whenConstruct_thenExceptionThrown() {
        assertThrows(
            NullPointerException.class,
            () -> new SimpleComputableRationale<>(context -> EXPECTED, null, context -> HINT)
        );
    }

    @Test
    void givenNullHint_whenConstruct_thenExceptionNotThrown() {
        assertDoesNotThrow(
            () -> new SimpleComputableRationale<>(context -> EXPECTED, context -> ACTUAL, null)
        );
    }

    @Test
    void givenExpectedActualAndHint_whenGenerate_thenAllFieldsCorrect() {

        final Object subject = new Object();
        final SimpleComputableRationale<Object> generator = new SimpleComputableRationale<>(context -> EXPECTED,
            context -> ACTUAL, context -> HINT);
        final Rationale generated = generator.compute(new ComputableRationaleContext<>(subject, true));

        assertEquals(EXPECTED, generated.expected());
        assertEquals(ACTUAL, generated.actual());
        assertTrue(generated.hint().isPresent());
        assertEquals(HINT, generated.hint().get());
    }

    @Test
    void givenExpectedActualButNoHint_whenGenerate_thenAllFieldsCorrect() {

        final Object subject = new Object();
        final SimpleComputableRationale<Object> generator = new SimpleComputableRationale<>(context -> EXPECTED,
            context -> ACTUAL, null);
        final Rationale generated = generator.compute(new ComputableRationaleContext<>(subject, true));

        assertEquals(EXPECTED, generated.expected());
        assertEquals(ACTUAL, generated.actual());
        assertFalse(generated.hint().isPresent());
    }
}
