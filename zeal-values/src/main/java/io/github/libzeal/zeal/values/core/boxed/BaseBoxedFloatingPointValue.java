package io.github.libzeal.zeal.values.core.boxed;

abstract class BaseBoxedFloatingPointValue<N extends Number, V extends BaseBoxedFloatingPointValue<N, V>>
        extends BaseBoxedNumberValue<N, V> {

    BaseBoxedFloatingPointValue(final N subject, final String name, final FloatingPointOperations<N> operations) {
        super(subject, name, operations);
    }

    /**
     * Checks if the subject is finite.
     *
     * @return This value (fluent interface).
     */
    public V isFinite() {
        return append(
                expression(n -> Double.isFinite(n.doubleValue()))
                        .name("isFinite")
                        .expected("finite")
                        .build()
        );
    }

    /**
     * Checks if the subject is infinite.
     *
     * @return This value (fluent interface).
     */
    public V isInfinite() {
        return append(
                expression(n -> Double.isInfinite(n.doubleValue()))
                        .name("isInfinite")
                        .expected("infinite")
                        .build()
        );
    }

    /**
     * Checks if the subject is not a number (NaN).
     *
     * @return This value (fluent interface).
     */
    public V isNaN() {
        return append(
                expression(n -> Double.isNaN(n.doubleValue()))
                        .name("isNaN")
                        .expected("NaN")
                        .build()
        );
    }

    // Widening to a double is exact for both floating point types (float and double). Primitive comparisons are used
    // (rather than compareTo) to keep IEEE 754 behavior: NaN is neither less than nor greater than any value and -0.0
    // is not less than 0.0
    record FloatingPointOperations<N extends Number>(
            N zero, N min, N max
    )
            implements BoxedNumberOperations<N> {

        @Override
        public boolean eq(final N a, final N b) {
            return a.equals(b);
        }

        @Override
        public boolean lt(final N a, final N b) {
            return a.doubleValue() < b.doubleValue();
        }

        @Override
        public boolean gt(final N a, final N b) {
            return a.doubleValue() > b.doubleValue();
        }

        @Override
        public boolean lte(final N a, final N b) {
            return a.doubleValue() <= b.doubleValue();
        }

        @Override
        public boolean gte(final N a, final N b) {
            return a.doubleValue() >= b.doubleValue();
        }
    }
}
