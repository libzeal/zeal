package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.values.core.boxed.BoxedCharacterValue;
import io.github.libzeal.zeal.values.core.basic.ObjectValueTest;
import io.github.libzeal.zeal.values.core.basic.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

public class BoxedCharacterValueTest extends ObjectValueTest<Character,
        BoxedCharacterValue> {

    @Override
    protected BoxedCharacterValue expression(final Character value) {
        return new BoxedCharacterValue(value);
    }

    @Override
    protected Character exampleValue1() {
        return 'a';
    }

    @Override
    protected Character exampleValue2() {
        return 'b';
    }

    @Override
    protected void extendTestCases(ExpressionTestCaseBuilder<Character, BoxedCharacterValue> builder) {
        isLowerCaseTestCases(builder);
        isUpperCaseTestCases(builder);
        isLetterTestCases(builder);
        isNotLetterTestCases(builder);
        isDigitTestCases(builder);
        isNotDigitTestCases(builder);
        isLetterOrDigitTestCases(builder);
        isNotLetterOrDigitTestCases(builder);
        isWhitespaceTestCases(builder);
        isNotWhitespaceTestCases(builder);
    }

    private void isLowerCaseTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterValue> builder) {
        builder.newTest((expression, value) -> expression.isLowerCase())
                .subject('c')
                .expectedState(TRUE)
                .expectedName("isLowerCase")
                .expectedExpected("isLowerCase")
                .expectedActual("c")
                .addTest()
            .newTest((expression, value) -> expression.isLowerCase())
                .subject('C')
                .expectedState(FALSE)
                .expectedName("isLowerCase")
                .expectedExpected("isLowerCase")
                .expectedActual("C")
                .addTest();
    }

    private void isUpperCaseTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterValue> builder) {
        builder.newTest((expression, value) -> expression.isUpperCase())
                .subject('c')
                .expectedState(FALSE)
                .expectedName("isUpperCase")
                .expectedExpected("isUpperCase")
                .expectedActual("c")
                .addTest()
            .newTest((expression, value) -> expression.isUpperCase())
                .subject('C')
                .expectedState(TRUE)
                .expectedName("isUpperCase")
                .expectedExpected("isUpperCase")
                .expectedActual("C")
                .addTest();
    }

    private void isLetterTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterValue> builder) {
        builder.newTest((expression, value) -> expression.isLetter())
                .subject('1')
                .expectedState(FALSE)
                .expectedName("isLetter")
                .expectedExpected("isLetter")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isLetter())
                .subject('C')
                .expectedState(TRUE)
                .expectedName("isLetter")
                .expectedExpected("isLetter")
                .expectedActual("C")
                .addTest();
    }

    private void isNotLetterTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterValue> builder) {
        builder.newTest((expression, value) -> expression.isNotLetter())
                .subject('1')
                .expectedState(TRUE)
                .expectedName("isNotLetter")
                .expectedExpected("not[isLetter]")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isNotLetter())
                .subject('C')
                .expectedState(FALSE)
                .expectedName("isNotLetter")
                .expectedExpected("not[isLetter]")
                .expectedActual("C")
                .addTest();
    }

    private void isDigitTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterValue> builder) {
        builder.newTest((expression, value) -> expression.isDigit())
                .subject('1')
                .expectedState(TRUE)
                .expectedName("isDigit")
                .expectedExpected("isDigit")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isDigit())
                .subject('C')
                .expectedState(FALSE)
                .expectedName("isDigit")
                .expectedExpected("isDigit")
                .expectedActual("C")
                .addTest();
    }

    private void isNotDigitTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterValue> builder) {
        builder.newTest((expression, value) -> expression.isNotDigit())
                .subject('1')
                .expectedState(FALSE)
                .expectedName("isNotDigit")
                .expectedExpected("not[isDigit]")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isNotDigit())
                .subject('C')
                .expectedState(TRUE)
                .expectedName("isNotDigit")
                .expectedExpected("not[isDigit]")
                .expectedActual("C")
                .addTest();
    }

    private void isLetterOrDigitTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterValue> builder) {
        builder.newTest((expression, value) -> expression.isLetterOrDigit())
                .subject('1')
                .expectedState(TRUE)
                .expectedName("isLetterOrDigit")
                .expectedExpected("isLetterOrDigit")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isLetterOrDigit())
                .subject('C')
                .expectedState(TRUE)
                .expectedName("isLetterOrDigit")
                .expectedExpected("isLetterOrDigit")
                .expectedActual("C")
                .addTest()
            .newTest((expression, value) -> expression.isLetterOrDigit())
                .subject('-')
                .expectedState(FALSE)
                .expectedName("isLetterOrDigit")
                .expectedExpected("isLetterOrDigit")
                .expectedActual("-")
                .addTest();
    }

    private void isNotLetterOrDigitTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterValue> builder) {
        builder.newTest((expression, value) -> expression.isNotLetterOrDigit())
                .subject('1')
                .expectedState(FALSE)
                .expectedName("isNotLetterOrDigit")
                .expectedExpected("not[isLetterOrDigit]")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isNotLetterOrDigit())
                .subject('C')
                .expectedState(FALSE)
                .expectedName("isNotLetterOrDigit")
                .expectedExpected("not[isLetterOrDigit]")
                .expectedActual("C")
                .addTest()
            .newTest((expression, value) -> expression.isNotLetterOrDigit())
                .subject('-')
                .expectedState(TRUE)
                .expectedName("isNotLetterOrDigit")
                .expectedExpected("not[isLetterOrDigit]")
                .expectedActual("-")
                .addTest();
    }

    private void isWhitespaceTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterValue> builder) {
        builder.newTest((expression, value) -> expression.isWhitespace())
                .subject('1')
                .expectedState(FALSE)
                .expectedName("isWhitespace")
                .expectedExpected("isWhitespace")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isWhitespace())
                .subject('C')
                .expectedState(FALSE)
                .expectedName("isWhitespace")
                .expectedExpected("isWhitespace")
                .expectedActual("C")
                .addTest()
            .newTest((expression, value) -> expression.isWhitespace())
                .subject(' ')
                .expectedState(TRUE)
                .expectedName("isWhitespace")
                .expectedExpected("isWhitespace")
                .expectedActual(" ")
                .addTest();
    }

    private void isNotWhitespaceTestCases(final ExpressionTestCaseBuilder<Character, BoxedCharacterValue> builder) {
        builder.newTest((expression, value) -> expression.isNotWhitespace())
                .subject('1')
                .expectedState(TRUE)
                .expectedName("isNotWhitespace")
                .expectedExpected("not[isWhitespace]")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isNotWhitespace())
                .subject('C')
                .expectedState(TRUE)
                .expectedName("isNotWhitespace")
                .expectedExpected("not[isWhitespace]")
                .expectedActual("C")
                .addTest()
            .newTest((expression, value) -> expression.isNotWhitespace())
                .subject(' ')
                .expectedState(FALSE)
                .expectedName("isNotWhitespace")
                .expectedExpected("not[isWhitespace]")
                .expectedActual(" ")
                .addTest();
    }
}
