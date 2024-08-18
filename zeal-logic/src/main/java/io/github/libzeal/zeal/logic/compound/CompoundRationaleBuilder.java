package io.github.libzeal.zeal.logic.compound;

import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import static java.util.Objects.requireNonNull;

class CompoundRationaleBuilder implements RationaleBuilder {

    private final String expected;

    private CompoundRationaleBuilder(final String expected) {
        this.expected = requireNonNull(expected);
    }

    public static CompoundRationaleBuilder withExpected(final String expected) {
        return new CompoundRationaleBuilder(expected);
    }

    @Override
    public Rationale build(final CompoundEvaluator.Tally tally) {

        final String actual =
            "Passed: " + tally.passed() + ", Failed: " + tally.failed() + ", Skipped: " + tally.skipped();

        return new SimpleRationale(expected, actual);
    }
}
