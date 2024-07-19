package io.github.libzeal.zeal.expression.evalulation;

import java.util.Optional;

public class ReasonGenerator<T> {

    private final ValueFormatter<T> expected;
    private final ValueFormatter<T> actual;
    private final ValueFormatter<T> hint;

    public ReasonGenerator(ValueFormatter<T> expected, ValueFormatter<T> actual) {
        this(expected, actual, null);
    }

    public ReasonGenerator(ValueFormatter<T> expected, ValueFormatter<T> actual, ValueFormatter<T> hint) {
        this.expected = expected;
        this.actual = actual;
        this.hint = hint;
    }

    public Reason generate(T subject) {
        return new Reason(
            expected.compute(subject),
            actual.compute(subject),
            hint == null ? null : hint.compute(subject)
        );
    }
}
