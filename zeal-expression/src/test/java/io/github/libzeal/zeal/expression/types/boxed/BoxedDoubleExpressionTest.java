package io.github.libzeal.zeal.expression.types.boxed;

import io.github.libzeal.zeal.expression.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.expression.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.evaluation.Result.PASSED;
import static io.github.libzeal.zeal.expression.types.ObjectExpression.stringify;

class BoxedDoubleExpressionTest extends BoxedNumericExpressionTest<Double, BoxedDoubleExpression>  {

    @Override
    protected BoxedDoubleExpression expression(final Double value) {
        return new BoxedDoubleExpression(value);
    }

    @Override
    protected Double exampleNegativeValue() {
        return -1.0;
    }

    @Override
    protected Double exampleZeroValue() {
        return 0.0;
    }

    @Override
    protected Double examplePositiveValue() {
        return 1.0;
    }

    @Override
    protected Double exampleMaximumValue() {
        return Double.MAX_VALUE;
    }

    @Override
    protected Double exampleMinimumValue() {
        return Double.MIN_VALUE;
    }

    @Override
    protected void customTestCases(ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        isEqualToTestCases(builder);
        isFiniteTestCases(builder);
        isInfiniteTestCases(builder);
        isNanTestCases(builder);
    }

    private void isEqualToTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleExpression> builder) {
        builder.newTest((expression, value) -> expression.isEqualTo(1.0, 1.0))
                .value(-0.1)
                .expectedState(FAILED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpectedValue("1.0 +/- 1.0")
                .expectedActualValue(stringify(-0.1))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0, 1.0))
                .value(0.0)
                .expectedState(PASSED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpectedValue("1.0 +/- 1.0")
                .expectedActualValue(stringify(0.0))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0, 1.0))
                .value(0.1)
                .expectedState(PASSED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpectedValue("1.0 +/- 1.0")
                .expectedActualValue(stringify(0.1))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0, 1.0))
                .value(1.9)
                .expectedState(PASSED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpectedValue("1.0 +/- 1.0")
                .expectedActualValue(stringify(1.9))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0, 1.0))
                .value(2.0)
                .expectedState(PASSED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpectedValue("1.0 +/- 1.0")
                .expectedActualValue(stringify(2.0))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0, 1.0))
                .value(2.1)
                .expectedState(FAILED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpectedValue("1.0 +/- 1.0")
                .expectedActualValue(stringify(2.1))
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
                .value(exampleZeroValue())
                .expectedState(PASSED)
                .expectedName("isFinite")
                .expectedExpectedValue("finite")
                .expectedActualValue(stringify(exampleZeroValue()))
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
                .value(exampleZeroValue())
                .expectedState(FAILED)
                .expectedName("isInfinite")
                .expectedExpectedValue("infinite")
                .expectedActualValue(stringify(exampleZeroValue()))
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
                .value(exampleZeroValue())
                .expectedState(FAILED)
                .expectedName("isNaN")
                .expectedExpectedValue("NaN")
                .expectedActualValue(stringify(exampleZeroValue()))
                .addTest();
    }
}
