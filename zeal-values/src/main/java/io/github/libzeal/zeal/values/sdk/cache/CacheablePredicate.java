package io.github.libzeal.zeal.values.sdk.cache;

import java.util.function.Function;

@FunctionalInterface
public interface CacheablePredicate<T, C> extends Function<T, CacheableResult<C>> {
}
