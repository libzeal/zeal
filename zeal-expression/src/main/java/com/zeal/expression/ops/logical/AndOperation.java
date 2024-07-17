package com.zeal.expression.ops.logical;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.Explanation;
import com.zeal.expression.Guards;

import java.util.Optional;

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
    public Optional<Explanation> failureExplanation() {

        for (BooleanExpression e: expressions) {
            if (e.isFalse()) {
                return e.failureExplanation();
            }
        }

        return Optional.empty();
    }
}
