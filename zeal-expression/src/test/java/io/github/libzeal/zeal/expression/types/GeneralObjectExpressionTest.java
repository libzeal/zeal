package io.github.libzeal.zeal.expression.types;

@SuppressWarnings("java:S2187")
class GeneralObjectExpressionTest extends ObjectExpressionTest<Object, GeneralObjectExpression<Object>> {

    @Override
    protected GeneralObjectExpression<Object> expression(Object value) {
        return new GeneralObjectExpression<>(value);
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
