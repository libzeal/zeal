package io.github.libzeal.zeal.logic.unary.future.rationale;

import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.util.Formatter;

import static java.util.Objects.requireNonNull;

/**
 * A formatter that consumes a subject and provides a string representation. This interface is intended to be used to
 * generate the expected and actual values of an {@link Rationale}.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
@FunctionalInterface
public interface ComputableField<T> {

    String compute(ComputableRationaleContext<T> context);
}
