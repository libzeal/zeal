package io.github.libzeal.zeal.expression.types.boxed;

import io.github.libzeal.zeal.expression.types.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.lang.evaluation.Result.PASSED;

class BoxedLongExpressionTest extends BoxedNumericExpressionTest<Long, BoxedLongExpression>  {

    @Override
    protected BoxedLongExpression expression(final Long value) {
        return new BoxedLongExpression(value);
    }

    @Override
    protected Long exampleNegativeValue() {
        return -1L;
    }

    @Override
    protected Long exampleZeroValue() {
        return 0L;
    }

    @Override
    protected Long examplePositiveValue() {
        return 1L;
    }

    @Override
    protected Long exampleMaximumValue() {
        return Long.MAX_VALUE;
    }

    @Override
    protected Long exampleMinimumValue() {
        return Long.MIN_VALUE;
    }

    @Override
    protected void customTestCases(ExpressionTestCaseBuilder<Long, BoxedLongExpression> builder) {
        isEvenTestCases(builder);
        isOddTestCases(builder);
    }

    private void isEvenTestCases(final ExpressionTestCaseBuilder<Long, BoxedLongExpression> builder) {
        builder.newTest((expression, value) -> expression.isEven())
                .value(2L)
                .expectedState(PASSED)
                .expectedName("isEven")
                .expectedExpectedValue("% 2 := 0")
                .expectedActualValue("2")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .value(1L)
                .expectedState(FAILED)
                .expectedName("isEven")
                .expectedExpectedValue("% 2 := 0")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .value(0L)
                .expectedState(PASSED)
                .expectedName("isEven")
                .expectedExpectedValue("% 2 := 0")
                .expectedActualValue("0")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .value(-1L)
                .expectedState(FAILED)
                .expectedName("isEven")
                .expectedExpectedValue("% 2 := 0")
                .expectedActualValue("-1")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .value(-2L)
                .expectedState(PASSED)
                .expectedName("isEven")
                .expectedExpectedValue("% 2 := 0")
                .expectedActualValue("-2")
                .addTest();
    }

    private void isOddTestCases(final ExpressionTestCaseBuilder<Long, BoxedLongExpression> builder) {
        builder.newTest((expression, value) -> expression.isOdd())
                .value(2L)
                .expectedState(FAILED)
                .expectedName("isOdd")
                .expectedExpectedValue("% 2 != 0")
                .expectedActualValue("2")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .value(1L)
                .expectedState(PASSED)
                .expectedName("isOdd")
                .expectedExpectedValue("% 2 != 0")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .value(0L)
                .expectedState(FAILED)
                .expectedName("isOdd")
                .expectedExpectedValue("% 2 != 0")
                .expectedActualValue("0")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .value(-1L)
                .expectedState(PASSED)
                .expectedName("isOdd")
                .expectedExpectedValue("% 2 != 0")
                .expectedActualValue("-1")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .value(-2L)
                .expectedState(FAILED)
                .expectedName("isOdd")
                .expectedExpectedValue("% 2 != 0")
                .expectedActualValue("-2")
                .addTest();
    }
}
