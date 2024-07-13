package com.zeal.expression.ops.logical;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.Guards;

public class CompoundAnd implements LogicalOperation {

    private final BooleanExpression first;
    private final BooleanExpression second;
    private final BooleanExpression[] expressions;

    public CompoundAnd(BooleanExpression first, BooleanExpression second, BooleanExpression[] expressions) {
        this.first = Guards.nullable(first);
        this.second = Guards.nullable(second);
        this.expressions = Guards.nullable(expressions);
    }

    @Override
    public boolean isTrue() {

        if (first.isFalse() || second.isFalse()) {
            return false;
        }

        for (BooleanExpression e: expressions) {
            if (e.isFalse()) {
                return false;
            }
        }

        return true;
    }
}
