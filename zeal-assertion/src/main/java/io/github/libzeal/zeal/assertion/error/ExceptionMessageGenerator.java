package io.github.libzeal.zeal.assertion.error;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.evaluation.format.EvaluationFormatter;
import io.github.libzeal.zeal.expression.evaluation.format.SimpleEvaluationFormatter;

class ExceptionMessageGenerator {

    public String generate(Evaluation eval, String message, StackTraceElement[] trace) {

        final EvaluationFormatter formatter = new SimpleEvaluationFormatter();

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
