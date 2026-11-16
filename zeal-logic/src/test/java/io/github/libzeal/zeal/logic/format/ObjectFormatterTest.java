package io.github.libzeal.zeal.logic.format;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectFormatterTest {

    @Test
    void givenNull_whenStringify_thenNullPlaceholderReturned() {
        assertEquals("(null)", ObjectFormatter.stringify(null));
    }

    @Test
    void givenObject_whenStringify_thenToStringReturned() {
        assertEquals("foo", ObjectFormatter.stringify("foo"));
    }

    @Test
    void givenObjectArray_whenStringify_thenElementsReturned() {
        assertEquals("[foo, null]", ObjectFormatter.stringify(new String[]{"foo", null}));
    }

    @Test
    void givenEmptyArray_whenStringify_thenEmptyBracketsReturned() {
        assertEquals("[]", ObjectFormatter.stringify(new String[0]));
    }

    @Test
    void givenPrimitiveArray_whenStringify_thenElementsReturned() {
        assertEquals("[1, 2]", ObjectFormatter.stringify(new int[]{1, 2}));
        assertEquals("[true, false]", ObjectFormatter.stringify(new boolean[]{true, false}));
        assertEquals("[a, b]", ObjectFormatter.stringify(new char[]{'a', 'b'}));
    }

    @Test
    void givenNestedArray_whenStringify_thenNestedElementsReturned() {
        assertEquals("[[1], [2, 3]]", ObjectFormatter.stringify(new int[][]{{1}, {2, 3}}));
        assertEquals("[[a], [b]]", ObjectFormatter.stringify(new Object[]{new String[]{"a"}, new String[]{"b"}}));
    }
}
