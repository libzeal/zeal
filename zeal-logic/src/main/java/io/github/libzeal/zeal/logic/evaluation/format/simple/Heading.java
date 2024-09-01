package io.github.libzeal.zeal.logic.evaluation.format.simple;

import static java.util.Objects.requireNonNull;

class Heading {

    private final String text;

    public Heading(final String text) {
        this.text = requireNonNull(text);
    }

    public String text() {
        return text;
    }
}
