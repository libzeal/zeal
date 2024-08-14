package io.github.libzeal.zeal.expression.lang.rationale;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Hint {

    private final String text;
    private final String opposite;

    public Hint(final String text, final String opposite) {
        this.text = requireNonNull(text);
        this.opposite = requireNonNull(opposite);
    }

    public static Hint symmetrical(final String text) {
        return new Hint(text, text);
    }

    public String text() {
        return text;
    }

    public String opposite() {
        return opposite;
    }

    @Override
    public String toString() {
        return text();
    }

    public Hint negate() {
        return new Hint(opposite, text);
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) return true;
        if (!(o instanceof Hint)) return false;

        final Hint hint = (Hint) o;
        return Objects.equals(text, hint.text) && Objects.equals(opposite, hint.opposite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, opposite);
    }
}
