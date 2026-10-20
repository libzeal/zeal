package io.github.libzeal.zeal.logic.unary.cache;

import static java.util.Objects.requireNonNull;

public record CachedResult<C>(
        boolean passed,
        C cache){

    public CachedResult {
        requireNonNull(cache);
    }

    public static CachedResultBuilder of(final boolean passed) {
        return new CachedResultBuilder(passed);
    }

    public static CachedResultBuilder ofPassed() {
        return new CachedResultBuilder(true);
    }

    public static CachedResultBuilder ofFailed() {
        return new CachedResultBuilder(false);
    }

    public record CachedResultBuilder(boolean passed) {

        public <C> CachedResult<C> withCache(final C cache) {
            return new CachedResult<>(passed, cache);
        }
    }
}
