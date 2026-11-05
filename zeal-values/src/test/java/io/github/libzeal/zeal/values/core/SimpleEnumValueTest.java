package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.values.core.test.MockEnum;

@SuppressWarnings("java:S2187")
class SimpleEnumValueTest extends EnumValueTest<MockEnum> {

    @Override
    protected SimpleEnumValue<MockEnum> expression(MockEnum value) {
        return new SimpleEnumValue<>(value);
    }

    @Override
    protected MockEnum nonNullValueA() {
        return MockEnum.YES;
    }

    @Override
    protected MockEnum nonNullValueB() {
        return MockEnum.NO;
    }
}
