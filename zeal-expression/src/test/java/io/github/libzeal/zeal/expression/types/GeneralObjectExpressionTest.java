package io.github.libzeal.zeal.expression.types;

@SuppressWarnings("java:S2187")
public class GeneralObjectExpressionTest extends ObjectExpressionTest<Object, GeneralObjectExpression<Object>> {

    @Override
    protected GeneralObjectExpression<Object> expression(Object value) {
        return new GeneralObjectExpression<>(value);
    }
}
