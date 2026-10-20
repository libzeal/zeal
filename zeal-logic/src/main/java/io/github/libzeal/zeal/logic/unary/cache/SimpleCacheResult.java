package io.github.libzeal.zeal.logic.unary.cache;

import static java.util.Objects.requireNonNull;

public record SimpleCacheResult<C>(boolean passed,
                                   C cache) implements CacheableResult<C> {

    public SimpleCacheResult {
        requireNonNull(cache);
    }

    public static SimpleCacheResultBuilder of(final boolean passed) {
        return new SimpleCacheResultBuilder(passed);
    }

    public static SimpleCacheResultBuilder ofPassed() {
        return new SimpleCacheResultBuilder(true);
    }

    public static SimpleCacheResultBuilder ofFailed() {
        return new SimpleCacheResultBuilder(false);
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
