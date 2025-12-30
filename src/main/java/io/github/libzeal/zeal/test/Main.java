package io.github.libzeal.zeal.test;

import static io.github.libzeal.zeal.assertions.Assertions.require;

import java.util.List;

public class Main {

    void main() {

        require(value("foo"));
    }

    private static UnaryValue<String> value(final String value) {

    }
}
