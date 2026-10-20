package io.github.libzeal.zeal.logic.unary.cache;

@FunctionalInterface
public interface CacheablePredicate<T, C> {

    CacheableResult<C> test(T value);
}
