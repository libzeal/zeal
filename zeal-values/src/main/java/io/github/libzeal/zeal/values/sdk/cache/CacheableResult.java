package io.github.libzeal.zeal.values.sdk.cache;

public interface CacheableResult<C> {

    boolean passed();

    C cache();
}
