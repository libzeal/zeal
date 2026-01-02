package io.github.libzeal.zeal.values.api.cache;

import static java.util.Objects.requireNonNull;

public final class SimpleCacheResult<C> implements CacheableResult<C> {

    private final boolean passed;
    private final C cache;

    public SimpleCacheResult(final boolean passed, final C cache) {
        this.passed = passed;
        this.cache = requireNonNull(cache);
    }

    public static SimpleCacheResultBuilder of(final boolean passed) {
        return new SimpleCacheResultBuilder(passed);
    }

    @Override
    public boolean passed() {
        return passed;
    }

    @Override
    public C cache() {
        return cache;
    }

    public final static class SimpleCacheResultBuilder {

        private final boolean passed;

        private SimpleCacheResultBuilder(final boolean passed) {
            this.passed = passed;
        }

        public <C> SimpleCacheResult<C> withCache(final C cache) {
            return new SimpleCacheResult<>(passed, cache);
        }
    }
}
