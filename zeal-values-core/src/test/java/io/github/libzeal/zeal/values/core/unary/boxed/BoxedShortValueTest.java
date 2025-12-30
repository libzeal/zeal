package io.github.libzeal.zeal.values.core.unary.boxed;

class BoxedShortValueTest extends BoxedWholeNumberValueTest<Short, BoxedShortValue> {

    @Override
    protected BoxedShortValue expression(final Short value) {
        return new BoxedShortValue(value);
    }

    @Override
    protected Short exampleNegativeValue() {
        return -1;
    }

    @Override
    protected Short exampleZeroValue() {
        return 0;
    }

    @Override
    protected Short examplePositiveValue() {
        return 1;
    }

    @Override
    protected Short exampleMaximumValue() {
        return Short.MAX_VALUE;
    }

    @Override
    protected Short exampleMinimumValue() {
        return Short.MIN_VALUE;
    }

    @Override
    protected Short add(final Short a, final short b) {
        return (short) (a + b);
    }

    @Override
    protected Short subtract(final Short a, final short b) {
        return (short) (a - b);
    }
}
