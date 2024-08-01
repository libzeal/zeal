package io.github.libzeal.zeal.expression.predicate;

import io.github.libzeal.zeal.expression.evaluation.Rationale;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * A {@link Rationale} implementation that does not compute its values until the values are requested.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class DeferredRationale<T> implements Rationale {

    private final T subject;
    private final ValueSupplier<T> expected;
    private final ValueSupplier<T> actual;
    private final ValueSupplier<T> hint;

    /**
     * Creates a generator without a hint.
     *
     * @param subject
     *     The subject from which to generate the rationale.
     * @param expected
     *     A supplier that provides the expected value.
     * @param actual
     *     A supplier that provides the actual value.
     *
     * @throws NullPointerException
     *     Either the expected or actual value generators are {@code null}.
     */
    public DeferredRationale(T subject, ValueSupplier<T> expected, ValueSupplier<T> actual) {
        this(subject, expected, actual, null);
    }

    /**
     * Creates a generator.
     *
     * @param subject
     *     The subject from which to generate the rationale.
     * @param expected
     *     A supplier that provides the expected value.
     * @param actual
     *     A supplier that provides the actual value.
     * @param hint
     *     A supplier that provides the hint.
     *
     * @throws NullPointerException
     *     Either the expected or actual value generators are {@code null} (the hint may me {@code null}).
     */
    public DeferredRationale(T subject, ValueSupplier<T> expected, ValueSupplier<T> actual, ValueSupplier<T> hint) {
        this.subject = subject;
        this.expected = requireNonNull(expected);
        this.actual = requireNonNull(actual);
        this.hint = hint;
    }

    @Override
    public String expected() {
        return expected.compute(subject);
    }

    @Override
    public String actual() {
        return actual.compute(subject);
    }

    @Override
    public Optional<String> hint() {
        return Optional.ofNullable(hint)
            .map(h -> h.compute(subject));
    }
}
