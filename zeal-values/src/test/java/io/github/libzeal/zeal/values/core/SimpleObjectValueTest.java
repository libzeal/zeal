package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.values.core.test.ExpressionTestCaseBuilder;
import io.github.libzeal.zeal.values.sdk.BaseValueTest;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

@SuppressWarnings("java:S2187")
class SimpleObjectValueTest extends BaseValueTest<Object, SimpleObjectValue<Object>> {

    @Override
    protected SimpleObjectValue<Object> expression(Object value) {
        return new SimpleObjectValue<>(value);
    }

    @Override
    protected Object nonNullValueA() {
        return "foo";
    }

    @Override
    protected Object nonNullValueB() {
        return "bar";
    }

    @Override
    protected void extendTestCases(final ExpressionTestCaseBuilder<Object, SimpleObjectValue<Object>> builder) {
        builder.newTest((expression, value) -> expression.isNull())
            .subject(null)
            .expectedState(TRUE)
            .expectedName("isNull")
            .expectedExpected("(null)")
            .expectedActual("(null)")
            .addTest()
            .newTest((expression, value) -> expression.isNull())
            .subject(nonNullValueA())
            .expectedState(FALSE)
            .expectedName("isNull")
            .expectedExpected("(null)")
            .expectedActual(nonNullValueA().toString())
            .addTest();
    }
}
