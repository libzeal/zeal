package io.github.libzeal.zeal.logic.evaluation.format.simple;

import io.github.libzeal.zeal.logic.evaluation.Result;

class SimpleResultFormatter implements ComponentFormatter<Result> {

    @Override
    public String format(final Result result, final SimpleFormatterContext context) {

        return switch (result) {
            case TRUE -> "[T]";
            case FALSE -> "[F]";
            default -> "[ ]";
        };
    }
}
