package com.zeal.expression.ops.logical;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.BooleanResult;
import com.zeal.expression.Guards;

public class AndOperation implements LogicalOperation {

    private final BooleanExpression[] expressions;

    public AndOperation(BooleanExpression[] expressions) {
        this.expressions = Guards.nullable(expressions);
    }

    @Override
    public BooleanResult result() {

        for (BooleanExpression e: expressions) {
            if (e.result().isFalse()) {
                return BooleanResult.FALSE;
            }
        }

        return BooleanResult.TRUE;
    }
}
