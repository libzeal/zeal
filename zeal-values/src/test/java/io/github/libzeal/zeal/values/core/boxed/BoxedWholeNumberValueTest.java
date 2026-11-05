package io.github.libzeal.zeal.values.core.boxed;

import io.github.libzeal.zeal.values.sdk.BaseValue;
import org.junit.jupiter.api.Test;


public abstract class BoxedWholeNumberValueTest<T extends Number, E extends BaseValue<T, E> & BoxedWholeNumberValue<T, E>>
    extends BoxedNumberValueTest<T, E> {

    // ---- isEven ----

    @Test
    void givenPositiveEven_whenIsEven_thenTrue() {
        newTestCase()
            .subject(add(exampleZeroValue(), (short) 2))
            .expression(BoxedWholeNumberValue::isEven)
            .result(true)
            .name("isEven")
            .expected("% 2 := 0")
            .actual("2")
            .run();
    }

    @Test
    void givenPositiveOdd_whenIsEven_thenFalse() {
        newTestCase()
            .subject(add(exampleZeroValue(), (short) 1))
            .expression(BoxedWholeNumberValue::isEven)
            .result(false)
            .name("isEven")
            .expected("% 2 := 0")
            .actual("1")
            .run();
    }

    @Test
    void givenZero_whenIsEven_thenTrue() {
        newTestCase()
            .subject(exampleZeroValue())
            .expression(BoxedWholeNumberValue::isEven)
            .result(true)
            .name("isEven")
            .expected("% 2 := 0")
            .actual("0")
            .run();
    }

    @Test
    void givenNegativeOdd_whenIsEven_thenFalse() {
        newTestCase()
            .subject(subtract(exampleZeroValue(), (short) 1))
            .expression(BoxedWholeNumberValue::isEven)
            .result(false)
            .name("isEven")
            .expected("% 2 := 0")
            .actual("-1")
            .run();
    }

    @Test
    void givenNegativeEven_whenIsEven_thenTrue() {
        newTestCase()
            .subject(subtract(exampleZeroValue(), (short) 2))
            .expression(BoxedWholeNumberValue::isEven)
            .result(true)
            .name("isEven")
            .expected("% 2 := 0")
            .actual("-2")
            .run();
    }

    // ---- isOdd ----

    @Test
    void givenPositiveEven_whenIsOdd_thenFalse() {
        newTestCase()
            .subject(add(exampleZeroValue(), (short) 2))
            .expression(BoxedWholeNumberValue::isOdd)
            .result(false)
            .name("isOdd")
            .expected("% 2 != 0")
            .actual("2")
            .run();
    }

    @Test
    void givenPositiveOdd_whenIsOdd_thenTrue() {
        newTestCase()
            .subject(add(exampleZeroValue(), (short) 1))
            .expression(BoxedWholeNumberValue::isOdd)
            .result(true)
            .name("isOdd")
            .expected("% 2 != 0")
            .actual("1")
            .run();
    }

    @Test
    void givenZero_whenIsOdd_thenFalse() {
        newTestCase()
            .subject(exampleZeroValue())
            .expression(BoxedWholeNumberValue::isOdd)
            .result(false)
            .name("isOdd")
            .expected("% 2 != 0")
            .actual("0")
            .run();
    }

    @Test
    void givenNegativeOdd_whenIsOdd_thenTrue() {
        newTestCase()
            .subject(subtract(exampleZeroValue(), (short) 1))
            .expression(BoxedWholeNumberValue::isOdd)
            .result(true)
            .name("isOdd")
            .expected("% 2 != 0")
            .actual("-1")
            .run();
    }

    @Test
    void givenNegativeEven_whenIsOdd_thenFalse() {
        newTestCase()
            .subject(subtract(exampleZeroValue(), (short) 2))
            .expression(BoxedWholeNumberValue::isOdd)
            .result(false)
            .name("isOdd")
            .expected("% 2 != 0")
            .actual("-2")
            .run();
    }

    protected abstract T add(T a, short b);
    protected abstract T subtract(T a, short b);
}
