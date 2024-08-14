package io.github.libzeal.zeal.expression.lang.rationale;

import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.expression.lang.rationale.SimpleRationale.SKIPPED_VALUE;
import static org.junit.jupiter.api.Assertions.*;

class SimpleRationaleTest {

    @Test
    void whenEmpty_thenFieldsAreAllBlank() {

        final Rationale rationale = SimpleRationale.empty();

        assertEquals("", rationale.expected());
        assertEquals("", rationale.actual());
        assertFalse(rationale.hint().isPresent());
    }

    @Test
    void whenSkipped_thenFieldsAreCorrect() {

        final Rationale rationale = SimpleRationale.skipped();

        assertEquals(SKIPPED_VALUE, rationale.expected());
        assertEquals(SKIPPED_VALUE, rationale.actual());
        assertFalse(rationale.hint().isPresent());
    }

    @Test
    void givenExpectedAndActualValues_whenConstruct_thenFieldsAreAllCorrect() {

        final String expected = "foo";
        final String actual = "bar";

        Rationale rationale = new SimpleRationale(expected, actual);

        assertEquals(expected, rationale.expected());
        assertEquals(actual, rationale.actual());
        assertFalse(rationale.hint().isPresent());
    }

    @Test
    void givenExpectedActualAndHint_whenConstruct_thenFieldsAreAllCorrect() {

        final String expected = "foo";
        final String actual = "bar";
        final Hint hint = Hint.symmetrical("baz");

        Rationale rationale = new SimpleRationale(expected, actual, hint);

        assertEquals(expected, rationale.expected());
        assertEquals(actual, rationale.actual());
        assertTrue(rationale.hint().isPresent());
        assertEquals(hint, rationale.hint().get());
    }

    @Test
    void givenSameExpectedActualAndHint_whenEquals_thenEquals() {

        final String expected = "foo";
        final String actual = "bar";
        final Hint hint = Hint.symmetrical("baz");

        Rationale a = new SimpleRationale(expected, actual, hint);
        Rationale b = new SimpleRationale(expected, actual, hint);

        assertEquals(a, b);
    }

    @Test
    void givenSameObject_whenEquals_thenEquals() {

        final String expected = "foo";
        final String actual = "bar";
        final Hint hint = Hint.symmetrical("baz");

        Rationale a = new SimpleRationale(expected, actual, hint);

        assertEquals(a, a);
    }

    @Test
    void givenDifferentTypes_whenEquals_thenNotEquals() {

        final String expected = "foo";
        final String actual = "bar";
        final Hint hint = Hint.symmetrical("baz");

        Rationale a = new SimpleRationale(expected, actual, hint);

        assertNotEquals(a, new Object());
    }

    @Test
    void givenDifferentExpected_whenEquals_thenNotEquals() {

        final String expected1 = "foo1";
        final String expected2 = "foo2";
        final String actual = "bar";
        final Hint hint = Hint.symmetrical("baz");

        Rationale a = new SimpleRationale(expected1, actual, hint);
        Rationale b = new SimpleRationale(expected2, actual, hint);

        assertNotEquals(a, b);
    }

    @Test
    void givenDifferentActual_whenEquals_thenNotEquals() {

        final String expected = "foo";
        final String actual1 = "bar1";
        final String actual2 = "bar2";
        final Hint hint = Hint.symmetrical("baz");

        Rationale a = new SimpleRationale(expected, actual1, hint);
        Rationale b = new SimpleRationale(expected, actual2, hint);

        assertNotEquals(a, b);
    }

    @Test
    void givenDifferentHint_whenEquals_thenNotEquals() {

        final String expected = "foo";
        final String actual = "bar";
        final Hint hint1 = Hint.symmetrical("baz1");
        final Hint hint2 = Hint.symmetrical("baz2");

        Rationale a = new SimpleRationale(expected, actual, hint1);
        Rationale b = new SimpleRationale(expected, actual, hint2);

        assertNotEquals(a, b);
    }

    @Test
    void givenSameExpectedActualAndHint_whenHashCode_thenEquals() {

        final String expected = "foo";
        final String actual = "bar";
        final Hint hint = Hint.symmetrical("baz");

        Rationale a = new SimpleRationale(expected, actual, hint);
        Rationale b = new SimpleRationale(expected, actual, hint);

        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void givenSameObject_whenHashCode_thenEquals() {

        final String expected = "foo";
        final String actual = "bar";
        final Hint hint = Hint.symmetrical("baz");

        Rationale a = new SimpleRationale(expected, actual, hint);

        assertEquals(a.hashCode(), a.hashCode());
    }

    @Test
    void givenDifferentExpected_whenHashCode_thenNotEquals() {

        final String expected1 = "foo1";
        final String expected2 = "foo2";
        final String actual = "bar";
        final Hint hint = Hint.symmetrical("baz");

        Rationale a = new SimpleRationale(expected1, actual, hint);
        Rationale b = new SimpleRationale(expected2, actual, hint);

        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void givenDifferentActual_whenHashCode_thenNotEquals() {

        final String expected = "foo";
        final String actual1 = "bar1";
        final String actual2 = "bar2";
        final Hint hint = Hint.symmetrical("baz");

        Rationale a = new SimpleRationale(expected, actual1, hint);
        Rationale b = new SimpleRationale(expected, actual2, hint);

        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void givenDifferentHint_whenHashCode_thenNotEquals() {

        final String expected = "foo";
        final String actual = "bar";
        final Hint hint1 = Hint.symmetrical("baz1");
        final Hint hint2 = Hint.symmetrical("baz2");

        Rationale a = new SimpleRationale(expected, actual, hint1);
        Rationale b = new SimpleRationale(expected, actual, hint2);

        assertNotEquals(a.hashCode(), b.hashCode());
    }
}
