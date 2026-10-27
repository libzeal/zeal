package io.github.libzeal.zeal.logic.evaluation.format.simple;

import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;

class SimpleSkippedFormatter implements ComponentFormatter<Skipped> {

    private final int indentation;

    public SimpleSkippedFormatter(final int indentation) {
        this.indentation = indentation;
    }

    @Override
    public String format(final Skipped skipped, final SimpleFormatterContext context) {
        return Line.indent(indentation, "[ ] " + skipped.name()).asString(context);
    }
}
