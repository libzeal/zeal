package io.github.libzeal.zeal.assertion.examples;

import static io.github.libzeal.zeal.assertion.Assertions.require;
import static io.github.libzeal.zeal.types.core.CoreTypes.value;

public class PreconditionExamples {

    public static void main(String[] args) {

        require(value("foo").isEqualTo("baz").isEqualTo("bar"));
    }
}
