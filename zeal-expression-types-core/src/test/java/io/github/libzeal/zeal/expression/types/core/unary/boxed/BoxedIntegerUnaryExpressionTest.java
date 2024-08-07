package io.github.libzeal.zeal.expression.types.core.unary.boxed;

import io.github.libzeal.zeal.expression.types.core.unary.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.lang.evaluation.Result.PASSED;

class BoxedIntegerUnaryExpressionTest extends BoxedNumericExpressionTest<Integer, BoxedIntegerUnaryExpression>  {

    @Override
    protected BoxedIntegerUnaryExpression expression(final Integer value) {
        return new BoxedIntegerUnaryExpression(value);
    }

    @Override
    protected Integer exampleNegativeValue() {
        return -1;
    }

    @Override
    protected Integer exampleZeroValue() {
        return 0;
    }

    @Override
    protected Integer examplePositiveValue() {
        return 1;
    }

    @Override
    protected Integer exampleMaximumValue() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected Integer exampleMinimumValue() {
        return Integer.MIN_VALUE;
    }

    @Override
    protected void customTestCases(ExpressionTestCaseBuilder<Integer, BoxedIntegerUnaryExpression> builder) {
        isEvenTestCases(builder);
        isOddTestCases(builder);
    }

    private void isEvenTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isEven())
                .value(2)
                .expectedState(PASSED)
                .expectedName("isEven")
                .expectedExpectedValue("% 2 := 0")
                .expectedActualValue("2")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .value(1)
                .expectedState(FAILED)
                .expectedName("isEven")
                .expectedExpectedValue("% 2 := 0")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .value(0)
                .expectedState(PASSED)
                .expectedName("isEven")
                .expectedExpectedValue("% 2 := 0")
                .expectedActualValue("0")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .value(-1)
                .expectedState(FAILED)
                .expectedName("isEven")
                .expectedExpectedValue("% 2 := 0")
                .expectedActualValue("-1")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .value(-2)
                .expectedState(PASSED)
                .expectedName("isEven")
                .expectedExpectedValue("% 2 := 0")
                .expectedActualValue("-2")
                .addTest();
    }

    private void isOddTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isOdd())
                .value(2)
                .expectedState(FAILED)
                .expectedName("isOdd")
                .expectedExpectedValue("% 2 != 0")
                .expectedActualValue("2")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .value(1)
                .expectedState(PASSED)
                .expectedName("isOdd")
                .expectedExpectedValue("% 2 != 0")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .value(0)
                .expectedState(FAILED)
                .expectedName("isOdd")
                .expectedExpectedValue("% 2 != 0")
                .expectedActualValue("0")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .value(-1)
                .expectedState(PASSED)
                .expectedName("isOdd")
                .expectedExpectedValue("% 2 != 0")
                .expectedActualValue("-1")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .value(-2)
                .expectedState(FAILED)
                .expectedName("isOdd")
                .expectedExpectedValue("% 2 != 0")
                .expectedActualValue("-2")
                .addTest();
    }
}
