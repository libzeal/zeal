package io.github.libzeal.zeal.values.core.unary;

import io.github.libzeal.zeal.values.core.unary.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

@SuppressWarnings("java:S2187")
abstract class EnumValueTest<T extends Enum<T>> extends ObjectValueTest<T, GeneralEnumValue<T>> {
    
    @Override
    protected void extendTestCases(ExpressionTestCaseBuilder<T, GeneralEnumValue<T>> builder) {
        ordinalIsTestCases(builder);
        ordinalIsNotTestCases(builder);
        nameIsTestCases(builder);
        nameIsNotTestCases(builder);
        caseInsensitiveNameIsTestCases(builder);
        caseInsensitiveNameIsNotTestCases(builder);
    }

    private void ordinalIsTestCases(ExpressionTestCaseBuilder<T, GeneralEnumValue<T>> builder) {

        T value1 = exampleValue1();
        T value2 = exampleValue2();
        
        builder.newTest((expression, value) -> expression.ordinalIs(value1.ordinal()))
                .subject(value1)
                .expectedState(TRUE)
                .expectedName(value -> "ordinalIs[" + value1.ordinal() + "]")
                .expectedExpected(String.valueOf(value1.ordinal()))
                .expectedActual(String.valueOf(value1.ordinal()))
                .addTest()
            .newTest((expression, value) -> expression.ordinalIs(value1.ordinal()))
                .subject(value2)
                .expectedState(FALSE)
                .expectedName(value -> "ordinalIs[" + value1.ordinal() + "]")
                .expectedExpected(String.valueOf(value1.ordinal()))
                .expectedActual(String.valueOf(value2.ordinal()))
                .addTest();
    }

    private void ordinalIsNotTestCases(ExpressionTestCaseBuilder<T, GeneralEnumValue<T>> builder) {

        T value1 = exampleValue1();
        T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.ordinalIsNot(value1.ordinal()))
                .subject(value1)
                .expectedState(FALSE)
                .expectedName(value -> "not[ordinalIs[" + value1.ordinal() + "]]")
                .expectedExpected("not[" + value1.ordinal() + "]")
                .expectedActual(String.valueOf(value1.ordinal()))
                .addTest()
            .newTest((expression, value) -> expression.ordinalIsNot(value1.ordinal()))
                .subject(value2)
                .expectedState(TRUE)
                .expectedName(value -> "not[ordinalIs[" + value1.ordinal() + "]]")
                .expectedExpected("not[" + value1.ordinal() + "]")
                .expectedActual(String.valueOf(value2.ordinal()))
                .addTest();
    }

    private void nameIsTestCases(ExpressionTestCaseBuilder<T, GeneralEnumValue<T>> builder) {

        T value1 = exampleValue1();
        T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.nameIs(value1.name()))
                .subject(value1)
                .expectedState(TRUE)
                .expectedName(value -> "nameIs[" + value1.name() + "]")
                .expectedExpected(value1.name())
                .expectedActual(value1.name())
                .addTest()
            .newTest((expression, value) -> expression.nameIs(value1.name()))
                .subject(value2)
                .expectedState(FALSE)
                .expectedName(value -> "nameIs[" + value1.name() + "]")
                .expectedExpected(value1.name())
                .expectedActual(value2.name())
                .addTest();
    }

    private void nameIsNotTestCases(ExpressionTestCaseBuilder<T, GeneralEnumValue<T>> builder) {

        T value1 = exampleValue1();
        T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.nameIsNot(value1.name()))
                .subject(value1)
                .expectedState(FALSE)
                .expectedName(value -> "not[nameIs[" + value1.name() + "]]")
                .expectedExpected("not[" + value1.name() + "]")
                .expectedActual(value1.name())
                .addTest()
            .newTest((expression, value) -> expression.nameIsNot(value1.name()))
                .subject(value2)
                .expectedState(TRUE)
                .expectedName(value -> "not[nameIs[" + value1.name() + "]]")
                .expectedExpected("not[" + value1.name() + "]")
                .expectedActual(value2.name())
                .addTest();
    }

    private void caseInsensitiveNameIsTestCases(ExpressionTestCaseBuilder<T, GeneralEnumValue<T>> builder) {

        T value1 = exampleValue1();
        T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.caseInsensitiveNameIs(value1.name()))
                .subject(value1)
                .expectedState(TRUE)
                .expectedName(value -> "caseInsensitiveNameIs[" + value1.name() + "]")
                .expectedExpected("caseInsensitive[" + value1.name() + "]")
                .expectedActual(value1.name())
                .addTest()
            .newTest((expression, value) -> expression.caseInsensitiveNameIs(value1.name().toLowerCase()))
                .subject(value1)
                .expectedState(TRUE)
                .expectedName(value -> "caseInsensitiveNameIs[" + value1.name().toLowerCase() + "]")
                .expectedExpected("caseInsensitive[" + value1.name().toLowerCase() + "]")
                .expectedActual(value1.name())
                .addTest()
            .newTest((expression, value) -> expression.caseInsensitiveNameIs(value1.name()))
                .subject(value2)
                .expectedState(FALSE)
                .expectedName(value -> "caseInsensitiveNameIs[" + value1.name() + "]")
                .expectedExpected("caseInsensitive[" + value1.name() + "]")
                .expectedActual(value2.name())
                .addTest();
    }

    private void caseInsensitiveNameIsNotTestCases(ExpressionTestCaseBuilder<T,
        GeneralEnumValue<T>> builder) {

        T value1 = exampleValue1();
        T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.caseInsensitiveNameIsNot(value1.name()))
                .subject(value1)
                .expectedState(FALSE)
                .expectedName(value -> "not[caseInsensitiveNameIs[" + value1.name() + "]]")
                .expectedExpected("not[caseInsensitive[" + value1.name() + "]]")
                .expectedActual(value1.name())
                .addTest()
            .newTest((expression, value) -> expression.caseInsensitiveNameIsNot(value1.name().toLowerCase()))
                .subject(value1)
                .expectedState(FALSE)
                .expectedName(value -> "not[caseInsensitiveNameIs[" + value1.name().toLowerCase() + "]]")
                .expectedExpected("not[caseInsensitive[" + value1.name().toLowerCase() + "]]")
                .expectedActual(value1.name())
                .addTest()
            .newTest((expression, value) -> expression.caseInsensitiveNameIsNot(value1.name()))
                .subject(value2)
                .expectedState(TRUE)
                .expectedName(value -> "not[caseInsensitiveNameIs[" + value1.name() + "]]")
                .expectedExpected("not[caseInsensitive[" + value1.name() + "]]")
                .expectedActual(value2.name())
                .addTest();
    }
}
