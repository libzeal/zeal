package com.zeal.expression.api;

import com.zeal.expression.BooleanExpression;
import com.zeal.expression.ops.logical.*;

public final class LogicalOperations {

    private LogicalOperations() {}

    public static BooleanExpression not(BooleanExpression expression) {
        return new Not(expression);
    }

    public static BooleanExpression and(BooleanExpression a, BooleanExpression b) {
        return new BinaryAnd(a, b);
    }

    public static BooleanExpression and(BooleanExpression first, BooleanExpression second, BooleanExpression... remaining) {
        return new CompoundAnd(first, second, remaining);
    }

    public static BooleanExpression or(BooleanExpression a, BooleanExpression b) {
        return new BinaryOr(a, b);
    }

    public static BooleanExpression or(BooleanExpression first, BooleanExpression second, BooleanExpression... remaining) {
        return new CompoundOr(first, second, remaining);
    }
}
