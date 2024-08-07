package io.github.libzeal.zeal.expression.types.unary.boxed;

import io.github.libzeal.zeal.expression.types.unary.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.lang.evaluation.Result.PASSED;

class BoxedShortUnaryExpressionTest extends BoxedNumericExpressionTest<Short, BoxedShortUnaryExpression>  {

    @Override
    protected BoxedShortUnaryExpression expression(final Short value) {
        return new BoxedShortUnaryExpression(value);
    }

    @Override
    protected Short exampleNegativeValue() {
        return -1;
    }

    @Override
    protected Short exampleZeroValue() {
        return 0;
    }

    @Override
    protected Short examplePositiveValue() {
        return 1;
    }

    @Override
    protected Short exampleMaximumValue() {
        return Short.MAX_VALUE;
    }

    @Override
    protected Short exampleMinimumValue() {
        return Short.MIN_VALUE;
    }

    @Override
    protected void customTestCases(ExpressionTestCaseBuilder<Short, BoxedShortUnaryExpression> builder) {
        isEvenTestCases(builder);
        isOddTestCases(builder);
    }

    private void isEvenTestCases(final ExpressionTestCaseBuilder<Short, BoxedShortUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isEven())
                .value((short) 2)
                .expectedState(PASSED)
                .expectedName("isEven")
                .expectedExpectedValue("% 2 := 0")
                .expectedActualValue("2")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .value((short) 1)
                .expectedState(FAILED)
                .expectedName("isEven")
                .expectedExpectedValue("% 2 := 0")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .value((short) 0)
                .expectedState(PASSED)
                .expectedName("isEven")
                .expectedExpectedValue("% 2 := 0")
                .expectedActualValue("0")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .value((short) -1)
                .expectedState(FAILED)
                .expectedName("isEven")
                .expectedExpectedValue("% 2 := 0")
                .expectedActualValue("-1")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .value((short) -2)
                .expectedState(PASSED)
                .expectedName("isEven")
                .expectedExpectedValue("% 2 := 0")
                .expectedActualValue("-2")
                .addTest();
    }

    private void isOddTestCases(final ExpressionTestCaseBuilder<Short, BoxedShortUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isOdd())
                .value((short) 2)
                .expectedState(FAILED)
                .expectedName("isOdd")
                .expectedExpectedValue("% 2 != 0")
                .expectedActualValue("2")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .value((short) 1)
                .expectedState(PASSED)
                .expectedName("isOdd")
                .expectedExpectedValue("% 2 != 0")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .value((short) 0)
                .expectedState(FAILED)
                .expectedName("isOdd")
                .expectedExpectedValue("% 2 != 0")
                .expectedActualValue("0")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .value((short) -1)
                .expectedState(PASSED)
                .expectedName("isOdd")
                .expectedExpectedValue("% 2 != 0")
                .expectedActualValue("-1")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .value((short) -2)
                .expectedState(FAILED)
                .expectedName("isOdd")
                .expectedExpectedValue("% 2 != 0")
                .expectedActualValue("-2")
                .addTest();
    }
}
