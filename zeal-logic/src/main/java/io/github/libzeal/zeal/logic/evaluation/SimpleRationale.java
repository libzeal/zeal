package io.github.libzeal.zeal.logic.evaluation;

import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * A simple {@link Rationale} implementation that stores strings for the
 * expected value, actual value, and hint (the strings are already constructed
 * without any indirection).
 *
 * @author Justin Albano
 */
public final class SimpleRationale implements Rationale {

    private static final SimpleRationale EMPTY = new SimpleRationale("", "");
    private final String expected;
    private final String actual;
    private final @Nullable String hint;
    private final @Nullable String negatedExpected;
    private final @Nullable String negatedHint;

    /**
     * Creates an empty rationale, where the expected value, actual value, and
     * hint are all blank strings.
     *
     * @return An empty rationale.
     */
    public static SimpleRationale empty() {
        return EMPTY;
    }

    /**
     * Creates a new rationale without a hint.
     *
     * @param expected
     *         The expected value.
     * @param actual
     *         The actual value.
     */
    public SimpleRationale(final String expected, final String actual) {
        this(expected, actual, null);
    }

    /**
     * Creates a new rationale.
     *
     * @param expected
     *         The expected value.
     * @param actual
     *         The actual value.
     * @param hint
     *         A hint as to how the expected and actual values should match.
     */
    public SimpleRationale(
            final String expected,
            final String actual,
            final @Nullable String hint
    ) {
        this(expected, null, actual, hint, null);
    }

    /**
     * Creates a new rationale with a negated expected value and negated hint.
     * These values are used when the evaluation is negated, such as when it is
     * wrapped by a NOT, NOR, or NAND expression.
     *
     * @param expected
     *         The expected value.
     * @param negatedExpected
     *         The expected value when the evaluation is negated. If
     *         {@code null}, the default of {@link Rationale#negatedExpected()}
     *         is used.
     * @param actual
     *         The actual value.
     * @param hint
     *         A hint as to how the expected and actual values should match.
     * @param negatedHint
     *         The hint when the evaluation is negated. If {@code null}, the
     *         default of {@link Rationale#negatedHint()} is used.
     */
    public SimpleRationale(
            final String expected,
            final @Nullable String negatedExpected,
            final String actual,
            final @Nullable String hint,
            final @Nullable String negatedHint
    ) {
        this.expected = expected;
        this.actual = actual;
        this.hint = hint;
        this.negatedExpected = negatedExpected;
        this.negatedHint = negatedHint;
    }

    @Override
    public String expected() {
        return expected;
    }

    @Override
    public String actual() {
        return actual;
    }

    @Override
    public Optional<String> hint() {
        return Optional.ofNullable(hint);
    }

    @Override
    public String negatedExpected() {
        return negatedExpected == null ? Rationale.super.negatedExpected() : negatedExpected;
    }

    @Override
    public Optional<String> negatedHint() {
        return negatedHint == null ? Rationale.super.negatedHint() : Optional.of(negatedHint);
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof final SimpleRationale rationale)) {
            return false;
        }

        return Objects.equals(expected, rationale.expected) &&
                Objects.equals(actual, rationale.actual) &&
                Objects.equals(hint, rationale.hint) &&
                Objects.equals(negatedExpected, rationale.negatedExpected) &&
                Objects.equals(negatedHint, rationale.negatedHint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expected, actual, hint, negatedExpected, negatedHint);
    }
}
