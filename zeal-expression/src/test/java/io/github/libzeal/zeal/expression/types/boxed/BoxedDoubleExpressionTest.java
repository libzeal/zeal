package io.github.libzeal.zeal.expression.types.boxed;

import io.github.libzeal.zeal.expression.test.ExpressionTestCaseBuilder;
import io.github.libzeal.zeal.expression.types.ObjectExpressionTest;

import static io.github.libzeal.zeal.expression.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.evaluation.Result.PASSED;
import static io.github.libzeal.zeal.expression.types.ObjectExpression.stringify;

class BoxedDoubleExpressionTest extends ObjectExpressionTest<Double, BoxedDoubleExpression>  {

    @Override
    protected BoxedDoubleExpression expression(final Double value) {
        return new BoxedDoubleExpression(value);
    }

    @Override
    protected Double exampleValue1() {
        return 1.0;
    }

    @Override
    protected Double exampleValue2() {
        return 2.0;
    }

    @Override
    protected void extendTestCases(ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        isEqualToTestCases(builder);
        isNotEqualToTestCases(builder);
        isWithinDeltaTestCases(builder);
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
        isFiniteTestCases(builder);
        isInfiniteTestCases(builder);
        isNanTestCases(builder);
    }

    private void isEqualToTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.isEqualTo(1.0))
                .value(1.0)
                .expectedState(PASSED)
                .expectedName("isEqualTo[" + stringify(1.0) + "]")
                .expectedExpectedValue(stringify(1.0))
                .expectedActualValue(stringify(1.0))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0))
                .value(2.0)
                .expectedState(FAILED)
                .expectedName("isEqualTo[" + stringify(1.0) + "]")
                .expectedExpectedValue(stringify(1.0))
                .expectedActualValue(stringify(2.0))
                .addTest();
    }

    private void isNotEqualToTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.isNotEqualTo(1.0))
                .value(1.0)
                .expectedState(FAILED)
                .expectedName("isNotEqualTo[" + stringify(1.0) + "]")
                .expectedExpectedValue("not[" + stringify(1.0) + "]")
                .expectedActualValue(stringify(1.0))
                .addTest()
            .newTest((expression, value) -> expression.isNotEqualTo(1.0))
                .value(2.0)
                .expectedState(PASSED)
                .expectedName("isNotEqualTo[" + stringify(1.0) + "]")
                .expectedExpectedValue("not[" + stringify(1.0) + "]")
                .expectedActualValue(stringify(2.0))
                .addTest();
    }

    private void isWithinDeltaTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        // FIXME Add test cases
    }

    private void isLessThanTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.isLessThan(1.0))
                .value(1.0)
                .expectedState(FAILED)
                .expectedName("isLessThan[" + stringify(1.0) + "]")
                .expectedExpectedValue("< " + stringify(1.0))
                .expectedActualValue(stringify(1.0))
                .addTest()
            .newTest((expression, value) -> expression.isLessThan(1.0))
                .value(2.0)
                .expectedState(FAILED)
                .expectedName("isLessThan[" + stringify(1.0) + "]")
                .expectedExpectedValue("< " + stringify(1.0))
                .expectedActualValue(stringify(2.0))
                .addTest()
            .newTest((expression, value) -> expression.isLessThan(1.0))
                .value(0.0)
                .expectedState(PASSED)
                .expectedName("isLessThan[" + stringify(1.0) + "]")
                .expectedExpectedValue("< " + stringify(1.0))
                .expectedActualValue(stringify(0.0))
                .addTest();
    }

    private void ltTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.lt(1.0))
                .value(1.0)
                .expectedState(FAILED)
                .expectedName("isLessThan[" + stringify(1.0) + "]")
                .expectedExpectedValue("< " + stringify(1.0))
                .expectedActualValue(stringify(1.0))
                .addTest()
            .newTest((expression, value) -> expression.lt(1.0))
                .value(2.0)
                .expectedState(FAILED)
                .expectedName("isLessThan[" + stringify(1.0) + "]")
                .expectedExpectedValue("< " + stringify(1.0))
                .expectedActualValue(stringify(2.0))
                .addTest()
            .newTest((expression, value) -> expression.lt(1.0))
                .value(0.0)
                .expectedState(PASSED)
                .expectedName("isLessThan[" + stringify(1.0) + "]")
                .expectedExpectedValue("< " + stringify(1.0))
                .expectedActualValue(stringify(0.0))
                .addTest();
    }

    private void isGreaterThanTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.isGreaterThan(1.0))
                .value(1.0)
                .expectedState(FAILED)
                .expectedName("isGreaterThan[" + stringify(1.0) + "]")
                .expectedExpectedValue("> " + stringify(1.0))
                .expectedActualValue(stringify(1.0))
                .addTest()
            .newTest((expression, value) -> expression.isGreaterThan(1.0))
                .value(2.0)
                .expectedState(PASSED)
                .expectedName("isGreaterThan[" + stringify(1.0) + "]")
                .expectedExpectedValue("> " + stringify(1.0))
                .expectedActualValue(stringify(2.0))
                .addTest()
            .newTest((expression, value) -> expression.isGreaterThan(1.0))
                .value(0.0)
                .expectedState(FAILED)
                .expectedName("isGreaterThan[" + stringify(1.0) + "]")
                .expectedExpectedValue("> " + stringify(1.0))
                .expectedActualValue(stringify(0.0))
                .addTest();
    }

    private void gtTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.gt(1.0))
                .value(1.0)
                .expectedState(FAILED)
                .expectedName("isGreaterThan[" + stringify(1.0) + "]")
                .expectedExpectedValue("> " + stringify(1.0))
                .expectedActualValue(stringify(1.0))
                .addTest()
            .newTest((expression, value) -> expression.gt(1.0))
                .value(2.0)
                .expectedState(PASSED)
                .expectedName("isGreaterThan[" + stringify(1.0) + "]")
                .expectedExpectedValue("> " + stringify(1.0))
                .expectedActualValue(stringify(2.0))
                .addTest()
            .newTest((expression, value) -> expression.gt(1.0))
                .value(0.0)
                .expectedState(FAILED)
                .expectedName("isGreaterThan[" + stringify(1.0) + "]")
                .expectedExpectedValue("> " + stringify(1.0))
                .expectedActualValue(stringify(0.0))
                .addTest();
    }

    private void isLessThanOrEqualToTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.isLessThanOrEqualTo(1.0))
                .value(1.0)
                .expectedState(PASSED)
                .expectedName("isLessThanOrEqualTo[" + stringify(1.0) + "]")
                .expectedExpectedValue("<= " + stringify(1.0))
                .expectedActualValue(stringify(1.0))
                .addTest()
            .newTest((expression, value) -> expression.isLessThanOrEqualTo(1.0))
                .value(2.0)
                .expectedState(FAILED)
                .expectedName("isLessThanOrEqualTo[" + stringify(1.0) + "]")
                .expectedExpectedValue("<= " + stringify(1.0))
                .expectedActualValue(stringify(2.0))
                .addTest()
            .newTest((expression, value) -> expression.isLessThanOrEqualTo(1.0))
                .value(0.0)
                .expectedState(PASSED)
                .expectedName("isLessThanOrEqualTo[" + stringify(1.0) + "]")
                .expectedExpectedValue("<= " + stringify(1.0))
                .expectedActualValue(stringify(0.0))
                .addTest();
    }

    private void lteTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.lte(1.0))
                .value(1.0)
                .expectedState(PASSED)
                .expectedName("isLessThanOrEqualTo[" + stringify(1.0) + "]")
                .expectedExpectedValue("<= " + stringify(1.0))
                .expectedActualValue(stringify(1.0))
                .addTest()
            .newTest((expression, value) -> expression.lte(1.0))
                .value(2.0)
                .expectedState(FAILED)
                .expectedName("isLessThanOrEqualTo[" + stringify(1.0) + "]")
                .expectedExpectedValue("<= " + stringify(1.0))
                .expectedActualValue(stringify(2.0))
                .addTest()
            .newTest((expression, value) -> expression.lte(1.0))
                .value(0.0)
                .expectedState(PASSED)
                .expectedName("isLessThanOrEqualTo[" + stringify(1.0) + "]")
                .expectedExpectedValue("<= " + stringify(1.0))
                .expectedActualValue(stringify(0.0))
                .addTest();
    }

    private void isGreaterThanOrEqualToTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.isGreaterThanOrEqualTo(1.0))
                .value(1.0)
                .expectedState(PASSED)
                .expectedName("isGreaterThanOrEqualTo[" + stringify(1.0) + "]")
                .expectedExpectedValue(">= " + stringify(1.0))
                .expectedActualValue(stringify(1.0))
                .addTest()
            .newTest((expression, value) -> expression.isGreaterThanOrEqualTo(1.0))
                .value(2.0)
                .expectedState(PASSED)
                .expectedName("isGreaterThanOrEqualTo[" + stringify(1.0) + "]")
                .expectedExpectedValue(">= " + stringify(1.0))
                .expectedActualValue(stringify(2.0))
                .addTest()
            .newTest((expression, value) -> expression.isGreaterThanOrEqualTo(1.0))
                .value(0.0)
                .expectedState(FAILED)
                .expectedName("isGreaterThanOrEqualTo[" + stringify(1.0) + "]")
                .expectedExpectedValue(">= " + stringify(1.0))
                .expectedActualValue(stringify(0.0))
                .addTest();
    }

    private void gteTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.gte(1.0))
                .value(1.0)
                .expectedState(PASSED)
                .expectedName("isGreaterThanOrEqualTo[" + stringify(1.0) + "]")
                .expectedExpectedValue(">= " + stringify(1.0))
                .expectedActualValue(stringify(1.0))
                .addTest()
            .newTest((expression, value) -> expression.gte(1.0))
                .value(2.0)
                .expectedState(PASSED)
                .expectedName("isGreaterThanOrEqualTo[" + stringify(1.0) + "]")
                .expectedExpectedValue(">= " + stringify(1.0))
                .expectedActualValue(stringify(2.0))
                .addTest()
            .newTest((expression, value) -> expression.gte(1.0))
                .value(0.0)
                .expectedState(FAILED)
                .expectedName("isGreaterThanOrEqualTo[" + stringify(1.0) + "]")
                .expectedExpectedValue(">= " + stringify(1.0))
                .expectedActualValue(stringify(0.0))
                .addTest();
    }

    private void isMaxValueTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.isMaxValue())
                .value(Double.MAX_VALUE)
                .expectedState(PASSED)
                .expectedName("isMax")
                .expectedExpectedValue(String.valueOf(Double.MAX_VALUE))
                .expectedActualValue(String.valueOf(Double.MAX_VALUE))
                .addTest()
            .newTest((expression, value) -> expression.isMaxValue())
                .value(0.0)
                .expectedState(FAILED)
                .expectedName("isMax")
                .expectedExpectedValue(String.valueOf(Double.MAX_VALUE))
                .expectedActualValue(String.valueOf(0.0))
                .addTest();
    }

    private void isMinValueTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.isMinValue())
                .value(Double.MIN_VALUE)
                .expectedState(PASSED)
                .expectedName("isMin")
                .expectedExpectedValue(String.valueOf(Double.MIN_VALUE))
                .expectedActualValue(String.valueOf(Double.MIN_VALUE))
                .addTest()
            .newTest((expression, value) -> expression.isMinValue())
                .value(Double.MIN_VALUE + 1)
                .expectedState(FAILED)
                .expectedName("isMin")
                .expectedExpectedValue(String.valueOf(Double.MIN_VALUE))
                .expectedActualValue(String.valueOf(Double.MIN_VALUE + 1))
                .addTest();
    }

    private void isZeroTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.isZero())
                .value(1.0)
                .expectedState(FAILED)
                .expectedName("isZero")
                .expectedExpectedValue(stringify(0.0))
                .expectedActualValue(stringify(1.0))
                .addTest()
            .newTest((expression, value) -> expression.isZero())
                .value(0.0)
                .expectedState(PASSED)
                .expectedName("isZero")
                .expectedExpectedValue(stringify(0.0))
                .expectedActualValue(stringify(0.0))
                .addTest()
            .newTest((expression, value) -> expression.isZero())
                .value(-1.0)
                .expectedState(FAILED)
                .expectedName("isZero")
                .expectedExpectedValue(stringify(0.0))
                .expectedActualValue(stringify(-1.0))
                .addTest();
    }

    private void isPositiveTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.isPositive())
                .value(1.0)
                .expectedState(PASSED)
                .expectedName("isPositive")
                .expectedExpectedValue("> " + stringify(0.0))
                .expectedActualValue(stringify(1.0))
                .addTest()
            .newTest((expression, value) -> expression.isPositive())
                .value(0.0)
                .expectedState(FAILED)
                .expectedName("isPositive")
                .expectedExpectedValue("> " + stringify(0.0))
                .expectedActualValue(stringify(0.0))
                .addTest()
            .newTest((expression, value) -> expression.isPositive())
                .value(-1.0)
                .expectedState(FAILED)
                .expectedName("isPositive")
                .expectedExpectedValue("> " + stringify(0.0))
                .expectedActualValue(stringify(-1.0))
                .addTest();
    }

    private void isNotPositiveTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.isNotPositive())
                .value(1.0)
                .expectedState(FAILED)
                .expectedName("isNotPositive")
                .expectedExpectedValue("<= " + stringify(0.0))
                .expectedActualValue(stringify(1.0))
                .addTest()
            .newTest((expression, value) -> expression.isNotPositive())
                .value(0.0)
                .expectedState(PASSED)
                .expectedName("isNotPositive")
                .expectedExpectedValue("<= " + stringify(0.0))
                .expectedActualValue(stringify(0.0))
                .addTest()
            .newTest((expression, value) -> expression.isNotPositive())
                .value(-1.0)
                .expectedState(PASSED)
                .expectedName("isNotPositive")
                .expectedExpectedValue("<= " + stringify(0.0))
                .expectedActualValue(stringify(-1.0))
                .addTest();
    }

    private void isNegativeTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.isNegative())
                .value(1.0)
                .expectedState(FAILED)
                .expectedName("isNegative")
                .expectedExpectedValue("< " + stringify(0.0))
                .expectedActualValue(stringify(1.0))
                .addTest()
            .newTest((expression, value) -> expression.isNegative())
                .value(0.0)
                .expectedState(FAILED)
                .expectedName("isNegative")
                .expectedExpectedValue("< " + stringify(0.0))
                .expectedActualValue(stringify(0.0))
                .addTest()
            .newTest((expression, value) -> expression.isNegative())
                .value(-1.0)
                .expectedState(PASSED)
                .expectedName("isNegative")
                .expectedExpectedValue("< " + stringify(0.0))
                .expectedActualValue(stringify(-1.0))
                .addTest();
    }

    private void isNotNegativeTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.isNotNegative())
                .value(1.0)
                .expectedState(PASSED)
                .expectedName("isNotNegative")
                .expectedExpectedValue(">= " + stringify(0.0))
                .expectedActualValue(stringify(1.0))
                .addTest()
            .newTest((expression, value) -> expression.isNotNegative())
                .value(0.0)
                .expectedState(PASSED)
                .expectedName("isNotNegative")
                .expectedExpectedValue(">= " + stringify(0.0))
                .expectedActualValue(stringify(0.0))
                .addTest()
            .newTest((expression, value) -> expression.isNotNegative())
                .value(-1.0)
                .expectedState(FAILED)
                .expectedName("isNotNegative")
                .expectedExpectedValue(">= " + stringify(0.0))
                .expectedActualValue(stringify(-1.0))
                .addTest();
    }

    private void isFiniteTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.isFinite())
                .value(Double.POSITIVE_INFINITY)
                .expectedState(FAILED)
                .expectedName("isFinite")
                .expectedExpectedValue("finite")
                .expectedActualValue(stringify(Double.POSITIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isFinite())
                .value(Double.NEGATIVE_INFINITY)
                .expectedState(FAILED)
                .expectedName("isFinite")
                .expectedExpectedValue("finite")
                .expectedActualValue(stringify(Double.NEGATIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isFinite())
                .value(0.0)
                .expectedState(PASSED)
                .expectedName("isFinite")
                .expectedExpectedValue("finite")
                .expectedActualValue(stringify(0.0))
                .addTest();
    }

    private void isInfiniteTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.isInfinite())
                .value(Double.POSITIVE_INFINITY)
                .expectedState(PASSED)
                .expectedName("isInfinite")
                .expectedExpectedValue("infinite")
                .expectedActualValue(stringify(Double.POSITIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isInfinite())
                .value(Double.NEGATIVE_INFINITY)
                .expectedState(PASSED)
                .expectedName("isInfinite")
                .expectedExpectedValue("infinite")
                .expectedActualValue(stringify(Double.NEGATIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isInfinite())
                .value(0.0)
                .expectedState(FAILED)
                .expectedName("isInfinite")
                .expectedExpectedValue("infinite")
                .expectedActualValue(stringify(0.0))
                .addTest();
    }

    private void isNanTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.isNaN())
                .value(Double.NaN)
                .expectedState(PASSED)
                .expectedName("isNaN")
                .expectedExpectedValue("NaN")
                .expectedActualValue(stringify(Double.NaN))
                .addTest()
            .newTest((expression, value) -> expression.isNaN())
                .value(0.0)
                .expectedState(FAILED)
                .expectedName("isNaN")
                .expectedExpectedValue("NaN")
                .expectedActualValue(stringify(0.0))
                .addTest();
    }
}
