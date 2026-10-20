package io.github.libzeal.zeal.logic.unary.cache;

public interface CachedField<T, C> {

    String compute(Context<T, C> context);

    record Context<T, C>(T subject, boolean passed, C cached) {}
}
