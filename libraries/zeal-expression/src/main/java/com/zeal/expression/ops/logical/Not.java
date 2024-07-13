package com.zeal.expression.ops.logical;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.Guards;

public class Not implements LogicalOperation {

    private final BooleanExpression expression;

    public Not(BooleanExpression expression) {
        this.expression = Guards.nullable(expression);
    }

    @Override
    public boolean isTrue() {
        return !expression.isTrue();
    }
}
