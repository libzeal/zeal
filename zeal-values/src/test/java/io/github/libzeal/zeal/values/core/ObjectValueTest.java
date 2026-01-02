package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.values.api.BasedObjectValueTest;

@SuppressWarnings("java:S2187")
class ObjectValueTest extends BasedObjectValueTest<Object, ObjectValue<Object>> {

    @Override
    protected ObjectValue<Object> expression(Object value) {
        return new ObjectValue<>(value);
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
