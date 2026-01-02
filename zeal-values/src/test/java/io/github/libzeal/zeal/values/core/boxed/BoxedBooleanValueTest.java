package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.values.api.BasedObjectValueTest;
import io.github.libzeal.zeal.values.core.test.ExpressionTestCaseBuilder;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

public class BoxedBooleanValueTest extends BasedObjectValueTest<Boolean, BoxedBooleanValue> {

    @Override
    protected BoxedBooleanValue expression(final Boolean value) {
        return new BoxedBooleanValue(value);
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
    protected void extendTestCases(ExpressionTestCaseBuilder<Boolean, BoxedBooleanValue> builder) {
        isTrueTestCases(builder);
        isFalseTestCases(builder);
    }

    private void isTrueTestCases(final ExpressionTestCaseBuilder<Boolean, BoxedBooleanValue> builder) {
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

    private void isFalseTestCases(final ExpressionTestCaseBuilder<Boolean, BoxedBooleanValue> builder) {
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
