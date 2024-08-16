package io.github.libzeal.zeal.expression.lang.compound;

import io.github.libzeal.zeal.expression.lang.rationale.Rationale;
import io.github.libzeal.zeal.expression.lang.rationale.SimpleRationale;

import static java.util.Objects.requireNonNull;

class CompoundRationaleBuilder {

    private final String expected;

    private CompoundRationaleBuilder(final String expected) {
        this.expected = requireNonNull(expected);
    }

    public static CompoundRationaleBuilder withExpected(final String expected) {
        return new CompoundRationaleBuilder(expected);
    }

    public Rationale build(final CompoundEvaluator.Tally tally) {

        final String actual =
            "Passed: " + tally.passed() + ", Failed: " + tally.failed() + ", Skipped: " + tally.skipped();

        return new SimpleRationale(expected, actual);
    }
}
