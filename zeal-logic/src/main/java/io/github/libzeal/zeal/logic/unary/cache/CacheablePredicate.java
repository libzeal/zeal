package io.github.libzeal.zeal.logic.unary.cache;

@FunctionalInterface
public interface CacheablePredicate<T, C> {

    CachedResult<C> test(T value);
}
