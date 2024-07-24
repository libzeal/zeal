package io.github.libzeal.zeal.expression.evaluation;

/**
 * A formatter that consumes a subject and provides a string representation. This interface is intended to be used to
 * generate the expected and actual values of an {@link Rationale}.
 *
 * @param <T>
 *     The type of the subject.
 */
@FunctionalInterface
public interface ValueSupplier<T> {

    /**
     * Computes the value based on the supplied subject.
     *
     * @param subject
     *     The subject to compute the value for.
     *
     * @return The computed value.
     */
    String compute(T subject);

    /**
     * Creates a {@link ValueSupplier} that always computes to the supplied value. This method can be thought of as a
     * convenience method to adapt the supplied string value to the {@link ValueSupplier} functional interface.
     *
     * @param value
     *     The value to return from the {@link ValueSupplier}.
     * @param <S>
     *     The type of the subject.
     *
     * @return A {@link ValueSupplier} that always results in the supplied value.
     */
    static <S> ValueSupplier<S> of(String value) {
        return s -> value;
    }
}
