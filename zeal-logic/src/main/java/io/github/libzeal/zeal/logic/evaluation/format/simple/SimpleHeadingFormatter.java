package io.github.libzeal.zeal.logic.evaluation.format.simple;

class SimpleHeadingFormatter implements ComponentFormatter<Heading> {

    private final int indentation;

    public SimpleHeadingFormatter(final int indentation) {
        this.indentation = indentation;
    }

    @Override
    public String format(final Heading heading, final SimpleFormatterContext context) {
        return Line.indent(indentation, heading.text() + ":\n").asString(context);
    }
}
