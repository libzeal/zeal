package io.github.libzeal.zeal.values.sdk.cache;

@FunctionalInterface
public interface CachedComputableField<T, C> {

    String compute(CachedComputableRationaleContext<T, C> context);
}
