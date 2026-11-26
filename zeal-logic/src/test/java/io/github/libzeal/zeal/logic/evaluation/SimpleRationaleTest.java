package io.github.libzeal.zeal.logic.evaluation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleRationaleTest {

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
        final String hint = "baz";

        Rationale rationale = new SimpleRationale(expected, actual, hint);

        assertEquals(expected, rationale.expected());
        assertEquals(actual, rationale.actual());
        assertTrue(rationale.hint().isPresent());
        assertEquals(hint, rationale.hint().get());
    }

    @Test
    void whenEmpty_thenFieldsAreAllBlank() {

        final Rationale rationale = SimpleRationale.empty();

        assertEquals("", rationale.expected());
        assertEquals("", rationale.actual());
        assertFalse(rationale.hint().isPresent());
    }

    @Test
    void givenSameExpectedActualAndHint_whenEquals_thenEquals() {

        final String expected = "foo";
        final String actual = "bar";
        final String hint = "baz";

        Rationale a = new SimpleRationale(expected, actual, hint);
        Rationale b = new SimpleRationale(expected, actual, hint);

        assertEquals(a, b);
    }

    @Test
    void givenSameObject_whenEquals_thenEquals() {

        final String expected = "foo";
        final String actual = "bar";
        final String hint = "baz";

        Rationale a = new SimpleRationale(expected, actual, hint);

        assertEquals(a, a);
    }

    @Test
    void givenDifferentTypes_whenEquals_thenNotEquals() {

        final String expected = "foo";
        final String actual = "bar";
        final String hint = "baz";

        Rationale a = new SimpleRationale(expected, actual, hint);

        assertNotEquals(a, new Object());
    }

    @Test
    void givenDifferentExpected_whenEquals_thenNotEquals() {

        final String expected1 = "foo1";
        final String expected2 = "foo2";
        final String actual = "bar";
        final String hint = "baz";

        Rationale a = new SimpleRationale(expected1, actual, hint);
        Rationale b = new SimpleRationale(expected2, actual, hint);

        assertNotEquals(a, b);
    }

    @Test
    void givenDifferentActual_whenEquals_thenNotEquals() {

        final String expected = "foo";
        final String actual1 = "bar1";
        final String actual2 = "bar2";
        final String hint = "baz";

        Rationale a = new SimpleRationale(expected, actual1, hint);
        Rationale b = new SimpleRationale(expected, actual2, hint);

        assertNotEquals(a, b);
    }

    @Test
    void givenDifferentHint_whenEquals_thenNotEquals() {

        final String expected = "foo";
        final String actual = "bar";
        final String hint1 = "baz1";
        final String hint2 = "baz2";

        Rationale a = new SimpleRationale(expected, actual, hint1);
        Rationale b = new SimpleRationale(expected, actual, hint2);

        assertNotEquals(a, b);
    }

    @Test
    void givenSameExpectedActualAndHint_whenHashCode_thenEquals() {

        final String expected = "foo";
        final String actual = "bar";
        final String hint = "baz";

        Rationale a = new SimpleRationale(expected, actual, hint);
        Rationale b = new SimpleRationale(expected, actual, hint);

        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void givenSameObject_whenHashCode_thenEquals() {

        final String expected = "foo";
        final String actual = "bar";
        final String hint = "baz";

        Rationale a = new SimpleRationale(expected, actual, hint);

        assertEquals(a.hashCode(), a.hashCode());
    }

    @Test
    void givenDifferentExpected_whenHashCode_thenNotEquals() {

        final String expected1 = "foo1";
        final String expected2 = "foo2";
        final String actual = "bar";
        final String hint = "baz";

        Rationale a = new SimpleRationale(expected1, actual, hint);
        Rationale b = new SimpleRationale(expected2, actual, hint);

        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void givenDifferentActual_whenHashCode_thenNotEquals() {

        final String expected = "foo";
        final String actual1 = "bar1";
        final String actual2 = "bar2";
        final String hint = "baz";

        Rationale a = new SimpleRationale(expected, actual1, hint);
        Rationale b = new SimpleRationale(expected, actual2, hint);

        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void givenDifferentHint_whenHashCode_thenNotEquals() {

        final String expected = "foo";
        final String actual = "bar";
        final String hint1 = "baz1";
        final String hint2 = "baz2";

        Rationale a = new SimpleRationale(expected, actual, hint1);
        Rationale b = new SimpleRationale(expected, actual, hint2);

        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void givenNoNegatedExpected_whenNegatedExpected_thenExpectedWrappedInNot() {

        final Rationale rationale = new SimpleRationale("foo", "bar", "baz");

        assertEquals("not[foo]", rationale.negatedExpected());
    }

    @Test
    void givenHintAndNoNegatedHint_whenNegatedHint_thenHintPrefixedWithNegated() {

        final Rationale rationale = new SimpleRationale("foo", "bar", "baz");

        assertEquals("Negated: baz", rationale.negatedHint().orElseThrow());
    }

    @Test
    void givenNoHintAndNoNegatedHint_whenNegatedHint_thenEmpty() {

        final Rationale rationale = new SimpleRationale("foo", "bar");

        assertTrue(rationale.negatedHint().isEmpty());
    }

    @Test
    void givenNegatedExpectedAndNegatedHint_whenConstruct_thenNegatedFieldsUsed() {

        final Rationale rationale = new SimpleRationale("foo", "notFoo", "bar", "baz", "notBaz");

        assertEquals("foo", rationale.expected());
        assertEquals("bar", rationale.actual());
        assertEquals("baz", rationale.hint().orElseThrow());
        assertEquals("notFoo", rationale.negatedExpected());
        assertEquals("notBaz", rationale.negatedHint().orElseThrow());
    }

    @Test
    void givenNegatedHintAndNoHint_whenNegatedHint_thenNegatedHintUsed() {

        final Rationale rationale = new SimpleRationale("foo", null, "bar", null, "notBaz");

        assertTrue(rationale.hint().isEmpty());
        assertEquals("notBaz", rationale.negatedHint().orElseThrow());
    }

    @Test
    void givenSameNegatedFields_whenEquals_thenEqual() {

        final Rationale a = new SimpleRationale("foo", "notFoo", "bar", "baz", "notBaz");
        final Rationale b = new SimpleRationale("foo", "notFoo", "bar", "baz", "notBaz");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void givenDifferentNegatedExpected_whenEquals_thenNotEqual() {

        final Rationale a = new SimpleRationale("foo", "notFoo1", "bar", "baz", "notBaz");
        final Rationale b = new SimpleRationale("foo", "notFoo2", "bar", "baz", "notBaz");

        assertNotEquals(a, b);
    }

    @Test
    void givenDifferentNegatedHint_whenEquals_thenNotEqual() {

        final Rationale a = new SimpleRationale("foo", "notFoo", "bar", "baz", "notBaz1");
        final Rationale b = new SimpleRationale("foo", "notFoo", "bar", "baz", "notBaz2");

        assertNotEquals(a, b);
    }
}
