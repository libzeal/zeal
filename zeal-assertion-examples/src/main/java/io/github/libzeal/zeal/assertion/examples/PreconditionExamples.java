package io.github.libzeal.zeal.assertion.examples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.github.libzeal.zeal.assertion.Assertions.require;
import static io.github.libzeal.zeal.values.Values.value;

public class PreconditionExamples {

    public static void main(String[] args) {

        final List<String> list = Collections.singletonList("foo");

        require(value(list).includesAllOf("bar", "baz"));
        require(value("foo").isNotEmpty());
    }
}
