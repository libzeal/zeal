package io.github.libzeal.zeal.expression.types.boxed;

import io.github.libzeal.zeal.expression.test.ExpressionTestCaseBuilder;
import io.github.libzeal.zeal.expression.types.ObjectExpressionTest;

import static io.github.libzeal.zeal.expression.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.evaluation.Result.PASSED;

class BoxedIntegerExpressionTest extends ObjectExpressionTest<Integer, BoxedIntegerExpression>  {

    @Override
    protected BoxedIntegerExpression expression(final Integer value) {
        return new BoxedIntegerExpression(value);
    }

    @Override
    protected Integer exampleValue1() {
        return 1;
    }

    @Override
    protected Integer exampleValue2() {
        return 2;
    }

    @Override
    protected void extendTestCases(ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
        isEqualToTestCases(builder);
        isNotEqualToTestCases(builder);
        isLessThanTestCases(builder);
        ltTestCases(builder);
        isGreaterThanTestCases(builder);
        gtTestCases(builder);
        isLessThanOrEqualToTestCases(builder);
        lteTestCases(builder);
        isGreaterThanOrEqualToTestCases(builder);
        gteTestCases(builder);
        isMaxValueTestCases(builder);
        isMinValueTestCases(builder);
        isZeroTestCases(builder);
        isPositiveTestCases(builder);
        isNotPositiveTestCases(builder);
        isNegativeTestCases(builder);
        isNotNegativeTestCases(builder);
        isEvenTestCases(builder);
        isOddTestCases(builder);
    }

    private void isEqualToTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
        builder.newTest((expression, value) -> expression.isEqualTo(1))
                .value(1)
                .expectedState(PASSED)
                .expectedName("isEqualTo[1]")
                .expectedExpectedValue("1")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1))
                .value(2)
                .expectedState(FAILED)
                .expectedName("isEqualTo[1]")
                .expectedExpectedValue("1")
                .expectedActualValue("2")
                .addTest();
    }

    private void isNotEqualToTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
        builder.newTest((expression, value) -> expression.isNotEqualTo(1))
                .value(1)
                .expectedState(FAILED)
                .expectedName("isNotEqualTo[1]")
                .expectedExpectedValue("not[1]")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.isNotEqualTo(1))
                .value(2)
                .expectedState(PASSED)
                .expectedName("isNotEqualTo[1]")
                .expectedExpectedValue("not[1]")
                .expectedActualValue("2")
                .addTest();
    }

    private void isLessThanTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
        builder.newTest((expression, value) -> expression.isLessThan(1))
                .value(1)
                .expectedState(FAILED)
                .expectedName("isLessThan[1]")
                .expectedExpectedValue("< 1")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.isLessThan(1))
                .value(2)
                .expectedState(FAILED)
                .expectedName("isLessThan[1]")
                .expectedExpectedValue("< 1")
                .expectedActualValue("2")
                .addTest()
            .newTest((expression, value) -> expression.isLessThan(1))
                .value(0)
                .expectedState(PASSED)
                .expectedName("isLessThan[1]")
                .expectedExpectedValue("< 1")
                .expectedActualValue("0")
                .addTest();
    }

    private void ltTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
        builder.newTest((expression, value) -> expression.lt(1))
                .value(1)
                .expectedState(FAILED)
                .expectedName("isLessThan[1]")
                .expectedExpectedValue("< 1")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.lt(1))
                .value(2)
                .expectedState(FAILED)
                .expectedName("isLessThan[1]")
                .expectedExpectedValue("< 1")
                .expectedActualValue("2")
                .addTest()
            .newTest((expression, value) -> expression.lt(1))
                .value(0)
                .expectedState(PASSED)
                .expectedName("isLessThan[1]")
                .expectedExpectedValue("< 1")
                .expectedActualValue("0")
                .addTest();
    }

    private void isGreaterThanTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
        builder.newTest((expression, value) -> expression.isGreaterThan(1))
                .value(1)
                .expectedState(FAILED)
                .expectedName("isGreaterThan[1]")
                .expectedExpectedValue("> 1")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.isGreaterThan(1))
                .value(2)
                .expectedState(PASSED)
                .expectedName("isGreaterThan[1]")
                .expectedExpectedValue("> 1")
                .expectedActualValue("2")
                .addTest()
            .newTest((expression, value) -> expression.isGreaterThan(1))
                .value(0)
                .expectedState(FAILED)
                .expectedName("isGreaterThan[1]")
                .expectedExpectedValue("> 1")
                .expectedActualValue("0")
                .addTest();
    }

    private void gtTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
        builder.newTest((expression, value) -> expression.gt(1))
                .value(1)
                .expectedState(FAILED)
                .expectedName("isGreaterThan[1]")
                .expectedExpectedValue("> 1")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.gt(1))
                .value(2)
                .expectedState(PASSED)
                .expectedName("isGreaterThan[1]")
                .expectedExpectedValue("> 1")
                .expectedActualValue("2")
                .addTest()
            .newTest((expression, value) -> expression.gt(1))
                .value(0)
                .expectedState(FAILED)
                .expectedName("isGreaterThan[1]")
                .expectedExpectedValue("> 1")
                .expectedActualValue("0")
                .addTest();
    }

    private void isLessThanOrEqualToTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
        builder.newTest((expression, value) -> expression.isLessThanOrEqualTo(1))
                .value(1)
                .expectedState(PASSED)
                .expectedName("isLessThanOrEqualTo[1]")
                .expectedExpectedValue("<= 1")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.isLessThanOrEqualTo(1))
                .value(2)
                .expectedState(FAILED)
                .expectedName("isLessThanOrEqualTo[1]")
                .expectedExpectedValue("<= 1")
                .expectedActualValue("2")
                .addTest()
            .newTest((expression, value) -> expression.isLessThanOrEqualTo(1))
                .value(0)
                .expectedState(PASSED)
                .expectedName("isLessThanOrEqualTo[1]")
                .expectedExpectedValue("<= 1")
                .expectedActualValue("0")
                .addTest();
    }

    private void lteTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
        builder.newTest((expression, value) -> expression.lte(1))
                .value(1)
                .expectedState(PASSED)
                .expectedName("isLessThanOrEqualTo[1]")
                .expectedExpectedValue("<= 1")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.lte(1))
                .value(2)
                .expectedState(FAILED)
                .expectedName("isLessThanOrEqualTo[1]")
                .expectedExpectedValue("<= 1")
                .expectedActualValue("2")
                .addTest()
            .newTest((expression, value) -> expression.lte(1))
                .value(0)
                .expectedState(PASSED)
                .expectedName("isLessThanOrEqualTo[1]")
                .expectedExpectedValue("<= 1")
                .expectedActualValue("0")
                .addTest();
    }

    private void isGreaterThanOrEqualToTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
        builder.newTest((expression, value) -> expression.isGreaterThanOrEqualTo(1))
                .value(1)
                .expectedState(PASSED)
                .expectedName("isGreaterThanOrEqualTo[1]")
                .expectedExpectedValue(">= 1")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.isGreaterThanOrEqualTo(1))
                .value(2)
                .expectedState(PASSED)
                .expectedName("isGreaterThanOrEqualTo[1]")
                .expectedExpectedValue(">= 1")
                .expectedActualValue("2")
                .addTest()
            .newTest((expression, value) -> expression.isGreaterThanOrEqualTo(1))
                .value(0)
                .expectedState(FAILED)
                .expectedName("isGreaterThanOrEqualTo[1]")
                .expectedExpectedValue(">= 1")
                .expectedActualValue("0")
                .addTest();
    }

    private void gteTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
        builder.newTest((expression, value) -> expression.gte(1))
                .value(1)
                .expectedState(PASSED)
                .expectedName("isGreaterThanOrEqualTo[1]")
                .expectedExpectedValue(">= 1")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.gte(1))
                .value(2)
                .expectedState(PASSED)
                .expectedName("isGreaterThanOrEqualTo[1]")
                .expectedExpectedValue(">= 1")
                .expectedActualValue("2")
                .addTest()
            .newTest((expression, value) -> expression.gte(1))
                .value(0)
                .expectedState(FAILED)
                .expectedName("isGreaterThanOrEqualTo[1]")
                .expectedExpectedValue(">= 1")
                .expectedActualValue("0")
                .addTest();
    }

    private void isMaxValueTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
        builder.newTest((expression, value) -> expression.isMaxValue())
                .value(Integer.MAX_VALUE)
                .expectedState(PASSED)
                .expectedName("isMax")
                .expectedExpectedValue(String.valueOf(Integer.MAX_VALUE))
                .expectedActualValue(String.valueOf(Integer.MAX_VALUE))
                .addTest()
            .newTest((expression, value) -> expression.isMaxValue())
                .value(Integer.MAX_VALUE - 1)
                .expectedState(FAILED)
                .expectedName("isMax")
                .expectedExpectedValue(String.valueOf(Integer.MAX_VALUE))
                .expectedActualValue(String.valueOf(Integer.MAX_VALUE - 1))
                .addTest();
    }

    private void isMinValueTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
        builder.newTest((expression, value) -> expression.isMinValue())
                .value(Integer.MIN_VALUE)
                .expectedState(PASSED)
                .expectedName("isMin")
                .expectedExpectedValue(String.valueOf(Integer.MIN_VALUE))
                .expectedActualValue(String.valueOf(Integer.MIN_VALUE))
                .addTest()
            .newTest((expression, value) -> expression.isMinValue())
                .value(Integer.MIN_VALUE + 1)
                .expectedState(FAILED)
                .expectedName("isMin")
                .expectedExpectedValue(String.valueOf(Integer.MIN_VALUE))
                .expectedActualValue(String.valueOf(Integer.MIN_VALUE + 1))
                .addTest();
    }

    private void isZeroTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
        builder.newTest((expression, value) -> expression.isZero())
                .value(1)
                .expectedState(FAILED)
                .expectedName("isZero")
                .expectedExpectedValue("0")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.isZero())
                .value(0)
                .expectedState(PASSED)
                .expectedName("isZero")
                .expectedExpectedValue("0")
                .expectedActualValue("0")
                .addTest()
            .newTest((expression, value) -> expression.isZero())
                .value(-1)
                .expectedState(FAILED)
                .expectedName("isZero")
                .expectedExpectedValue("0")
                .expectedActualValue("-1")
                .addTest();
    }

    private void isPositiveTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
        builder.newTest((expression, value) -> expression.isPositive())
                .value(1)
                .expectedState(PASSED)
                .expectedName("isPositive")
                .expectedExpectedValue("> 0")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.isPositive())
                .value(0)
                .expectedState(FAILED)
                .expectedName("isPositive")
                .expectedExpectedValue("> 0")
                .expectedActualValue("0")
                .addTest()
            .newTest((expression, value) -> expression.isPositive())
                .value(-1)
                .expectedState(FAILED)
                .expectedName("isPositive")
                .expectedExpectedValue("> 0")
                .expectedActualValue("-1")
                .addTest();
    }

    private void isNotPositiveTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
        builder.newTest((expression, value) -> expression.isNotPositive())
                .value(1)
                .expectedState(FAILED)
                .expectedName("isNotPositive")
                .expectedExpectedValue("<= 0")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.isNotPositive())
                .value(0)
                .expectedState(PASSED)
                .expectedName("isNotPositive")
                .expectedExpectedValue("<= 0")
                .expectedActualValue("0")
                .addTest()
            .newTest((expression, value) -> expression.isNotPositive())
                .value(-1)
                .expectedState(PASSED)
                .expectedName("isNotPositive")
                .expectedExpectedValue("<= 0")
                .expectedActualValue("-1")
                .addTest();
    }

    private void isNegativeTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
        builder.newTest((expression, value) -> expression.isNegative())
                .value(1)
                .expectedState(FAILED)
                .expectedName("isNegative")
                .expectedExpectedValue("< 0")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.isNegative())
                .value(0)
                .expectedState(FAILED)
                .expectedName("isNegative")
                .expectedExpectedValue("< 0")
                .expectedActualValue("0")
                .addTest()
            .newTest((expression, value) -> expression.isNegative())
                .value(-1)
                .expectedState(PASSED)
                .expectedName("isNegative")
                .expectedExpectedValue("< 0")
                .expectedActualValue("-1")
                .addTest();
    }

    private void isNotNegativeTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
        builder.newTest((expression, value) -> expression.isNotNegative())
                .value(1)
                .expectedState(PASSED)
                .expectedName("isNotNegative")
                .expectedExpectedValue(">= 0")
                .expectedActualValue("1")
                .addTest()
            .newTest((expression, value) -> expression.isNotNegative())
                .value(0)
                .expectedState(PASSED)
                .expectedName("isNotNegative")
                .expectedExpectedValue(">= 0")
                .expectedActualValue("0")
                .addTest()
            .newTest((expression, value) -> expression.isNotNegative())
                .value(-1)
                .expectedState(FAILED)
                .expectedName("isNotNegative")
                .expectedExpectedValue(">= 0")
                .expectedActualValue("-1")
                .addTest();
    }

    private void isEvenTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
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

    private void isOddTestCases(final ExpressionTestCaseBuilder<Integer, BoxedIntegerExpression> builder) {
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
