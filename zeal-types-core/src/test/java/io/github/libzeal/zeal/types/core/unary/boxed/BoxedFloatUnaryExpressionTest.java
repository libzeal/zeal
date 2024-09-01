package io.github.libzeal.zeal.types.core.unary.boxed;

import io.github.libzeal.zeal.logic.util.Formatter;
import io.github.libzeal.zeal.types.core.unary.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

class BoxedFloatUnaryExpressionTest extends BoxedNumberUnaryExpressionTest<Float, BoxedFloatUnaryExpression> {

    @Override
    protected BoxedFloatUnaryExpression expression(final Float value) {
        return new BoxedFloatUnaryExpression(value);
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
    protected void customTestCases(ExpressionTestCaseBuilder<Float, BoxedFloatUnaryExpression> builder) {
        isEqualToTestCases(builder);
        isFiniteTestCases(builder);
        isInfiniteTestCases(builder);
        isNanTestCases(builder);
    }

    private void isEqualToTestCases(final ExpressionTestCaseBuilder<Float, BoxedFloatUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isEqualTo(1.0f, 1.0f))
                .subject(-0.1f)
                .expectedState(FALSE)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(Formatter.stringify(-0.1f))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0f, 1.0f))
                .subject(0.0f)
                .expectedState(TRUE)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(Formatter.stringify(0.0f))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0f, 1.0f))
                .subject(0.1f)
                .expectedState(TRUE)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(Formatter.stringify(0.1f))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0f, 1.0f))
                .subject(1.9f)
                .expectedState(TRUE)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(Formatter.stringify(1.9f))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0f, 1.0f))
                .subject(2.0f)
                .expectedState(TRUE)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(Formatter.stringify(2.0))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0f, 1.0f))
                .subject(2.1f)
                .expectedState(FALSE)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(Formatter.stringify(2.1f))
                .addTest();
    }

    private void isFiniteTestCases(final ExpressionTestCaseBuilder<Float, BoxedFloatUnaryExpression> builder) {
        Object o = exampleZeroValue();
        builder.newTest((expression, value) -> expression.isFinite())
                .subject(Float.POSITIVE_INFINITY)
                .expectedState(FALSE)
                .expectedName("isFinite")
                .expectedExpected("finite")
                .expectedActual(Formatter.stringify(Float.POSITIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isFinite())
                .subject(Float.NEGATIVE_INFINITY)
                .expectedState(FALSE)
                .expectedName("isFinite")
                .expectedExpected("finite")
                .expectedActual(Formatter.stringify(Float.NEGATIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isFinite())
                .subject(exampleZeroValue())
                .expectedState(TRUE)
                .expectedName("isFinite")
                .expectedExpected("finite")
                .expectedActual(Formatter.stringify(o))
                .addTest();
    }

    private void isInfiniteTestCases(final ExpressionTestCaseBuilder<Float, BoxedFloatUnaryExpression> builder) {
        Object o = exampleZeroValue();
        builder.newTest((expression, value) -> expression.isInfinite())
                .subject(Float.POSITIVE_INFINITY)
                .expectedState(TRUE)
                .expectedName("isInfinite")
                .expectedExpected("infinite")
                .expectedActual(Formatter.stringify(Float.POSITIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isInfinite())
                .subject(Float.NEGATIVE_INFINITY)
                .expectedState(TRUE)
                .expectedName("isInfinite")
                .expectedExpected("infinite")
                .expectedActual(Formatter.stringify(Float.NEGATIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isInfinite())
                .subject(exampleZeroValue())
                .expectedState(FALSE)
                .expectedName("isInfinite")
                .expectedExpected("infinite")
                .expectedActual(Formatter.stringify(o))
                .addTest();
    }

    private void isNanTestCases(final ExpressionTestCaseBuilder<Float, BoxedFloatUnaryExpression> builder) {
        Object o = exampleZeroValue();
        builder.newTest((expression, value) -> expression.isNaN())
                .subject(Float.NaN)
                .expectedState(TRUE)
                .expectedName("isNaN")
                .expectedExpected("NaN")
                .expectedActual(Formatter.stringify(Float.NaN))
                .addTest()
            .newTest((expression, value) -> expression.isNaN())
                .subject(exampleZeroValue())
                .expectedState(FALSE)
                .expectedName("isNaN")
                .expectedExpected("NaN")
                .expectedActual(Formatter.stringify(o))
                .addTest();
    }
}
