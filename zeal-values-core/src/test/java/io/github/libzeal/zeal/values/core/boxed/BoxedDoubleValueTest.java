package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.logic.util.Formatter;
import io.github.libzeal.zeal.values.core.basic.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

class BoxedDoubleValueTest extends BoxedNumberValueTest<Double, BoxedDoubleValue> {

    @Override
    protected BoxedDoubleValue expression(final Double value) {
        return new BoxedDoubleValue(value);
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
    protected void customTestCases(ExpressionTestCaseBuilder<Double, BoxedDoubleValue> builder) {
        isEqualToTestCases(builder);
        isFiniteTestCases(builder);
        isInfiniteTestCases(builder);
        isNanTestCases(builder);
    }

    private void isEqualToTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleValue> builder) {
        builder.newTest((expression, value) -> expression.isEqualTo(1.0, 1.0))
                .subject(-0.1)
                .expectedState(FALSE)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(Formatter.stringify(-0.1))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0, 1.0))
                .subject(0.0)
                .expectedState(TRUE)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(Formatter.stringify(0.0))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0, 1.0))
                .subject(0.1)
                .expectedState(TRUE)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(Formatter.stringify(0.1))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0, 1.0))
                .subject(1.9)
                .expectedState(TRUE)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(Formatter.stringify(1.9))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0, 1.0))
                .subject(2.0)
                .expectedState(TRUE)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(Formatter.stringify(2.0))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(1.0, 1.0))
                .subject(2.1)
                .expectedState(FALSE)
                .expectedName("isEqualTo[1.0 +/- 1.0]")
                .expectedExpected("1.0 +/- 1.0")
                .expectedActual(Formatter.stringify(2.1))
                .addTest();
    }

    private void isFiniteTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleValue> builder) {
        Object o = exampleZeroValue();
        builder.newTest((expression, value) -> expression.isFinite())
                .subject(Double.POSITIVE_INFINITY)
                .expectedState(FALSE)
                .expectedName("isFinite")
                .expectedExpected("finite")
                .expectedActual(Formatter.stringify(Double.POSITIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isFinite())
                .subject(Double.NEGATIVE_INFINITY)
                .expectedState(FALSE)
                .expectedName("isFinite")
                .expectedExpected("finite")
                .expectedActual(Formatter.stringify(Double.NEGATIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isFinite())
                .subject(exampleZeroValue())
                .expectedState(TRUE)
                .expectedName("isFinite")
                .expectedExpected("finite")
                .expectedActual(Formatter.stringify(o))
                .addTest();
    }

    private void isInfiniteTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleValue> builder) {
        Object o = exampleZeroValue();
        builder.newTest((expression, value) -> expression.isInfinite())
                .subject(Double.POSITIVE_INFINITY)
                .expectedState(TRUE)
                .expectedName("isInfinite")
                .expectedExpected("infinite")
                .expectedActual(Formatter.stringify(Double.POSITIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isInfinite())
                .subject(Double.NEGATIVE_INFINITY)
                .expectedState(TRUE)
                .expectedName("isInfinite")
                .expectedExpected("infinite")
                .expectedActual(Formatter.stringify(Double.NEGATIVE_INFINITY))
                .addTest()
            .newTest((expression, value) -> expression.isInfinite())
                .subject(exampleZeroValue())
                .expectedState(FALSE)
                .expectedName("isInfinite")
                .expectedExpected("infinite")
                .expectedActual(Formatter.stringify(o))
                .addTest();
    }

    private void isNanTestCases(final ExpressionTestCaseBuilder<Double, BoxedDoubleValue> builder) {
        Object o = exampleZeroValue();
        builder.newTest((expression, value) -> expression.isNaN())
                .subject(Double.NaN)
                .expectedState(TRUE)
                .expectedName("isNaN")
                .expectedExpected("NaN")
                .expectedActual(Formatter.stringify(Double.NaN))
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
