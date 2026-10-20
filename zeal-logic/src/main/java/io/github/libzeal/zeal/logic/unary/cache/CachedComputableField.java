package io.github.libzeal.zeal.logic.unary.cache;

@FunctionalInterface
public interface CachedComputableField<T, C> {

    String compute(CachedComputableRationaleContext<T, C> context);
}
