package io.github.libzeal.zeal.values.core.basic;

@SuppressWarnings("java:S2187")
class GeneralObjectValueTest extends ObjectValueTest<Object, GeneralObjectValue<Object>> {

    @Override
    protected GeneralObjectValue<Object> expression(Object value) {
        return new GeneralObjectValue<>(value);
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
