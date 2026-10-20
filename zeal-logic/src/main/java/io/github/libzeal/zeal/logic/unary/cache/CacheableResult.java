package io.github.libzeal.zeal.logic.unary.cache;

public interface CacheableResult<C> {

    boolean passed();

    C cache();
}
