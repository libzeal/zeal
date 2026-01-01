package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.logic.util.Formatter;
import io.github.libzeal.zeal.values.core.test.ExpressionTestCaseBuilder;
import io.github.libzeal.zeal.values.api.ObjectValueTest;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

public abstract class BoxedNumberValueTest<T extends Number, E extends BoxedNumberValue<T, E>>
    extends ObjectValueTest<T, E> {

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
        isNotZeroTestCases(builder);
        isPositiveTestCases(builder);
        isNotPositiveTestCases(builder);
        isNegativeTestCases(builder);
        isNotNegativeTestCases(builder);
        customTestCases(builder);
    }

    private void isEqualToTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T value1 = exampleValue1();
        final T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.isEqualTo(value1))
                .subject(value1)
                .expectedState(TRUE)
                .expectedName("isEqualTo[" + Formatter.stringify(value1) + "]")
                .expectedExpected(Formatter.stringify(value1))
                .expectedActual(Formatter.stringify(value1))
                .addTest()
            .newTest((expression, value) -> expression.isEqualTo(value1))
                .subject(value2)
                .expectedState(FALSE)
                .expectedName("isEqualTo[" + Formatter.stringify(value1) + "]")
                .expectedExpected(Formatter.stringify(value1))
                .expectedActual(Formatter.stringify(value2))
                .addTest();
    }

    private void isNotEqualToTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T value1 = exampleValue1();
        final T value2 = exampleValue2();

        builder.newTest((expression, value) -> expression.isNotEqualTo(value1))
                .subject(value1)
                .expectedState(FALSE)
                .expectedName("isNotEqualTo[" + Formatter.stringify(value1) + "]")
                .expectedExpected("not[" + Formatter.stringify(value1) + "]")
                .expectedActual(Formatter.stringify(value1))
                .addTest()
            .newTest((expression, value) -> expression.isNotEqualTo(value1))
                .subject(value2)
                .expectedState(TRUE)
                .expectedName("isNotEqualTo[" + Formatter.stringify(value1) + "]")
                .expectedExpected("not[" + Formatter.stringify(value1) + "]")
                .expectedActual(Formatter.stringify(value2))
                .addTest();
    }

    private void isLessThanTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T negative = exampleNegativeValue();
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();

        builder.newTest((expression, value) -> expression.isLessThan(zero))
                .subject(positive)
                .expectedState(FALSE)
                .expectedName("isLessThan[" + Formatter.stringify(zero) + "]")
                .expectedExpected("< " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isLessThan(zero))
                .subject(zero)
                .expectedState(FALSE)
                .expectedName("isLessThan[" + Formatter.stringify(zero) + "]")
                .expectedExpected("< " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isLessThan(zero))
                .subject(negative)
                .expectedState(TRUE)
                .expectedName("isLessThan[" + Formatter.stringify(zero) + "]")
                .expectedExpected("< " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(negative))
                .addTest();
    }

    private void ltTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T negative = exampleNegativeValue();
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();

        builder.newTest((expression, value) -> expression.lt(zero))
                .subject(positive)
                .expectedState(FALSE)
                .expectedName("isLessThan[" + Formatter.stringify(zero) + "]")
                .expectedExpected("< " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.lt(zero))
                .subject(zero)
                .expectedState(FALSE)
                .expectedName("isLessThan[" + Formatter.stringify(zero) + "]")
                .expectedExpected("< " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.lt(zero))
                .subject(negative)
                .expectedState(TRUE)
                .expectedName("isLessThan[" + Formatter.stringify(zero) + "]")
                .expectedExpected("< " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(negative))
                .addTest();
    }

    private void isGreaterThanTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T negative = exampleNegativeValue();
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();

        builder.newTest((expression, value) -> expression.isGreaterThan(zero))
                .subject(positive)
                .expectedState(TRUE)
                .expectedName("isGreaterThan[" + Formatter.stringify(zero) + "]")
                .expectedExpected("> " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isGreaterThan(zero))
                .subject(zero)
                .expectedState(FALSE)
                .expectedName("isGreaterThan[" + Formatter.stringify(zero) + "]")
                .expectedExpected("> " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isGreaterThan(zero))
                .subject(negative)
                .expectedState(FALSE)
                .expectedName("isGreaterThan[" + Formatter.stringify(zero) + "]")
                .expectedExpected("> " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(negative))
                .addTest();
    }

    private void gtTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T negative = exampleNegativeValue();
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();

        builder.newTest((expression, value) -> expression.gt(zero))
                .subject(positive)
                .expectedState(TRUE)
                .expectedName("isGreaterThan[" + Formatter.stringify(zero) + "]")
                .expectedExpected("> " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.gt(zero))
                .subject(zero)
                .expectedState(FALSE)
                .expectedName("isGreaterThan[" + Formatter.stringify(zero) + "]")
                .expectedExpected("> " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.gt(zero))
                .subject(negative)
                .expectedState(FALSE)
                .expectedName("isGreaterThan[" + Formatter.stringify(zero) + "]")
                .expectedExpected("> " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(negative))
                .addTest();
    }

    private void isLessThanOrEqualToTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T negative = exampleNegativeValue();
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();

        builder.newTest((expression, value) -> expression.isLessThanOrEqualTo(zero))
                .subject(positive)
                .expectedState(FALSE)
                .expectedName("isLessThanOrEqualTo[" + Formatter.stringify(zero) + "]")
                .expectedExpected("<= " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isLessThanOrEqualTo(zero))
                .subject(zero)
                .expectedState(TRUE)
                .expectedName("isLessThanOrEqualTo[" + Formatter.stringify(zero) + "]")
                .expectedExpected("<= " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isLessThanOrEqualTo(zero))
                .subject(negative)
                .expectedState(TRUE)
                .expectedName("isLessThanOrEqualTo[" + Formatter.stringify(zero) + "]")
                .expectedExpected("<= " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(negative))
                .addTest();
    }

    private void lteTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T negative = exampleNegativeValue();
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();

        String result;
        result = Formatter.stringify(zero);
        builder.newTest((expression, value) -> expression.lte(zero))
                .subject(positive)
                .expectedState(FALSE)
                .expectedName("isLessThanOrEqualTo[" + Formatter.stringify(zero) + "]")
                .expectedExpected("<= " + result)
                .expectedActual(Formatter.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.lte(zero))
                .subject(zero)
                .expectedState(TRUE)
                .expectedName("isLessThanOrEqualTo[" + Formatter.stringify(zero) + "]")
                .expectedExpected("<= " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.lte(zero))
                .subject(negative)
                .expectedState(TRUE)
                .expectedName("isLessThanOrEqualTo[" + Formatter.stringify(zero) + "]")
                .expectedExpected("<= " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(negative))
                .addTest();
    }

    private void isGreaterThanOrEqualToTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T negative = exampleNegativeValue();
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();

        builder.newTest((expression, value) -> expression.isGreaterThanOrEqualTo(zero))
                .subject(positive)
                .expectedState(TRUE)
                .expectedName("isGreaterThanOrEqualTo[" + Formatter.stringify(zero) + "]")
                .expectedExpected(">= " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isGreaterThanOrEqualTo(zero))
                .subject(zero)
                .expectedState(TRUE)
                .expectedName("isGreaterThanOrEqualTo[" + Formatter.stringify(zero) + "]")
                .expectedExpected(">= " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isGreaterThanOrEqualTo(zero))
                .subject(negative)
                .expectedState(FALSE)
                .expectedName("isGreaterThanOrEqualTo[" + Formatter.stringify(zero) + "]")
                .expectedExpected(">= " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(negative))
                .addTest();
    }

    private void gteTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T negative = exampleNegativeValue();
        final T zero = exampleZeroValue();
        final T positive = examplePositiveValue();

        builder.newTest((expression, value) -> expression.gte(zero))
                .subject(positive)
                .expectedState(TRUE)
                .expectedName("isGreaterThanOrEqualTo[" + Formatter.stringify(zero) + "]")
                .expectedExpected(">= " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.gte(zero))
                .subject(zero)
                .expectedState(TRUE)
                .expectedName("isGreaterThanOrEqualTo[" + Formatter.stringify(zero) + "]")
                .expectedExpected(">= " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.gte(zero))
                .subject(negative)
                .expectedState(FALSE)
                .expectedName("isGreaterThanOrEqualTo[" + Formatter.stringify(zero) + "]")
                .expectedExpected(">= " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(negative))
                .addTest();
    }

    private void isMaxValueTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T max = exampleMaximumValue();
        final T min = exampleMinimumValue();

        builder.newTest((expression, value) -> expression.isMaxValue())
                .subject(max)
                .expectedState(TRUE)
                .expectedName("isMax")
                .expectedExpected(String.valueOf(max))
                .expectedActual(String.valueOf(max))
                .addTest()
            .newTest((expression, value) -> expression.isMaxValue())
                .subject(min)
                .expectedState(FALSE)
                .expectedName("isMax")
                .expectedExpected(String.valueOf(max))
                .expectedActual(String.valueOf(min))
                .addTest();
    }

    private void isMinValueTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T max = exampleMaximumValue();
        final T min = exampleMinimumValue();

        builder.newTest((expression, value) -> expression.isMinValue())
                .subject(max)
                .expectedState(FALSE)
                .expectedName("isMin")
                .expectedExpected(String.valueOf(min))
                .expectedActual(String.valueOf(max))
                .addTest()
            .newTest((expression, value) -> expression.isMinValue())
                .subject(min)
                .expectedState(TRUE)
                .expectedName("isMin")
                .expectedExpected(String.valueOf(min))
                .expectedActual(String.valueOf(min))
                .addTest();
    }

    private void isZeroTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T positive = examplePositiveValue();
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();

        builder.newTest((expression, value) -> expression.isZero())
                .subject(positive)
                .expectedState(FALSE)
                .expectedName("isZero")
                .expectedExpected(Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isZero())
                .subject(zero)
                .expectedState(TRUE)
                .expectedName("isZero")
                .expectedExpected(Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isZero())
                .subject(negative)
                .expectedState(FALSE)
                .expectedName("isZero")
                .expectedExpected(Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(negative))
                .addTest();
    }

    private void isNotZeroTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T positive = examplePositiveValue();
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();

        builder.newTest((expression, value) -> expression.isNotZero())
                .subject(positive)
                .expectedState(TRUE)
                .expectedName("isNotZero")
                .expectedExpected("not[" + Formatter.stringify(zero) + "]")
                .expectedActual(Formatter.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isNotZero())
                .subject(zero)
                .expectedState(FALSE)
                .expectedName("isNotZero")
                .expectedExpected("not[" + Formatter.stringify(zero) + "]")
                .expectedActual(Formatter.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isNotZero())
                .subject(negative)
                .expectedState(TRUE)
                .expectedName("isNotZero")
                .expectedExpected("not[" + Formatter.stringify(zero) + "]")
                .expectedActual(Formatter.stringify(negative))
                .addTest();
    }

    private void isPositiveTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T positive = examplePositiveValue();
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();

        builder.newTest((expression, value) -> expression.isPositive())
                .subject(positive)
                .expectedState(TRUE)
                .expectedName("isPositive")
                .expectedExpected("> " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isPositive())
                .subject(zero)
                .expectedState(FALSE)
                .expectedName("isPositive")
                .expectedExpected("> " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isPositive())
                .subject(negative)
                .expectedState(FALSE)
                .expectedName("isPositive")
                .expectedExpected("> " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(negative))
                .addTest();
    }

    private void isNotPositiveTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T positive = examplePositiveValue();
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();

        builder.newTest((expression, value) -> expression.isNotPositive())
                .subject(positive)
                .expectedState(FALSE)
                .expectedName("isNotPositive")
                .expectedExpected("<= " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isNotPositive())
                .subject(zero)
                .expectedState(TRUE)
                .expectedName("isNotPositive")
                .expectedExpected("<= " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isNotPositive())
                .subject(negative)
                .expectedState(TRUE)
                .expectedName("isNotPositive")
                .expectedExpected("<= " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(negative))
                .addTest();
    }

    private void isNegativeTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T positive = examplePositiveValue();
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();

        builder.newTest((expression, value) -> expression.isNegative())
                .subject(positive)
                .expectedState(FALSE)
                .expectedName("isNegative")
                .expectedExpected("< " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isNegative())
                .subject(zero)
                .expectedState(FALSE)
                .expectedName("isNegative")
                .expectedExpected("< " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isNegative())
                .subject(negative)
                .expectedState(TRUE)
                .expectedName("isNegative")
                .expectedExpected("< " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(negative))
                .addTest();
    }

    private void isNotNegativeTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T positive = examplePositiveValue();
        final T zero = exampleZeroValue();
        final T negative = exampleNegativeValue();

        builder.newTest((expression, value) -> expression.isNotNegative())
                .subject(positive)
                .expectedState(TRUE)
                .expectedName("isNotNegative")
                .expectedExpected(">= " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(positive))
                .addTest()
            .newTest((expression, value) -> expression.isNotNegative())
                .subject(zero)
                .expectedState(TRUE)
                .expectedName("isNotNegative")
                .expectedExpected(">= " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(zero))
                .addTest()
            .newTest((expression, value) -> expression.isNotNegative())
                .subject(negative)
                .expectedState(FALSE)
                .expectedName("isNotNegative")
                .expectedExpected(">= " + Formatter.stringify(zero))
                .expectedActual(Formatter.stringify(negative))
                .addTest();
    }

    @Override
    protected T exampleValue1() {
        return exampleZeroValue();
    }

    @Override
    protected T exampleValue2() {
        return examplePositiveValue();
    }

    protected abstract void customTestCases(final ExpressionTestCaseBuilder<T, E> builder);
    protected abstract T exampleNegativeValue();
    protected abstract T exampleZeroValue();
    protected abstract T examplePositiveValue();
    protected abstract T exampleMaximumValue();
    protected abstract T exampleMinimumValue();
}
