package io.github.libzeal.zeal.logic.unary.future.rationale;

import io.github.libzeal.zeal.logic.rationale.Rationale;

/**
 * A generator that creates {@link Rationale} instances based on a supplied subject.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
@FunctionalInterface
public interface ComputableRationale<T> {

    /**
     * Generates a new rationale based on the supplied subject.
     *
     * @param subject
     *     The subject.
     * @param passed
     *     A flag denoting if the evaluation passed.
     *
     * @return A rationale based on the supplied subject.
     */
    Rationale compute(T subject, boolean passed);
}
