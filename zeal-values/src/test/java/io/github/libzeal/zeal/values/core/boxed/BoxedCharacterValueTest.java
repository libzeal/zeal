package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

public class BoxedCharacterValueTest extends BaseValueTest<Character,
        BoxedCharacterValue> {

    @Override
    protected BoxedCharacterValue expression(final Character value) {
        return new BoxedCharacterValue(value);
    }

    @Override
    protected Character nonNullValueA() {
        return 'a';
    }

    @Override
    protected Character nonNullValueB() {
        return 'b';
    }

    // ---- isLowerCase ----

    @Test
    void givenLowerCase_whenIsLowerCase_thenTrue() {
        newTestCase()
            .subject('c')
            .expression(BoxedCharacterValue::isLowerCase)
            .result(TRUE)
            .name("isLowerCase")
            .expected("isLowerCase")
            .actual("c")
            .run();
    }

    @Test
    void givenUpperCase_whenIsLowerCase_thenFalse() {
        newTestCase()
            .subject('C')
            .expression(BoxedCharacterValue::isLowerCase)
            .result(FALSE)
            .name("isLowerCase")
            .expected("isLowerCase")
            .actual("C")
            .run();
    }

    // ---- isUpperCase ----

    @Test
    void givenLowerCase_whenIsUpperCase_thenFalse() {
        newTestCase()
            .subject('c')
            .expression(BoxedCharacterValue::isUpperCase)
            .result(FALSE)
            .name("isUpperCase")
            .expected("isUpperCase")
            .actual("c")
            .run();
    }

    @Test
    void givenUpperCase_whenIsUpperCase_thenTrue() {
        newTestCase()
            .subject('C')
            .expression(BoxedCharacterValue::isUpperCase)
            .result(TRUE)
            .name("isUpperCase")
            .expected("isUpperCase")
            .actual("C")
            .run();
    }

    // ---- isLetter ----

    @Test
    void givenDigit_whenIsLetter_thenFalse() {
        newTestCase()
            .subject('1')
            .expression(BoxedCharacterValue::isLetter)
            .result(FALSE)
            .name("isLetter")
            .expected("isLetter")
            .actual("1")
            .run();
    }

    @Test
    void givenLetter_whenIsLetter_thenTrue() {
        newTestCase()
            .subject('C')
            .expression(BoxedCharacterValue::isLetter)
            .result(TRUE)
            .name("isLetter")
            .expected("isLetter")
            .actual("C")
            .run();
    }

    // ---- isNotLetter ----

    @Test
    void givenDigit_whenIsNotLetter_thenTrue() {
        newTestCase()
            .subject('1')
            .expression(BoxedCharacterValue::isNotLetter)
            .result(TRUE)
            .name("isNotLetter")
            .expected("not[isLetter]")
            .actual("1")
            .run();
    }

    @Test
    void givenLetter_whenIsNotLetter_thenFalse() {
        newTestCase()
            .subject('C')
            .expression(BoxedCharacterValue::isNotLetter)
            .result(FALSE)
            .name("isNotLetter")
            .expected("not[isLetter]")
            .actual("C")
            .run();
    }

    // ---- isDigit ----

    @Test
    void givenDigit_whenIsDigit_thenTrue() {
        newTestCase()
            .subject('1')
            .expression(BoxedCharacterValue::isDigit)
            .result(TRUE)
            .name("isDigit")
            .expected("isDigit")
            .actual("1")
            .run();
    }

    @Test
    void givenLetter_whenIsDigit_thenFalse() {
        newTestCase()
            .subject('C')
            .expression(BoxedCharacterValue::isDigit)
            .result(FALSE)
            .name("isDigit")
            .expected("isDigit")
            .actual("C")
            .run();
    }

    // ---- isNotDigit ----

    @Test
    void givenDigit_whenIsNotDigit_thenFalse() {
        newTestCase()
            .subject('1')
            .expression(BoxedCharacterValue::isNotDigit)
            .result(FALSE)
            .name("isNotDigit")
            .expected("not[isDigit]")
            .actual("1")
            .run();
    }

    @Test
    void givenLetter_whenIsNotDigit_thenTrue() {
        newTestCase()
            .subject('C')
            .expression(BoxedCharacterValue::isNotDigit)
            .result(TRUE)
            .name("isNotDigit")
            .expected("not[isDigit]")
            .actual("C")
            .run();
    }

    // ---- isLetterOrDigit ----

    @Test
    void givenDigit_whenIsLetterOrDigit_thenTrue() {
        newTestCase()
            .subject('1')
            .expression(BoxedCharacterValue::isLetterOrDigit)
            .result(TRUE)
            .name("isLetterOrDigit")
            .expected("isLetterOrDigit")
            .actual("1")
            .run();
    }

    @Test
    void givenLetter_whenIsLetterOrDigit_thenTrue() {
        newTestCase()
            .subject('C')
            .expression(BoxedCharacterValue::isLetterOrDigit)
            .result(TRUE)
            .name("isLetterOrDigit")
            .expected("isLetterOrDigit")
            .actual("C")
            .run();
    }

    @Test
    void givenSymbol_whenIsLetterOrDigit_thenFalse() {
        newTestCase()
            .subject('-')
            .expression(BoxedCharacterValue::isLetterOrDigit)
            .result(FALSE)
            .name("isLetterOrDigit")
            .expected("isLetterOrDigit")
            .actual("-")
            .run();
    }

    // ---- isNotLetterOrDigit ----

    @Test
    void givenDigit_whenIsNotLetterOrDigit_thenFalse() {
        newTestCase()
            .subject('1')
            .expression(BoxedCharacterValue::isNotLetterOrDigit)
            .result(FALSE)
            .name("isNotLetterOrDigit")
            .expected("not[isLetterOrDigit]")
            .actual("1")
            .run();
    }

    @Test
    void givenLetter_whenIsNotLetterOrDigit_thenFalse() {
        newTestCase()
            .subject('C')
            .expression(BoxedCharacterValue::isNotLetterOrDigit)
            .result(FALSE)
            .name("isNotLetterOrDigit")
            .expected("not[isLetterOrDigit]")
            .actual("C")
            .run();
    }

    @Test
    void givenSymbol_whenIsNotLetterOrDigit_thenTrue() {
        newTestCase()
            .subject('-')
            .expression(BoxedCharacterValue::isNotLetterOrDigit)
            .result(TRUE)
            .name("isNotLetterOrDigit")
            .expected("not[isLetterOrDigit]")
            .actual("-")
            .run();
    }

    // ---- isWhitespace ----

    @Test
    void givenDigit_whenIsWhitespace_thenFalse() {
        newTestCase()
            .subject('1')
            .expression(BoxedCharacterValue::isWhitespace)
            .result(FALSE)
            .name("isWhitespace")
            .expected("isWhitespace")
            .actual("1")
            .run();
    }

    @Test
    void givenLetter_whenIsWhitespace_thenFalse() {
        newTestCase()
            .subject('C')
            .expression(BoxedCharacterValue::isWhitespace)
            .result(FALSE)
            .name("isWhitespace")
            .expected("isWhitespace")
            .actual("C")
            .run();
    }

    @Test
    void givenWhitespace_whenIsWhitespace_thenTrue() {
        newTestCase()
            .subject(' ')
            .expression(BoxedCharacterValue::isWhitespace)
            .result(TRUE)
            .name("isWhitespace")
            .expected("isWhitespace")
            .actual(" ")
            .run();
    }

    // ---- isNotWhitespace ----

    @Test
    void givenDigit_whenIsNotWhitespace_thenTrue() {
        newTestCase()
            .subject('1')
            .expression(BoxedCharacterValue::isNotWhitespace)
            .result(TRUE)
            .name("isNotWhitespace")
            .expected("not[isWhitespace]")
            .actual("1")
            .run();
    }

    @Test
    void givenLetter_whenIsNotWhitespace_thenTrue() {
        newTestCase()
            .subject('C')
            .expression(BoxedCharacterValue::isNotWhitespace)
            .result(TRUE)
            .name("isNotWhitespace")
            .expected("not[isWhitespace]")
            .actual("C")
            .run();
    }

    @Test
    void givenWhitespace_whenIsNotWhitespace_thenFalse() {
        newTestCase()
            .subject(' ')
            .expression(BoxedCharacterValue::isNotWhitespace)
            .result(FALSE)
            .name("isNotWhitespace")
            .expected("not[isWhitespace]")
            .actual(" ")
            .run();
    }
}
