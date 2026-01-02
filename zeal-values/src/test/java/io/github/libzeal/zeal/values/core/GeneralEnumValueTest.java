package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.values.core.test.MockEnum;

@SuppressWarnings("java:S2187")
class GeneralEnumValueTest extends BaseEnumValueTest<MockEnum> {

    @Override
    protected EnumValue<MockEnum> expression(MockEnum value) {
        return new EnumValue<>(value);
    }

    @Override
    protected MockEnum exampleValue1() {
        return MockEnum.YES;
    }

    @Override
    protected MockEnum exampleValue2() {
        return MockEnum.NO;
    }
}
