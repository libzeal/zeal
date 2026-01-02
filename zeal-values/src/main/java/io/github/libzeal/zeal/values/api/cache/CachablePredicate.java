package io.github.libzeal.zeal.values.api.cache;

import java.util.function.Function;

@FunctionalInterface
public interface CachablePredicate<T, C> extends Function<T, CacheableResult<C>> {
}
