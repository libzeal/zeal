package io.github.libzeal.zeal.values.api.cache;

import io.github.libzeal.zeal.logic.unary.future.rationale.ComputableRationaleContext;

import static java.util.Objects.requireNonNull;

public final class CachedComputableRationaleContext<T, C> extends ComputableRationaleContext<T> {

    private final C cache;

    CachedComputableRationaleContext(final T subject, final boolean passed, final C cache) {
        super(subject, passed);
        this.cache = requireNonNull(cache);
    }

    public C cache() {
        return cache;
    }
}
