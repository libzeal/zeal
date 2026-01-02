package io.github.libzeal.zeal.assertion.examples;

import java.util.Collections;

import static io.github.libzeal.zeal.assertion.Assertions.require;
import static io.github.libzeal.zeal.values.Values.value;

public class PreconditionExamples {

    public static void main(String[] args) {

        require(value(Collections.singletonList("foo")).contains("bar"));
    }
}
