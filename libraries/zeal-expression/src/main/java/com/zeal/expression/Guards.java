package com.zeal.expression;

import java.util.stream.Stream;

public final class Guards {

    private Guards() {}

    public static BooleanExpression nullable(BooleanExpression expression) {

        if (expression == null) {
            return BooleanValue.FALSE;
        }

        return expression;
    }

    public static BooleanExpression[] nullable(BooleanExpression[] expressions) {
        return Stream.of(expressions)
                .map(Guards::nullable)
                .toArray(BooleanExpression[]::new);
    }
}
