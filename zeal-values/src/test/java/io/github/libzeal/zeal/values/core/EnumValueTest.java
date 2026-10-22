package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

@SuppressWarnings("java:S2187")
abstract class EnumValueTest<T extends Enum<T>> extends BaseValueTest<T, SimpleEnumValue<T>> {

    // ---- ordinalIs ----

    @Test
    void givenSameOrdinal_whenOrdinalIs_thenTrue() {
        final T a = nonNullValueA();
        newTestCase()
            .subject(a)
            .expression(value -> value.ordinalIs(a.ordinal()))
            .result(TRUE)
            .name("ordinalIs[" + a.ordinal() + "]")
            .expected(String.valueOf(a.ordinal()))
            .actual(String.valueOf(a.ordinal()))
            .run();
    }

    @Test
    void givenDifferentOrdinal_whenOrdinalIs_thenFalse() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.ordinalIs(a.ordinal()))
            .result(FALSE)
            .name("ordinalIs[" + a.ordinal() + "]")
            .expected(String.valueOf(a.ordinal()))
            .actual(String.valueOf(b.ordinal()))
            .run();
    }

    // ---- ordinalIsNot ----

    @Test
    void givenSameOrdinal_whenOrdinalIsNot_thenFalse() {
        final T a = nonNullValueA();
        newTestCase()
            .subject(a)
            .expression(value -> value.ordinalIsNot(a.ordinal()))
            .result(FALSE)
            .name("not[ordinalIs[" + a.ordinal() + "]]")
            .expected("not[" + a.ordinal() + "]")
            .actual(String.valueOf(a.ordinal()))
            .run();
    }

    @Test
    void givenDifferentOrdinal_whenOrdinalIsNot_thenTrue() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.ordinalIsNot(a.ordinal()))
            .result(TRUE)
            .name("not[ordinalIs[" + a.ordinal() + "]]")
            .expected("not[" + a.ordinal() + "]")
            .actual(String.valueOf(b.ordinal()))
            .run();
    }

    // ---- nameIs ----

    @Test
    void givenSameName_whenNameIs_thenTrue() {
        final T a = nonNullValueA();
        newTestCase()
            .subject(a)
            .expression(value -> value.nameIs(a.name()))
            .result(TRUE)
            .name("nameIs[" + a.name() + "]")
            .expected(a.name())
            .actual(a.name())
            .run();
    }

    @Test
    void givenDifferentName_whenNameIs_thenFalse() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.nameIs(a.name()))
            .result(FALSE)
            .name("nameIs[" + a.name() + "]")
            .expected(a.name())
            .actual(b.name())
            .run();
    }

    // ---- nameIsNot ----

    @Test
    void givenSameName_whenNameIsNot_thenFalse() {
        final T a = nonNullValueA();
        newTestCase()
            .subject(a)
            .expression(value -> value.nameIsNot(a.name()))
            .result(FALSE)
            .name("not[nameIs[" + a.name() + "]]")
            .expected("not[" + a.name() + "]")
            .actual(a.name())
            .run();
    }

    @Test
    void givenDifferentName_whenNameIsNot_thenTrue() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.nameIsNot(a.name()))
            .result(TRUE)
            .name("not[nameIs[" + a.name() + "]]")
            .expected("not[" + a.name() + "]")
            .actual(b.name())
            .run();
    }

    // ---- caseInsensitiveNameIs ----

    @Test
    void givenSameName_whenCaseInsensitiveNameIs_thenTrue() {
        final T a = nonNullValueA();
        newTestCase()
            .subject(a)
            .expression(value -> value.caseInsensitiveNameIs(a.name()))
            .result(TRUE)
            .name("caseInsensitiveNameIs[" + a.name() + "]")
            .expected("caseInsensitive[" + a.name() + "]")
            .actual(a.name())
            .run();
    }

    @Test
    void givenLowerCaseName_whenCaseInsensitiveNameIs_thenTrue() {
        final T a = nonNullValueA();
        newTestCase()
            .subject(a)
            .expression(value -> value.caseInsensitiveNameIs(a.name().toLowerCase()))
            .result(TRUE)
            .name("caseInsensitiveNameIs[" + a.name().toLowerCase() + "]")
            .expected("caseInsensitive[" + a.name().toLowerCase() + "]")
            .actual(a.name())
            .run();
    }

    @Test
    void givenDifferentName_whenCaseInsensitiveNameIs_thenFalse() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.caseInsensitiveNameIs(a.name()))
            .result(FALSE)
            .name("caseInsensitiveNameIs[" + a.name() + "]")
            .expected("caseInsensitive[" + a.name() + "]")
            .actual(b.name())
            .run();
    }

    // ---- caseInsensitiveNameIsNot ----

    @Test
    void givenSameName_whenCaseInsensitiveNameIsNot_thenFalse() {
        final T a = nonNullValueA();
        newTestCase()
            .subject(a)
            .expression(value -> value.caseInsensitiveNameIsNot(a.name()))
            .result(FALSE)
            .name("not[caseInsensitiveNameIs[" + a.name() + "]]")
            .expected("not[caseInsensitive[" + a.name() + "]]")
            .actual(a.name())
            .run();
    }

    @Test
    void givenLowerCaseName_whenCaseInsensitiveNameIsNot_thenFalse() {
        final T a = nonNullValueA();
        newTestCase()
            .subject(a)
            .expression(value -> value.caseInsensitiveNameIsNot(a.name().toLowerCase()))
            .result(FALSE)
            .name("not[caseInsensitiveNameIs[" + a.name().toLowerCase() + "]]")
            .expected("not[caseInsensitive[" + a.name().toLowerCase() + "]]")
            .actual(a.name())
            .run();
    }

    @Test
    void givenDifferentName_whenCaseInsensitiveNameIsNot_thenTrue() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.caseInsensitiveNameIsNot(a.name()))
            .result(TRUE)
            .name("not[caseInsensitiveNameIs[" + a.name() + "]]")
            .expected("not[caseInsensitive[" + a.name() + "]]")
            .actual(b.name())
            .run();
    }
}
