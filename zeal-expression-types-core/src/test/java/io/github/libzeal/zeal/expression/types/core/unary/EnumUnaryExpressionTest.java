package io.github.libzeal.zeal.expression.types.core.unary;

import io.github.libzeal.zeal.expression.types.core.unary.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.lang.evaluation.Result.PASSED;

@SuppressWarnings("java:S2187")
abstract class EnumUnaryExpressionTest<T extends Enum<T>> extends ObjectUnaryExpressionTest<T, GeneralEnumUnaryExpression<T>> {
    
    @Override
    protected void extendTestCases(ExpressionTestCaseBuilder<T, GeneralEnumUnaryExpression<T>> builder) {
        ordinalIsTestCases(builder);
        ordinalIsNotTestCases(builder);
        nameIsTestCases(builder);
        nameIsNotTestCases(builder);
        caseInsensitiveNameIsTestCases(builder);
        caseInsensitiveNameIsNotTestCases(builder);
    }

    private void ordinalIsTestCases(ExpressionTestCaseBuilder<T, GeneralEnumUnaryExpression<T>> builder) {

        T value1 = exampleValue1();
        T value2 = exampleValue2();
        
        builder.newTest((expression, value) -> expression.ordinalIs(value1.ordinal()))
                .value(value1)
                .expectedState(PASSED)
                .expectedName(value -> "ordinalIs[" + value1.ordinal() + "]")
                .expectedExpectedValue(String.valueOf(value1.ordinal()))
                .expectedActualValue(String.valueOf(value1.ordinal()))
                .addTest()
            .newTest((expression, value) -> expression.ordinalIs(value1.ordinal()))
                .value(value2)
                .expectedState(FAILED)
                .expectedName(value -> "ordinalIs[" + value1.ordinal() + "]")
                .expectedExpectedValue(String.valueOf(value1.ordinal()))
                .expectedActualValue(String.valueOf(value2.ordinal()))
                .addTest();
    }

    private void ordinalIsNotTestCases(ExpressionTestCaseBuilder<T, GeneralEnumUnaryExpression<T>> builder) {

        T value1 = exampleValue1();
        T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.ordinalIsNot(value1.ordinal()))
                .value(value1)
                .expectedState(FAILED)
                .expectedName(value -> "not[ordinalIs[" + value1.ordinal() + "]]")
                .expectedExpectedValue("not[" + value1.ordinal() + "]")
                .expectedActualValue(String.valueOf(value1.ordinal()))
                .addTest()
            .newTest((expression, value) -> expression.ordinalIsNot(value1.ordinal()))
                .value(value2)
                .expectedState(PASSED)
                .expectedName(value -> "not[ordinalIs[" + value1.ordinal() + "]]")
                .expectedExpectedValue("not[" + value1.ordinal() + "]")
                .expectedActualValue(String.valueOf(value2.ordinal()))
                .addTest();
    }

    private void nameIsTestCases(ExpressionTestCaseBuilder<T, GeneralEnumUnaryExpression<T>> builder) {

        T value1 = exampleValue1();
        T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.nameIs(value1.name()))
                .value(value1)
                .expectedState(PASSED)
                .expectedName(value -> "nameIs[" + value1.name() + "]")
                .expectedExpectedValue(value1.name())
                .expectedActualValue(value1.name())
                .addTest()
            .newTest((expression, value) -> expression.nameIs(value1.name()))
                .value(value2)
                .expectedState(FAILED)
                .expectedName(value -> "nameIs[" + value1.name() + "]")
                .expectedExpectedValue(value1.name())
                .expectedActualValue(value2.name())
                .addTest();
    }

    private void nameIsNotTestCases(ExpressionTestCaseBuilder<T, GeneralEnumUnaryExpression<T>> builder) {

        T value1 = exampleValue1();
        T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.nameIsNot(value1.name()))
                .value(value1)
                .expectedState(FAILED)
                .expectedName(value -> "not[nameIs[" + value1.name() + "]]")
                .expectedExpectedValue("not[" + value1.name() + "]")
                .expectedActualValue(value1.name())
                .addTest()
            .newTest((expression, value) -> expression.nameIsNot(value1.name()))
                .value(value2)
                .expectedState(PASSED)
                .expectedName(value -> "not[nameIs[" + value1.name() + "]]")
                .expectedExpectedValue("not[" + value1.name() + "]")
                .expectedActualValue(value2.name())
                .addTest();
    }

    private void caseInsensitiveNameIsTestCases(ExpressionTestCaseBuilder<T, GeneralEnumUnaryExpression<T>> builder) {

        T value1 = exampleValue1();
        T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.caseInsensitiveNameIs(value1.name()))
                .value(value1)
                .expectedState(PASSED)
                .expectedName(value -> "caseInsensitiveNameIs[" + value1.name() + "]")
                .expectedExpectedValue("caseInsensitive[" + value1.name() + "]")
                .expectedActualValue(value1.name())
                .addTest()
            .newTest((expression, value) -> expression.caseInsensitiveNameIs(value1.name().toLowerCase()))
                .value(value1)
                .expectedState(PASSED)
                .expectedName(value -> "caseInsensitiveNameIs[" + value1.name().toLowerCase() + "]")
                .expectedExpectedValue("caseInsensitive[" + value1.name().toLowerCase() + "]")
                .expectedActualValue(value1.name())
                .addTest()
            .newTest((expression, value) -> expression.caseInsensitiveNameIs(value1.name()))
                .value(value2)
                .expectedState(FAILED)
                .expectedName(value -> "caseInsensitiveNameIs[" + value1.name() + "]")
                .expectedExpectedValue("caseInsensitive[" + value1.name() + "]")
                .expectedActualValue(value2.name())
                .addTest();
    }

    private void caseInsensitiveNameIsNotTestCases(ExpressionTestCaseBuilder<T,
        GeneralEnumUnaryExpression<T>> builder) {

        T value1 = exampleValue1();
        T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.caseInsensitiveNameIsNot(value1.name()))
                .value(value1)
                .expectedState(FAILED)
                .expectedName(value -> "not[caseInsensitiveNameIs[" + value1.name() + "]]")
                .expectedExpectedValue("not[caseInsensitive[" + value1.name() + "]]")
                .expectedActualValue(value1.name())
                .addTest()
            .newTest((expression, value) -> expression.caseInsensitiveNameIsNot(value1.name().toLowerCase()))
                .value(value1)
                .expectedState(FAILED)
                .expectedName(value -> "not[caseInsensitiveNameIs[" + value1.name().toLowerCase() + "]]")
                .expectedExpectedValue("not[caseInsensitive[" + value1.name().toLowerCase() + "]]")
                .expectedActualValue(value1.name())
                .addTest()
            .newTest((expression, value) -> expression.caseInsensitiveNameIsNot(value1.name()))
                .value(value2)
                .expectedState(PASSED)
                .expectedName(value -> "not[caseInsensitiveNameIs[" + value1.name() + "]]")
                .expectedExpectedValue("not[caseInsensitive[" + value1.name() + "]]")
                .expectedActualValue(value2.name())
                .addTest();
    }
}
