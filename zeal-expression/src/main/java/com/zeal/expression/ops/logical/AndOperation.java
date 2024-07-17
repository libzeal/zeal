package com.zeal.expression.ops.logical;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.Guards;

public class AndOperation implements LogicalOperation {

    private final BooleanExpression[] expressions;

    public AndOperation(BooleanExpression[] expressions) {
        this.expressions = Guards.nullable(expressions);
    }

    @Override
    public boolean isTrue() {

        for (BooleanExpression e: expressions) {
            if (e.isFalse()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean hasFailingNotNullCheck() {

        for (BooleanExpression e: expressions) {
            if (e.hasFailingNotNullCheck()) {
                return true;
            }
        }

        return true;
    }
}
