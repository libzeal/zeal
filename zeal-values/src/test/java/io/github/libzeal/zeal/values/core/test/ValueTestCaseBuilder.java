package io.github.libzeal.zeal.values.core.test;

import io.github.libzeal.zeal.values.core.ObjectValue;

import java.util.function.Function;

public final class ValueTestCaseBuilder<S, V extends ObjectValue<S, V>> {

    private final Function<S, V> expressionFactory;
    private S subject;
    private Boolean expectedResult;
    private Function<V, V> apply;
    private String expectedName;
    private String expectedExpected;
    private String expectedActual;
    private String expectedHint;

    private ValueTestCaseBuilder(final Function<S, V> expressionFactory) {
        this.expressionFactory = expressionFactory;
    }

    public static <T, V extends ObjectValue<T, V>> ValueTestCaseBuilder<T, V> forValue(final Function<T, V> expressionFactory) {
        return new ValueTestCaseBuilder<>(expressionFactory);
    }

    public ValueTestCaseBuilder<S, V> subject(S subject) {
        this.subject = subject;
        return this;
    }

    public ValueTestCaseBuilder<S, V> result(boolean result) {
        this.expectedResult = result;
        return this;
    }

    public ValueTestCaseBuilder<S, V> expression(Function<V, V> apply) {
        this.apply = apply;
        return this;
    }

    public ValueTestCaseBuilder<S, V> name(String name) {
        this.expectedName = name;
        return this;
    }

    public ValueTestCaseBuilder<S, V> expected(String expected) {
        this.expectedExpected = expected;
        return this;
    }

    public ValueTestCaseBuilder<S, V> actual(String actual) {
        this.expectedActual = actual;
        return this;
    }

    public ValueTestCaseBuilder<S, V> hint(String hint) {
        this.expectedHint = hint;
        return this;
    }

    public ValueTestCase<S, V> build() {
        return new ValueTestCase<>(
            expressionFactory,
            subject,
            expectedResult,
            apply,
            expectedName,
            expectedExpected,
            expectedActual,
            expectedHint
        );
    }

    public void run() {
        ValueTestCaseRunner.run(build());
    }
}
