package io.github.libzeal.zeal.assertion.test;

import static io.github.libzeal.zeal.assertion.Assertions.require;
import static io.github.libzeal.zeal.expression.UnaryExpressions.that;

public class Test {

    public static void main(String[] args) {

        Object value = new Object();

//        require(that(value).isType(Object.class).is("bar").isType(Collection.class).isNotNull(), "foo");
        require(that(Foo.FOO).is(Foo.FOO).ordinalIs(1), "Foo");

    }

    private enum Foo {
        FOO,
        BAR
    }
}
