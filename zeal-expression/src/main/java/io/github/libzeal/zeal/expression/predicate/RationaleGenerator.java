package io.github.libzeal.zeal.expression.predicate;

import io.github.libzeal.zeal.expression.evaluation.Rationale;

/**
 * A generator that creates {@link Rationale} instances based on a given subject.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public interface RationaleGenerator<T> {

    /**
     * Generates a new {@link Rationale} based on the supplied subject.
     *
     * @param subject
     *     The subject to used as the basis for the rationale.
     *
     * @return A {@link Rationale} instance based on the supplied subject.
     */
    Rationale generate(T subject);
}
