package io.github.libzeal.zeal.values.core.boxed;

class BoxedIntegerValueTest extends BoxedWholeNumberValueTest<Integer, BoxedIntegerValue> {

    @Override
    protected BoxedIntegerValue expression(final Integer value) {
        return new BoxedIntegerValue(value);
    }

    @Override
    protected Integer exampleNegativeValue() {
        return -1;
    }

    @Override
    protected Integer exampleZeroValue() {
        return 0;
    }

    @Override
    protected Integer examplePositiveValue() {
        return 1;
    }

    @Override
    protected Integer exampleMaximumValue() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected Integer exampleMinimumValue() {
        return Integer.MIN_VALUE;
    }

    @Override
    protected Integer add(final Integer a, final short b) {
        return a + b;
    }

    @Override
    protected Integer subtract(final Integer a, final short b) {
        return a - b;
    }
}
