package io.github.libzeal.zeal.expression.predicate;

import io.github.libzeal.zeal.expression.evaluation.Rationale;

import static java.util.Objects.requireNonNull;

/**
 * A {@link RationaleGenerator} that uses {@link ValueSupplier} instances for the expected value, actual value, and
 * hint. This indirection ensures that the expected value, actual value, and hint are not computed unless needed.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class DeferredRationaleGenerator<T> implements RationaleGenerator<T> {

    private final ValueSupplier<T> expected;
    private final ValueSupplier<T> actual;
    private final ValueSupplier<T> hint;

    /**
     * Creates a generator without a hint.
     *
     * @param expected
     *     A supplier that provides the expected value.
     * @param actual
     *     A supplier that provides the actual value.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public DeferredRationaleGenerator(ValueSupplier<T> expected, ValueSupplier<T> actual) {
        this(expected, actual, null);
    }

    /**
     * Creates a generator.
     *
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
    public DeferredRationaleGenerator(ValueSupplier<T> expected, ValueSupplier<T> actual, ValueSupplier<T> hint) {
        this.expected = requireNonNull(expected);
        this.actual = requireNonNull(actual);
        this.hint = hint;
    }

    @Override
    public Rationale generate(T subject) {
        return new Rationale(
            expected.compute(subject),
            actual.compute(subject),
            hint == null ? null : hint.compute(subject)
        );
    }
}
