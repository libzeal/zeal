package io.github.libzeal.zeal.values.api.cache;

public interface CacheableResult<C> {

    boolean passed();

    C cache();
}
