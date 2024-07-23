package io.github.libzeal.zeal.expression.types;

import io.github.libzeal.zeal.expression.test.ExpressionTestCaseBuilder;

@SuppressWarnings("java:S2187")
class StringExpressionTest extends ObjectExpressionTest<String, StringExpression> {

    @Override
    protected StringExpression expression(String value) {
        return new StringExpression(value);
    }

    @Override
    protected String exampleValue1() {
        return "foo";
    }

    @Override
    protected String exampleValue2() {
        return "bar";
    }

    @Override
    protected void extendTestCases(ExpressionTestCaseBuilder<String, StringExpression> builder) {
        super.extendTestCases(builder);
    }
}
