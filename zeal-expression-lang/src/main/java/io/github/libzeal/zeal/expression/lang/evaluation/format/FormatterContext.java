package io.github.libzeal.zeal.expression.lang.evaluation.format;

public class FormatterContext {

    private final int depth;

    public FormatterContext(final int depth) {
        this.depth = depth;
    }

    public int depth() {
        return depth;
    }
}
