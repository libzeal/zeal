package com.zeal.expression.ops.logical;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.BooleanResult;
import com.zeal.expression.Guards;

public class OrOperation implements LogicalOperation {

    private final BooleanExpression[] expressions;

    public OrOperation(BooleanExpression[] expressions) {
        this.expressions = Guards.nullable(expressions);
    }

    @Override
    public BooleanResult result() {

        for (BooleanExpression e: expressions) {
            if (e.result().isTrue()) {
                return BooleanResult.TRUE;
            }
        }

        return BooleanResult.FALSE;
    }
}
