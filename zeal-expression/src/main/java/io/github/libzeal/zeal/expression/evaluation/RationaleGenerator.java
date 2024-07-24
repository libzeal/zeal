package io.github.libzeal.zeal.expression.evaluation;

public class RationaleGenerator<T> {

    private final ValueSupplier<T> expected;
    private final ValueSupplier<T> actual;
    private final ValueSupplier<T> hint;

    public RationaleGenerator(ValueSupplier<T> expected, ValueSupplier<T> actual) {
        this(expected, actual, null);
    }

    public RationaleGenerator(ValueSupplier<T> expected, ValueSupplier<T> actual, ValueSupplier<T> hint) {
        this.expected = expected;
        this.actual = actual;
        this.hint = hint;
    }

    public Rationale generate(T subject) {
        return new Rationale(
            expected.compute(subject),
            actual.compute(subject),
            hint == null ? null : hint.compute(subject)
        );
    }
}
