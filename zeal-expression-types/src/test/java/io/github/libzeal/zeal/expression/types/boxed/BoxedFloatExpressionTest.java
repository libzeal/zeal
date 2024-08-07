package io.github.libzeal.zeal.expression.types.boxed;

import io.github.libzeal.zeal.expression.types.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.lang.evaluation.Result.PASSED;
import static io.github.libzeal.zeal.expression.types.ObjectExpression.stringify;

class BoxedFloatExpressionTest extends BoxedNumericExpressionTest<Float, BoxedFloatExpression>  {

    @Override
    protected BoxedFloatExpression expression(final Float value) {
        return new BoxedFloatExpression(value);
    }

    @Override
    protected Float exampleNegativeValue() {
        return -1.0f;
    }

    @Override
    protected Float exampleZeroValue() {
        return 0.0f;
    }

    @Override
    protected Float examplePositiveValue() {
        return 1.0f;
    }

    @Override
    protected Float exampleMaximumValue() {
        return Float.MAX_VALUE;
    }

    @Override
    protected Float exampleMinimumValue() {
        return Float.MIN_VALUE;
    }

    @Override
    protected void customTestCases(ExpressionTestCaseBuilder<Float, BoxedFloatExpression> builder) {
        isEqualToTestCases(builder);
        isFiniteTestCases(builder);
        isInfiniteTestCases(builder);
        isNanTestCases(builder);
    }

    private void isEqualToTestCases(final ExpressionTestCaseBuilder<Float, BoxedFloatExpression> builder) {
        builder.newTest((expression, value) -> expression.isEqualTo(1.0f, 1.0f))
                .value(-0.1f)
                .expectedState(FAILED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpectedValue("1.0 +/- 1.0")
                .expectedActualValue(stringify(-0.1f))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0f, 1.0f))
                .value(0.0f)
                .expectedState(PASSED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpectedValue("1.0 +/- 1.0")
                .expectedActualValue(stringify(0.0f))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0f, 1.0f))
                .value(0.1f)
                .expectedState(PASSED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpectedValue("1.0 +/- 1.0")
                .expectedActualValue(stringify(0.1f))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0f, 1.0f))
                .value(1.9f)
                .expectedState(PASSED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpectedValue("1.0 +/- 1.0")
                .expectedActualValue(stringify(1.9f))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0f, 1.0f))
                .value(2.0f)
                .expectedState(PASSED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpectedValue("1.0 +/- 1.0")
                .expectedActualValue(stringify(2.0))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0f, 1.0f))
                .value(2.1f)
                .expectedState(FAILED)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpectedValue("1.0 +/- 1.0")
                .expectedActualValue(stringify(2.1f))
                .addTest();
    }

    private void isFiniteTestCases(final ExpressionTestCaseBuilder<Float, BoxedFloatExpression> builder) {
        builder.newTest((expression, value) -> expression.isFinite())
                .value(Float.POSITIVE_INFINITY)
                .expectedState(FAILED)
                .expectedName("isFinite")
                .expectedExpectedValue("finite")
                .expectedActualValue(stringify(Float.POSITIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isFinite())
                .value(Float.NEGATIVE_INFINITY)
                .expectedState(FAILED)
                .expectedName("isFinite")
                .expectedExpectedValue("finite")
                .expectedActualValue(stringify(Float.NEGATIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isFinite())
                .value(exampleZeroValue())
                .expectedState(PASSED)
                .expectedName("isFinite")
                .expectedExpectedValue("finite")
                .expectedActualValue(stringify(exampleZeroValue()))
                .addTest();
    }

    private void isInfiniteTestCases(final ExpressionTestCaseBuilder<Float, BoxedFloatExpression> builder) {
        builder.newTest((expression, value) -> expression.isInfinite())
                .value(Float.POSITIVE_INFINITY)
                .expectedState(PASSED)
                .expectedName("isInfinite")
                .expectedExpectedValue("infinite")
                .expectedActualValue(stringify(Float.POSITIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isInfinite())
                .value(Float.NEGATIVE_INFINITY)
                .expectedState(PASSED)
                .expectedName("isInfinite")
                .expectedExpectedValue("infinite")
                .expectedActualValue(stringify(Float.NEGATIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isInfinite())
                .value(exampleZeroValue())
                .expectedState(FAILED)
                .expectedName("isInfinite")
                .expectedExpectedValue("infinite")
                .expectedActualValue(stringify(exampleZeroValue()))
                .addTest();
    }

    private void isNanTestCases(final ExpressionTestCaseBuilder<Float, BoxedFloatExpression> builder) {
        builder.newTest((expression, value) -> expression.isNaN())
                .value(Float.NaN)
                .expectedState(PASSED)
                .expectedName("isNaN")
                .expectedExpectedValue("NaN")
                .expectedActualValue(stringify(Float.NaN))
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
