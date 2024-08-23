package io.github.libzeal.zeal.assertion.examples;

import static io.github.libzeal.zeal.assertion.Assertions.require;
import static io.github.libzeal.zeal.logic.Operations.not;
import static io.github.libzeal.zeal.logic.condition.Conditions.allOf;
import static io.github.libzeal.zeal.logic.condition.Conditions.noneOf;
import static io.github.libzeal.zeal.types.core.CoreType.value;

public class PreconditionExamples {

    public static void main(String[] args) {

        require(not(value("foo").isNotNull().is(noneOf("foo", "baz"))));
        require(not(value("foo").isNotNull().is(allOf("foo", "foo"))));
    }
}