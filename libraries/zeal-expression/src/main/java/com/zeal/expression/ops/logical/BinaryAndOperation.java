package com.zeal.expression.ops.logical;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.Guards;

public class BinaryAndOperation implements LogicalOperation {

    private final BooleanExpression a;
    private final BooleanExpression b;

    public BinaryAndOperation(BooleanExpression a, BooleanExpression b) {
        this.a = Guards.nullable(a);
        this.b = Guards.nullable(b);
    }

    @Override
    public boolean isTrue() {
        return a.isTrue() && b.isTrue();
    }
}
