package io.github.libzeal.zeal.expression.evalulation;

import java.util.function.Supplier;

@FunctionalInterface
public interface ValueFormatter<T> {

    String compute(T subject);

    static <A> ValueFormatter<A> of(String value) {
        return s -> value;
    }

    static <A> ValueFormatter<A> of(Supplier<?> supplier) {
        return s -> String.valueOf(supplier.get());
    }
}
