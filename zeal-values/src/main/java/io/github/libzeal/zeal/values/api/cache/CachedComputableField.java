package io.github.libzeal.zeal.values.api.cache;

import static java.util.Objects.requireNonNull;

@FunctionalInterface
public interface CachedComputableField<T, C> {

    String compute(CachedComputableRationaleContext<T, C> context);
}
