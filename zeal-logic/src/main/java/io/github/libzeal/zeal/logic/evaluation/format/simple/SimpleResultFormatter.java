package io.github.libzeal.zeal.logic.evaluation.format.simple;

class SimpleResultFormatter implements ComponentFormatter<Boolean> {

    @Override
    public String format(final Boolean result, final SimpleFormatterContext context) {
        return result ? "[T]" : "[F]";
    }
}
