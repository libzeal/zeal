package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.values.core.test.MockEnum;

@SuppressWarnings("java:S2187")
class GeneralEnumValueTest extends EnumValueTest<MockEnum> {

    @Override
    protected GeneralEnumValue<MockEnum> expression(MockEnum value) {
        return new GeneralEnumValue<>(value);
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
