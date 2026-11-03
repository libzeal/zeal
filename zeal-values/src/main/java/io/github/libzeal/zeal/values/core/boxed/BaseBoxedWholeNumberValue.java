package io.github.libzeal.zeal.values.core.boxed;

import org.jspecify.annotations.Nullable;

abstract class BaseBoxedWholeNumberValue<N extends Number, V extends BaseBoxedWholeNumberValue<N, V>>
        extends BaseBoxedNumberValue<N, V>
        implements BoxedWholeNumberValue<N, V> {

    BaseBoxedWholeNumberValue(final @Nullable N subject, final String name, final WholeNumberOperations<N> operations) {
        super(subject, name, operations);
    }

    @Override
    public V isEven() {
        return append(
                expression(n -> n.longValue() % 2 == 0)
                        .name("isEven")
                        .expected("% 2 := 0")
                        .build()
        );
    }

    @Override
    public V isOdd() {
        return append(
                expression(n -> n.longValue() % 2 != 0)
                        .name("isOdd")
                        .expected("% 2 != 0")
                        .build()
        );
    }

    // Widening to a long is exact for all whole number types (byte, short, int, and long)
    record WholeNumberOperations<N extends Number>(N zero, N min, N max)
            implements BoxedNumberOperations<N> {

        @Override
        public boolean eq(final N a, final N b) {
            return a.equals(b);
        }

        @Override
        public boolean lt(final N a, final N b) {
            return a.longValue() < b.longValue();
        }

        @Override
        public boolean gt(final N a, final N b) {
            return a.longValue() > b.longValue();
        }

        @Override
        public boolean lte(final N a, final N b) {
            return a.longValue() <= b.longValue();
        }

        @Override
        public boolean gte(final N a, final N b) {
            return a.longValue() >= b.longValue();
        }
    }
}
