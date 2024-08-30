package io.github.libzeal.zeal.logic.evaluation.format.simple;

import io.github.libzeal.zeal.logic.evaluation.Result;

public class SimpleResultFormatter implements ComponentFormatter<Result> {

    @Override
    public String format(final Result result, final SimpleFormatterContext context) {

        switch (result) {
            case TRUE:
                return "[T]";
            case FALSE:
                return "[F]";
            default:
                return "[ ]";
        }
    }
}
