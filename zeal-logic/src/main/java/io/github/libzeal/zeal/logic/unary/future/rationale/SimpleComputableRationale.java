package io.github.libzeal.zeal.logic.unary.future.rationale;

import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import static java.util.Objects.requireNonNull;

/**
 * A generator that stores a {@link ComputableField} for the expected, actual, and hint and computes its rationale based
 * on these suppliers.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class SimpleComputableRationale<T> implements ComputableRationale<T> {

    private final ComputableField<T> expected;
    private final ComputableField<T> actual;
    private final ComputableField<T> hint;

    /**
     * Creates a new generator.
     *
     * @param expected
     *     The supplier for the expected value.
     * @param actual
     *     The supplier for the actual value.
     * @param hint
     *     The supplier for the hint.
     *
     * @throws NullPointerException
     *     The supplied expected and actual suppliers are {@code null}.
     */
    public SimpleComputableRationale(final ComputableField<T> expected, final ComputableField<T> actual,
                                     final ComputableField<T> hint) {
        this.expected = requireNonNull(expected);
        this.actual = requireNonNull(actual);
        this.hint = hint;
    }

    /**
     * Creates a new generator.
     *
     * @param expected
     *     The supplier for the expected value.
     * @param actual
     *     The supplier for the actual value.
     *
     * @throws NullPointerException
     *     The supplied expected and actual suppliers are {@code null}.
     */
    public SimpleComputableRationale(final ComputableField<T> expected, final ComputableField<T> actual) {
        this(expected, actual, null);
    }

    @Override
    public Rationale compute(final T subject, boolean passed) {

        final String generatedExpected = expected.compute(subject, passed);
        final String generatedActual = actual.compute(subject, passed);
        final String generatedHint = hint == null ? null : hint.compute(subject, passed);

        return new SimpleRationale(generatedExpected, generatedActual, generatedHint);
    }
}
