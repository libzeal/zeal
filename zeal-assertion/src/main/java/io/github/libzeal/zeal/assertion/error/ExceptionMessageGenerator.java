package io.github.libzeal.zeal.assertion.error;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.evaluation.format.EvaluatedExpressionFormatter;
import io.github.libzeal.zeal.expression.evaluation.format.SimpleEvaluatedExpressionFormatter;

class ExceptionMessageGenerator {

    public String generate(Evaluation eval, String message, StackTraceElement[] trace) {

        final EvaluatedExpressionFormatter formatter = new SimpleEvaluatedExpressionFormatter();

        return message +
            "\n\n" +
            formatter.format(eval) +
            "\n\n" +
            "At: " +
            location(trace);
    }

    private static String location(StackTraceElement[] trace) {

        if (trace.length >= 2) {
            return trace[1].toString();
        }
        else {
            return "<unknown>";
        }
    }
}
