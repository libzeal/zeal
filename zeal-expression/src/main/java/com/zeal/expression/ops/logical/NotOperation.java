package com.zeal.expression.ops.logical;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.Guards;

public class NotOperation implements LogicalOperation {

    private final BooleanExpression expression;

    public NotOperation(BooleanExpression expression) {
        this.expression = Guards.nullable(expression);
    }

    @Override
    public boolean isTrue() {
        return !expression.isTrue();
    }

    @Override
    public boolean hasFailingNotNullCheck() {
        return !expression.hasFailingNotNullCheck();
    }
}
