package io.github.libzeal.zeal.logic.evaluation.format.simple;

import static java.util.Objects.requireNonNull;

record Heading(String text) {

    Heading {
        requireNonNull(text);
    }
}
