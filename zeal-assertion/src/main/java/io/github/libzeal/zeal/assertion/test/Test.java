package io.github.libzeal.zeal.assertion.test;

import io.github.libzeal.zeal.expression.evalulation.formatter.EvaluatedExpressionFormatter;
import io.github.libzeal.zeal.expression.evalulation.formatter.SimpleEvaluatedExpressionFormatter;
import io.github.libzeal.zeal.expression.types.GeneralObjectExpression;

import java.util.Collection;

import static io.github.libzeal.zeal.assertion.api.Assertions.require;
import static io.github.libzeal.zeal.expression.api.Expressions.that;

public class Test {

    public static void main(String[] args) {

        String value = null;

        GeneralObjectExpression<String> expression = that(value).isType(String.class)
                .isType(Thread.class)
                .isType(Collection.class)
                .isNotNull();

        EvaluatedExpressionFormatter formatter = new SimpleEvaluatedExpressionFormatter();

        System.out.println(formatter.format(expression.evaluate()));

//        require(expression, "Expression must not be null");

        String found = require(that(value).isNotNull(), "Foo");
    }
}
