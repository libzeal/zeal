package io.github.libzeal.zeal.logic.unary.cache;

import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import static java.util.Objects.requireNonNull;

public record CachedComputableRationale<T, C>(
    CachedComputableField<T, C> expected,
    CachedComputableField<T, C> actual, CachedComputableField<T, C> hint) {

    public CachedComputableRationale {
        requireNonNull(expected);
        requireNonNull(actual);
    }

    public Rationale compute(final CachedComputableRationaleContext<T, C> context) {

        final String generatedExpected = expected.compute(context);
        final String generatedActual = actual.compute(context);
        final String generatedHint = hint == null ? null : hint.compute(context);

        return new SimpleRationale(generatedExpected, generatedActual, generatedHint);
    }
}
