package io.github.libzeal.zeal.expression.types.boxed;

import io.github.libzeal.zeal.expression.types.test.ExpressionTestCaseBuilder;
import io.github.libzeal.zeal.expression.types.ObjectExpressionTest;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.FAILED;
import static io.github.libzeal.zeal.expression.lang.evaluation.Result.PASSED;

public class BoxedBooleanExpressionTest extends ObjectExpressionTest<Boolean, BoxedBooleanExpression> {

    @Override
    protected BoxedBooleanExpression expression(final Boolean value) {
        return new BoxedBooleanExpression(value);
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
    protected void extendTestCases(ExpressionTestCaseBuilder<Boolean, BoxedBooleanExpression> builder) {
        isTrueTestCases(builder);
        isFalseTestCases(builder);
    }

    private void isTrueTestCases(final ExpressionTestCaseBuilder<Boolean, BoxedBooleanExpression> builder) {
        builder.newTest((expression, value) -> expression.isTrue())
                .value(true)
                .expectedState(PASSED)
                .expectedName("isTrue")
                .expectedExpectedValue("true")
                .expectedActualValue("true")
                .addTest()
            .newTest((expression, value) -> expression.isTrue())
                .value(false)
                .expectedState(FAILED)
                .expectedName("isTrue")
                .expectedExpectedValue("true")
                .expectedActualValue("false")
                .addTest();
    }

    private void isFalseTestCases(final ExpressionTestCaseBuilder<Boolean, BoxedBooleanExpression> builder) {
        builder.newTest((expression, value) -> expression.isFalse())
                .value(true)
                .expectedState(FAILED)
                .expectedName("isFalse")
                .expectedExpectedValue("false")
                .expectedActualValue("true")
                .addTest()
            .newTest((expression, value) -> expression.isFalse())
                .value(false)
                .expectedState(PASSED)
                .expectedName("isFalse")
                .expectedExpectedValue("false")
                .expectedActualValue("false")
                .addTest();
    }
}
