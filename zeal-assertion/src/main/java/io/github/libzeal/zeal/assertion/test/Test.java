package io.github.libzeal.zeal.assertion.test;

import io.github.libzeal.zeal.expression.evalulation.formatter.EvaluatedExpressionFormatter;
import io.github.libzeal.zeal.expression.evalulation.formatter.SimpleEvaluatedExpressionFormatter;
import io.github.libzeal.zeal.expression.types.GeneralObjectExpression;

import java.util.Collection;

import static io.github.libzeal.zeal.assertion.api.Assertions.require;
import static io.github.libzeal.zeal.expression.api.Expressions.that;

public class Test {

    public static void main(String[] args) {

        Object value = new Object();

        require(that(value).isType(Object.class).is("bar").isType(Collection.class).isNotNull(), "foo");
    }
}
