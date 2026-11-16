package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

@SuppressWarnings("java:S2187")
class SimpleObjectValueTest extends BaseValueTest<Object, SimpleObjectValue<Object>> {

    @Override
    protected SimpleObjectValue<Object> expression(Object value) {
        return new SimpleObjectValue<>(value);
    }

    @Override
    protected Object nonNullValueA() {
        return "foo";
    }

    @Override
    protected Object nonNullValueB() {
        return "bar";
    }

    // ---- equality of primitive and nested arrays ----

    @Test
    void givenPrimitiveArrayWithSameElements_whenIsEqualTo_thenTrue() {
        newTestCase()
            .subject(new int[]{1, 2})
            .expression(value -> value.isEqualTo(new int[]{1, 2}))
            .result(true)
            .name("isEqualTo[[1, 2]]")
            .expected("[1, 2]")
            .actual("[1, 2]")
            .run();
    }

    @Test
    void givenPrimitiveArrayWithDifferentElements_whenIsEqualTo_thenFalse() {
        newTestCase()
            .subject(new int[]{1, 2})
            .expression(value -> value.isEqualTo(new int[]{2, 1}))
            .result(false)
            .name("isEqualTo[[2, 1]]")
            .expected("[2, 1]")
            .actual("[1, 2]")
            .run();
    }

    @Test
    void givenPrimitiveArraysOfDifferentTypes_whenIsEqualTo_thenFalse() {
        newTestCase()
            .subject(new int[]{1, 2})
            .expression(value -> value.isEqualTo(new long[]{1, 2}))
            .result(false)
            .name("isEqualTo[[1, 2]]")
            .expected("[1, 2]")
            .actual("[1, 2]")
            .run();
    }

    @Test
    void givenNestedArrayWithSameElements_whenIsEqualTo_thenTrue() {
        newTestCase()
            .subject(new int[][]{{1}, {2, 3}})
            .expression(value -> value.isEqualTo(new int[][]{{1}, {2, 3}}))
            .result(true)
            .name("isEqualTo[[[1], [2, 3]]]")
            .expected("[[1], [2, 3]]")
            .actual("[[1], [2, 3]]")
            .run();
    }

    @Test
    void givenPrimitiveArrayWithSameElements_whenIsNotEqualTo_thenFalse() {
        newTestCase()
            .subject(new int[]{1, 2})
            .expression(value -> value.isNotEqualTo(new int[]{1, 2}))
            .result(false)
            .name("isNotEqualTo[[1, 2]]")
            .expected("not[[1, 2]]")
            .actual("[1, 2]")
            .run();
    }

    @Test
    void givenArraySubject_whenToStringIs_thenActualIsSubjectToString() {
        final int[] subject = {1, 2};
        newTestCase()
            .subject(subject)
            .expression(value -> value.toStringIs("[1, 2]"))
            .result(false)
            .name("toString().equals([1, 2])")
            .expected("[1, 2]")
            .actual(subject.toString())
            .run();
    }
}
