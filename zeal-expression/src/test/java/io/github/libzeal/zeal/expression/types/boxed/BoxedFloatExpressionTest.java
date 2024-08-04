package io.github.libzeal.zeal.expression.types.boxed;

import io.github.libzeal.zeal.expression.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.expression.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.evaluation.Result.PASSED;
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
        isWithinDeltaTestCases(builder);
        isFiniteTestCases(builder);
        isInfiniteTestCases(builder);
        isNanTestCases(builder);
    }

    private void isWithinDeltaTestCases(final ExpressionTestCaseBuilder<Float, BoxedFloatExpression> builder) {
        // FIXME Add test cases
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
