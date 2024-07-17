package com.zeal.expression.ops.logical;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.Guards;

public class OrOperation implements LogicalOperation {

    private final BooleanExpression[] expressions;

    public OrOperation(BooleanExpression[] expressions) {
        this.expressions = Guards.nullable(expressions);
    }

    @Override
    public boolean isTrue() {

        for (BooleanExpression e: expressions) {
            if (e.isTrue()) {
                return true;
            }
        }

        return false;
    }
}
