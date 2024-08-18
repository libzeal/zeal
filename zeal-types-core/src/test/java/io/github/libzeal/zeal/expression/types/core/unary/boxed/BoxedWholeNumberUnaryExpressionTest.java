package io.github.libzeal.zeal.expression.types.core.unary.boxed;

import io.github.libzeal.zeal.expression.types.core.unary.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

public abstract class BoxedWholeNumberUnaryExpressionTest<T extends Number, E extends BoxedWholeNumberUnaryExpression<T, E>>
    extends BoxedNumberUnaryExpressionTest<T, E> {

    @Override
    protected void customTestCases(final ExpressionTestCaseBuilder<T, E> builder) {
        isEvenTestCases(builder);
        isOddTestCases(builder);
    }

    private void isEvenTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T value0 = exampleZeroValue();
        final T valueNeg1 = subtract(value0, (short) 1);
        final T valueNeg2 = subtract(value0, (short) 2);
        final T value1 = add(value0, (short) 1);
        final T value2 = add(value0, (short) 2);

        builder.newTest((expression, value) -> expression.isEven())
                .subject(value2)
                .expectedState(TRUE)
                .expectedName("isEven")
                .expectedExpected("% 2 := 0")
                .expectedActual("2")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .subject(value1)
                .expectedState(FALSE)
                .expectedName("isEven")
                .expectedExpected("% 2 := 0")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .subject(value0)
                .expectedState(TRUE)
                .expectedName("isEven")
                .expectedExpected("% 2 := 0")
                .expectedActual("0")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .subject(valueNeg1)
                .expectedState(FALSE)
                .expectedName("isEven")
                .expectedExpected("% 2 := 0")
                .expectedActual("-1")
                .addTest()
            .newTest((expression, value) -> expression.isEven())
                .subject(valueNeg2)
                .expectedState(TRUE)
                .expectedName("isEven")
                .expectedExpected("% 2 := 0")
                .expectedActual("-2")
                .addTest();
    }

    private void isOddTestCases(final ExpressionTestCaseBuilder<T, E> builder) {

        final T value0 = exampleZeroValue();
        final T valueNeg1 = subtract(value0, (short) 1);
        final T valueNeg2 = subtract(value0, (short) 2);
        final T value1 = add(value0, (short) 1);
        final T value2 = add(value0, (short) 2);

        builder.newTest((expression, value) -> expression.isOdd())
                .subject(value2)
                .expectedState(FALSE)
                .expectedName("isOdd")
                .expectedExpected("% 2 != 0")
                .expectedActual("2")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .subject(value1)
                .expectedState(TRUE)
                .expectedName("isOdd")
                .expectedExpected("% 2 != 0")
                .expectedActual("1")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .subject(value0)
                .expectedState(FALSE)
                .expectedName("isOdd")
                .expectedExpected("% 2 != 0")
                .expectedActual("0")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .subject(valueNeg1)
                .expectedState(TRUE)
                .expectedName("isOdd")
                .expectedExpected("% 2 != 0")
                .expectedActual("-1")
                .addTest()
            .newTest((expression, value) -> expression.isOdd())
                .subject(valueNeg2)
                .expectedState(FALSE)
                .expectedName("isOdd")
                .expectedExpected("% 2 != 0")
                .expectedActual("-2")
                .addTest();
    }

    protected abstract T add(T a, short b);
    protected abstract T subtract(T a, short b);
}
