package io.github.libzeal.zeal.expression.types;

import io.github.libzeal.zeal.expression.test.MockEnum;

@SuppressWarnings("java:S2187")
class GeneralEnumExpressionTest extends EnumExpressionTest<MockEnum> {

    @Override
    protected GeneralEnumExpression<MockEnum> expression(MockEnum value) {
        return new GeneralEnumExpression<>(value);
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
