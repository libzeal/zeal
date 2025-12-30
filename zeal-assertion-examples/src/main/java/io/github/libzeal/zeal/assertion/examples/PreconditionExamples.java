package io.github.libzeal.zeal.assertion.examples;

import static io.github.libzeal.zeal.assertion.Assertions.require;
import static io.github.libzeal.zeal.values.core.Values.value;

public class PreconditionExamples {

    public static void main(String[] args) {

        require(value("foo").isEqualTo("foo").isEqualTo("bar").isEqualTo("baz"));
    }
}
