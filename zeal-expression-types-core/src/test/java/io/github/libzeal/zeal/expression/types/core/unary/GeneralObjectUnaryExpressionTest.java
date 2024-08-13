package io.github.libzeal.zeal.expression.types.core.unary;

@SuppressWarnings("java:S2187")
class GeneralObjectUnaryExpressionTest extends ObjectUnaryExpressionTest<Object, GeneralObjectUnaryExpression<Object>> {

    @Override
    protected GeneralObjectUnaryExpression<Object> expression(Object value) {
        return new GeneralObjectUnaryExpression<>(value);
    }

    @Override
    protected Object exampleValue1() {
        return "foo";
    }

    @Override
    protected Object exampleValue2() {
        return "bar";
    }
}
