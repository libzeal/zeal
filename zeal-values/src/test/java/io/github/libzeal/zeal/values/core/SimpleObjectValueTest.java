package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;

@SuppressWarnings("java:S2187")
class SimpleObjectValueTest extends BaseValueTest<Object, SimpleObjectValue<Object>> {

    @Override
    protected SimpleObjectValue<Object> expression(Object value) {
        return new SimpleObjectValue<>(value);
    }

    @Override
    protected Object nonNullValueA() {
        return "foo";
    }

    @Override
    protected Object nonNullValueB() {
        return "bar";
    }
}
