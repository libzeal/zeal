package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;

@SuppressWarnings("java:S2187")
class SimpleObjectValueTest extends BaseValueTest<Object, SimpleObjectValue<Object>> {

    @Override
    protected SimpleObjectValue<Object> expression(Object value) {
        return new SimpleObjectValue<>(value);
    }

    @Override
    protected Object exampleValue1() {
        return "foo";
    }

    @Override
    protected Object exampleValue2() {
        return "bar";
    }
}
