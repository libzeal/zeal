package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.values.sdk.BaseValueTest;
import org.junit.jupiter.api.Test;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

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
            .result(TRUE)
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
            .result(FALSE)
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
            .result(FALSE)
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
            .result(TRUE)
            .name("isFalse")
            .expected("false")
            .actual("false")
            .run();
    }
}
