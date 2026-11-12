package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;


public class BoxedBooleanValueTest extends BaseValueTest<Boolean, BoxedBooleanValue> {

    @Override
    protected BoxedBooleanValue expression(final Boolean value) {
        return new BoxedBooleanValue(value);
    }

    @Override
    protected Boolean nonNullValueA() {
        return Boolean.TRUE;
    }

    @Override
    protected Boolean nonNullValueB() {
        return Boolean.FALSE;
    }

    // ---- isTrue ----

    @Test
    void givenTrue_whenIsTrue_thenTrue() {
        newTestCase()
            .subject(true)
            .expression(BoxedBooleanValue::isTrue)
            .result(true)
            .name("isTrue")
            .expected("true")
            .actual("true")
            .run();
    }

    @Test
    void givenFalse_whenIsTrue_thenFalse() {
        newTestCase()
            .subject(false)
            .expression(BoxedBooleanValue::isTrue)
            .result(false)
            .name("isTrue")
            .expected("true")
            .actual("false")
            .run();
    }

    // ---- isFalse ----

    @Test
    void givenTrue_whenIsFalse_thenFalse() {
        newTestCase()
            .subject(true)
            .expression(BoxedBooleanValue::isFalse)
            .result(false)
            .name("isFalse")
            .expected("false")
            .actual("true")
            .run();
    }

    @Test
    void givenFalse_whenIsFalse_thenTrue() {
        newTestCase()
            .subject(false)
            .expression(BoxedBooleanValue::isFalse)
            .result(true)
            .name("isFalse")
            .expected("false")
            .actual("false")
            .run();
    }
}
