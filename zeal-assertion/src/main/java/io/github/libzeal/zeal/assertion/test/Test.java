package io.github.libzeal.zeal.assertion.test;

import static io.github.libzeal.zeal.assertion.Assertions.require;
import static io.github.libzeal.zeal.expression.UnaryExpressions.value;
import static io.github.libzeal.zeal.expression.lang.condition.Conditions.noneOf;

public class Test {

    public static void main(String[] args) {

        require(value("foo").is(noneOf("foo", "baz")));
    }
}
