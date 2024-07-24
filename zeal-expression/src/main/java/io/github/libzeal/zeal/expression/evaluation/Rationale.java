package io.github.libzeal.zeal.expression.evaluation;

import java.util.Optional;

public class Rationale {

    private final String expected;
    private final String actual;
    private final String hint;

    public static Rationale empty() {
        return new Rationale("", "", "");
    }

    public Rationale(String expected, String actual) {
        this(expected, actual, null);
    }

    public Rationale(String expected, String actual, String hint) {
        this.expected = expected;
        this.actual = actual;
        this.hint = hint;
    }

    public String expected() {
        return expected;
    }

    public String actual() {
        return actual;
    }

    public Optional<String> hint() {
        return Optional.ofNullable(hint);
    }
}
