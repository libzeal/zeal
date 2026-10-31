package io.github.libzeal.zeal.values.core.test;

import io.github.libzeal.zeal.values.core.ObjectValue;

import java.util.function.BiFunction;
import java.util.function.Function;

public record ValueTestCase<S, V extends ObjectValue<S, V>>(
    Function<S, V> expressionFactory,
    S subject,
    Boolean expectedResult,
    Function<V, V> apply,
    String expectedName,
    String expectedExpected,
    String expectedActual,
    String expectedHint
) {

    public ValueTestCase {

        if (expectedResult == null) {
            throw new IllegalArgumentException("expectedResult must not be null");
        }
        if (apply == null) {
            throw new IllegalArgumentException("apply must not be null");
        }
        if (expectedName == null) {
            throw new IllegalArgumentException("expectedName must not be null");
        }
    }
}
