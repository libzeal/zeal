package io.github.libzeal.zeal.expression.types.boxed;

import io.github.libzeal.zeal.expression.test.ExpressionTestCaseBuilder;
import io.github.libzeal.zeal.expression.types.ObjectExpression;
import io.github.libzeal.zeal.expression.types.ObjectExpressionTest;

import static io.github.libzeal.zeal.expression.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.evaluation.Result.PASSED;
import static io.github.libzeal.zeal.expression.types.ObjectExpression.stringify;

public abstract class BoxedNumericExpressionTest<T, E extends ObjectExpression<T, E>>
    extends ObjectExpressionTest<T, E> {

    @Override
    protected final void extendTestCases(ExpressionTestCaseBuilder<T, E> builder) {
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
        customTestCases(builder);
    }

    protected abstract void customTestCases(final ExpressionTestCaseBuilder<T, E> builder);

    private void isEqualToTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T value1 = exampleValue1();
        final T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.isEqualTo(value1))
                .value(value1)
                .expectedState(PASSED)
                .expectedName("isEqualTo[" + stringify(value1) + "]")
                .expectedExpectedValue(stringify(value1))
                .expectedActualValue(stringify(value1))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1L))
                .value(value2)
                .expectedState(FAILED)
                .expectedName("isEqualTo[" + stringify(value1) + "]")
                .expectedExpectedValue(stringify(value1))
                .expectedActualValue(stringify(value2))
                .addTest();
    }

    private void isNotEqualToTestCases(final ExpressionTestCaseBuilder<T, E> builder) {
        builder.newTest((expression, value) -> expression.isNotEqualTo(1L))
            .value(1L)
            .expectedState(FAILED)
            .expectedName("isNotEqualTo[1]")
            .expectedExpectedValue("not[1]")
            .expectedActualValue("1")
            .addTest()
            .newTest((expression, value) -> expression.isNotEqualTo(1L))
            .value(2L)
            .expectedState(PASSED)
            .expectedName("isNotEqualTo[1]")
            .expectedExpectedValue("not[1]")
            .expectedActualValue("2")
            .addTest();
    }

    private void isLessThanTestCases(final ExpressionTestCaseBuilder<T, E> builder) {
        builder.newTest((expression, value) -> expression.isLessThan(1L))
            .value(1L)
            .expectedState(FAILED)
            .expectedName("isLessThan[1]")
            .expectedExpectedValue("< 1")
            .expectedActualValue("1")
            .addTest()
            .newTest((expression, value) -> expression.isLessThan(1L))
            .value(2L)
            .expectedState(FAILED)
            .expectedName("isLessThan[1]")
            .expectedExpectedValue("< 1")
            .expectedActualValue("2")
            .addTest()
            .newTest((expression, value) -> expression.isLessThan(1L))
            .value(0L)
            .expectedState(PASSED)
            .expectedName("isLessThan[1]")
            .expectedExpectedValue("< 1")
            .expectedActualValue("0")
            .addTest();
    }

    private void ltTestCases(final ExpressionTestCaseBuilder<T, E> builder) {
        builder.newTest((expression, value) -> expression.lt(1L))
            .value(1L)
            .expectedState(FAILED)
            .expectedName("isLessThan[1]")
            .expectedExpectedValue("< 1")
            .expectedActualValue("1")
            .addTest()
            .newTest((expression, value) -> expression.lt(1L))
            .value(2L)
            .expectedState(FAILED)
            .expectedName("isLessThan[1]")
            .expectedExpectedValue("< 1")
            .expectedActualValue("2")
            .addTest()
            .newTest((expression, value) -> expression.lt(1L))
            .value(0L)
            .expectedState(PASSED)
            .expectedName("isLessThan[1]")
            .expectedExpectedValue("< 1")
            .expectedActualValue("0")
            .addTest();
    }

    private void isGreaterThanTestCases(final ExpressionTestCaseBuilder<T, E> builder) {
        builder.newTest((expression, value) -> expression.isGreaterThan(1L))
            .value(1L)
            .expectedState(FAILED)
            .expectedName("isGreaterThan[1]")
            .expectedExpectedValue("> 1")
            .expectedActualValue("1")
            .addTest()
            .newTest((expression, value) -> expression.isGreaterThan(1L))
            .value(2L)
            .expectedState(PASSED)
            .expectedName("isGreaterThan[1]")
            .expectedExpectedValue("> 1")
            .expectedActualValue("2")
            .addTest()
            .newTest((expression, value) -> expression.isGreaterThan(1L))
            .value(0L)
            .expectedState(FAILED)
            .expectedName("isGreaterThan[1]")
            .expectedExpectedValue("> 1")
            .expectedActualValue("0")
            .addTest();
    }

    private void gtTestCases(final ExpressionTestCaseBuilder<T, E> builder) {
        builder.newTest((expression, value) -> expression.gt(1L))
            .value(1L)
            .expectedState(FAILED)
            .expectedName("isGreaterThan[1]")
            .expectedExpectedValue("> 1")
            .expectedActualValue("1")
            .addTest()
            .newTest((expression, value) -> expression.gt(1L))
            .value(2L)
            .expectedState(PASSED)
            .expectedName("isGreaterThan[1]")
            .expectedExpectedValue("> 1")
            .expectedActualValue("2")
            .addTest()
            .newTest((expression, value) -> expression.gt(1L))
            .value(0L)
            .expectedState(FAILED)
            .expectedName("isGreaterThan[1]")
            .expectedExpectedValue("> 1")
            .expectedActualValue("0")
            .addTest();
    }

    private void isLessThanOrEqualToTestCases(final ExpressionTestCaseBuilder<T, E> builder) {
        builder.newTest((expression, value) -> expression.isLessThanOrEqualTo(1L))
            .value(1L)
            .expectedState(PASSED)
            .expectedName("isLessThanOrEqualTo[1]")
            .expectedExpectedValue("<= 1")
            .expectedActualValue("1")
            .addTest()
            .newTest((expression, value) -> expression.isLessThanOrEqualTo(1L))
            .value(2L)
            .expectedState(FAILED)
            .expectedName("isLessThanOrEqualTo[1]")
            .expectedExpectedValue("<= 1")
            .expectedActualValue("2")
            .addTest()
            .newTest((expression, value) -> expression.isLessThanOrEqualTo(1L))
            .value(0L)
            .expectedState(PASSED)
            .expectedName("isLessThanOrEqualTo[1]")
            .expectedExpectedValue("<= 1")
            .expectedActualValue("0")
            .addTest();
    }

    private void lteTestCases(final ExpressionTestCaseBuilder<T, E> builder) {
        builder.newTest((expression, value) -> expression.lte(1L))
            .value(1L)
            .expectedState(PASSED)
            .expectedName("isLessThanOrEqualTo[1]")
            .expectedExpectedValue("<= 1")
            .expectedActualValue("1")
            .addTest()
            .newTest((expression, value) -> expression.lte(1L))
            .value(2L)
            .expectedState(FAILED)
            .expectedName("isLessThanOrEqualTo[1]")
            .expectedExpectedValue("<= 1")
            .expectedActualValue("2")
            .addTest()
            .newTest((expression, value) -> expression.lte(1L))
            .value(0L)
            .expectedState(PASSED)
            .expectedName("isLessThanOrEqualTo[1]")
            .expectedExpectedValue("<= 1")
            .expectedActualValue("0")
            .addTest();
    }

    private void isGreaterThanOrEqualToTestCases(final ExpressionTestCaseBuilder<T, E> builder) {
        builder.newTest((expression, value) -> expression.isGreaterThanOrEqualTo(1L))
            .value(1L)
            .expectedState(PASSED)
            .expectedName("isGreaterThanOrEqualTo[1]")
            .expectedExpectedValue(">= 1")
            .expectedActualValue("1")
            .addTest()
            .newTest((expression, value) -> expression.isGreaterThanOrEqualTo(1L))
            .value(2L)
            .expectedState(PASSED)
            .expectedName("isGreaterThanOrEqualTo[1]")
            .expectedExpectedValue(">= 1")
            .expectedActualValue("2")
            .addTest()
            .newTest((expression, value) -> expression.isGreaterThanOrEqualTo(1L))
            .value(0L)
            .expectedState(FAILED)
            .expectedName("isGreaterThanOrEqualTo[1]")
            .expectedExpectedValue(">= 1")
            .expectedActualValue("0")
            .addTest();
    }

    private void gteTestCases(final ExpressionTestCaseBuilder<T, E> builder) {
        builder.newTest((expression, value) -> expression.gte(1L))
            .value(1L)
            .expectedState(PASSED)
            .expectedName("isGreaterThanOrEqualTo[1]")
            .expectedExpectedValue(">= 1")
            .expectedActualValue("1")
            .addTest()
            .newTest((expression, value) -> expression.gte(1L))
            .value(2L)
            .expectedState(PASSED)
            .expectedName("isGreaterThanOrEqualTo[1]")
            .expectedExpectedValue(">= 1")
            .expectedActualValue("2")
            .addTest()
            .newTest((expression, value) -> expression.gte(1L))
            .value(0L)
            .expectedState(FAILED)
            .expectedName("isGreaterThanOrEqualTo[1]")
            .expectedExpectedValue(">= 1")
            .expectedActualValue("0")
            .addTest();
    }

    private void isMaxValueTestCases(final ExpressionTestCaseBuilder<T, E> builder) {
        builder.newTest((expression, value) -> expression.isMaxValue())
            .value(Long.MAX_VALUE)
            .expectedState(PASSED)
            .expectedName("isMax")
            .expectedExpectedValue(String.valueOf(Long.MAX_VALUE))
            .expectedActualValue(String.valueOf(Long.MAX_VALUE))
            .addTest()
            .newTest((expression, value) -> expression.isMaxValue())
            .value(Long.MAX_VALUE - 1)
            .expectedState(FAILED)
            .expectedName("isMax")
            .expectedExpectedValue(String.valueOf(Long.MAX_VALUE))
            .expectedActualValue(String.valueOf(Long.MAX_VALUE - 1))
            .addTest();
    }

    private void isMinValueTestCases(final ExpressionTestCaseBuilder<T, E> builder) {
        builder.newTest((expression, value) -> expression.isMinValue())
            .value(Long.MIN_VALUE)
            .expectedState(PASSED)
            .expectedName("isMin")
            .expectedExpectedValue(String.valueOf(Long.MIN_VALUE))
            .expectedActualValue(String.valueOf(Long.MIN_VALUE))
            .addTest()
            .newTest((expression, value) -> expression.isMinValue())
            .value(Long.MIN_VALUE + 1)
            .expectedState(FAILED)
            .expectedName("isMin")
            .expectedExpectedValue(String.valueOf(Long.MIN_VALUE))
            .expectedActualValue(String.valueOf(Long.MIN_VALUE + 1))
            .addTest();
    }

    private void isZeroTestCases(final ExpressionTestCaseBuilder<T, E> builder) {
        builder.newTest((expression, value) -> expression.isZero())
            .value(1L)
            .expectedState(FAILED)
            .expectedName("isZero")
            .expectedExpectedValue("0")
            .expectedActualValue("1")
            .addTest()
            .newTest((expression, value) -> expression.isZero())
            .value(0L)
            .expectedState(PASSED)
            .expectedName("isZero")
            .expectedExpectedValue("0")
            .expectedActualValue("0")
            .addTest()
            .newTest((expression, value) -> expression.isZero())
            .value(-1L)
            .expectedState(FAILED)
            .expectedName("isZero")
            .expectedExpectedValue("0")
            .expectedActualValue("-1")
            .addTest();
    }

    private void isPositiveTestCases(final ExpressionTestCaseBuilder<T, E> builder) {
        builder.newTest((expression, value) -> expression.isPositive())
            .value(1L)
            .expectedState(PASSED)
            .expectedName("isPositive")
            .expectedExpectedValue("> 0")
            .expectedActualValue("1")
            .addTest()
            .newTest((expression, value) -> expression.isPositive())
            .value(0L)
            .expectedState(FAILED)
            .expectedName("isPositive")
            .expectedExpectedValue("> 0")
            .expectedActualValue("0")
            .addTest()
            .newTest((expression, value) -> expression.isPositive())
            .value(-1L)
            .expectedState(FAILED)
            .expectedName("isPositive")
            .expectedExpectedValue("> 0")
            .expectedActualValue("-1")
            .addTest();
    }

    private void isNotPositiveTestCases(final ExpressionTestCaseBuilder<T, E> builder) {
        builder.newTest((expression, value) -> expression.isNotPositive())
            .value(1L)
            .expectedState(FAILED)
            .expectedName("isNotPositive")
            .expectedExpectedValue("<= 0")
            .expectedActualValue("1")
            .addTest()
            .newTest((expression, value) -> expression.isNotPositive())
            .value(0L)
            .expectedState(PASSED)
            .expectedName("isNotPositive")
            .expectedExpectedValue("<= 0")
            .expectedActualValue("0")
            .addTest()
            .newTest((expression, value) -> expression.isNotPositive())
            .value(-1L)
            .expectedState(PASSED)
            .expectedName("isNotPositive")
            .expectedExpectedValue("<= 0")
            .expectedActualValue("-1")
            .addTest();
    }

    private void isNegativeTestCases(final ExpressionTestCaseBuilder<T, E> builder) {
        builder.newTest((expression, value) -> expression.isNegative())
            .value(1L)
            .expectedState(FAILED)
            .expectedName("isNegative")
            .expectedExpectedValue("< 0")
            .expectedActualValue("1")
            .addTest()
            .newTest((expression, value) -> expression.isNegative())
            .value(0L)
            .expectedState(FAILED)
            .expectedName("isNegative")
            .expectedExpectedValue("< 0")
            .expectedActualValue("0")
            .addTest()
            .newTest((expression, value) -> expression.isNegative())
            .value(-1L)
            .expectedState(PASSED)
            .expectedName("isNegative")
            .expectedExpectedValue("< 0")
            .expectedActualValue("-1")
            .addTest();
    }

    private void isNotNegativeTestCases(final ExpressionTestCaseBuilder<T, E> builder) {
        builder.newTest((expression, value) -> expression.isNotNegative())
            .value(1L)
            .expectedState(PASSED)
            .expectedName("isNotNegative")
            .expectedExpectedValue(">= 0")
            .expectedActualValue("1")
            .addTest()
            .newTest((expression, value) -> expression.isNotNegative())
            .value(0L)
            .expectedState(PASSED)
            .expectedName("isNotNegative")
            .expectedExpectedValue(">= 0")
            .expectedActualValue("0")
            .addTest()
            .newTest((expression, value) -> expression.isNotNegative())
            .value(-1L)
            .expectedState(FAILED)
            .expectedName("isNotNegative")
            .expectedExpectedValue(">= 0")
            .expectedActualValue("-1")
            .addTest();
    }
}
