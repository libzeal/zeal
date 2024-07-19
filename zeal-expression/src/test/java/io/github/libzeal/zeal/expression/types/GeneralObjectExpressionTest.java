package io.github.libzeal.zeal.expression.types;

import io.github.libzeal.zeal.expression.SubjectExpression;

public class GeneralObjectExpressionTest extends ObjectExpressionTest<Object, GeneralObjectExpression<Object>> {

    @Override
    protected GeneralObjectExpression<Object> expression(Object value) {
        return new GeneralObjectExpression<>(value);
    }
}
