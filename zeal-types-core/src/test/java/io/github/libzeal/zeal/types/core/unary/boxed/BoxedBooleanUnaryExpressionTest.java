package io.github.libzeal.zeal.types.core.unary.boxed;

import io.github.libzeal.zeal.types.core.unary.ObjectUnaryExpressionTest;
import io.github.libzeal.zeal.types.core.unary.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

public class BoxedBooleanUnaryExpressionTest extends ObjectUnaryExpressionTest<Boolean, BoxedBooleanUnaryExpression> {

    @Override
    protected BoxedBooleanUnaryExpression expression(final Boolean value) {
        return new BoxedBooleanUnaryExpression(value);
    }

    @Override
    protected Boolean exampleValue1() {
        return Boolean.TRUE;
    }

    @Override
    protected Boolean exampleValue2() {
        return Boolean.FALSE;
    }

    @Override
    protected void extendTestCases(ExpressionTestCaseBuilder<Boolean, BoxedBooleanUnaryExpression> builder) {
        isTrueTestCases(builder);
        isFalseTestCases(builder);
    }

    private void isTrueTestCases(final ExpressionTestCaseBuilder<Boolean, BoxedBooleanUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isTrue())
                .subject(true)
                .expectedState(TRUE)
                .expectedName("isTrue")
                .expectedExpected("true")
                .expectedActual("true")
                .addTest()
            .newTest((expression, value) -> expression.isTrue())
                .subject(false)
                .expectedState(FALSE)
                .expectedName("isTrue")
                .expectedExpected("true")
                .expectedActual("false")
                .addTest();
    }

    private void isFalseTestCases(final ExpressionTestCaseBuilder<Boolean, BoxedBooleanUnaryExpression> builder) {
        builder.newTest((expression, value) -> expression.isFalse())
                .subject(true)
                .expectedState(FALSE)
                .expectedName("isFalse")
                .expectedExpected("false")
                .expectedActual("true")
                .addTest()
            .newTest((expression, value) -> expression.isFalse())
                .subject(false)
                .expectedState(TRUE)
                .expectedName("isFalse")
                .expectedExpected("false")
                .expectedActual("false")
                .addTest();
    }
}
