package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.logic.unary.future.ComputableExpression;

import static io.github.libzeal.zeal.values.core.boxed.BoxedNumberExpressions.notNullExpression;

class BoxedWholeNumberExpressions {

    public static <T extends Number> ComputableExpression<T> isEven() {
        return notNullExpression((T l) -> l.longValue() % 2 == 0)
            .name("isEven")
            .expected("% 2 := 0")
            .build();
    }

    public static <T extends Number> ComputableExpression<T> isOdd() {
        return notNullExpression((T l) -> l.longValue() % 2 != 0)
            .name("isOdd")
            .expected("% 2 != 0")
            .build();
    }
}
