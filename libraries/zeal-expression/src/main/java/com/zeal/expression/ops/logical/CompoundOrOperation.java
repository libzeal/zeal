package com.zeal.expression.ops.logical;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.Guards;

public class CompoundOrOperation implements LogicalOperation {

    private final BooleanExpression first;
    private final BooleanExpression second;
    private final BooleanExpression[] expressions;

    public CompoundOrOperation(BooleanExpression first, BooleanExpression second, BooleanExpression[] expressions) {
        this.first = Guards.nullable(first);
        this.second = Guards.nullable(second);
        this.expressions = Guards.nullable(expressions);
    }

    @Override
    public boolean isTrue() {

        if (first.isTrue() || second.isTrue()) {
            return false;
        }

        for (BooleanExpression e: expressions) {
            if (e.isTrue()) {
                return true;
            }
        }

        return false;
    }
}
