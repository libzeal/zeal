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

    Rationale compute(ComputableRationaleContext<T> context);
}
