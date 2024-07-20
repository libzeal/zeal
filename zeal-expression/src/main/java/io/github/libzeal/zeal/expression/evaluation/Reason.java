package io.github.libzeal.zeal.expression.evaluation;

import java.util.Optional;

public class Reason {

    private final String expected;
    private final String actual;
    private final String hint;

    public static Reason empty() {
        return new Reason("", "", "");
    }

    public Reason(String expected, String actual) {
        this(expected, actual, null);
    }

    public Reason(String expected, String actual, String hint) {
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
