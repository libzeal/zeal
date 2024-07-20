package io.github.libzeal.zeal.assertion.error;

import io.github.libzeal.zeal.expression.evalulation.EvaluatedExpression;
import io.github.libzeal.zeal.expression.evaluation.formatter.EvaluatedExpressionFormatter;
import io.github.libzeal.zeal.expression.evaluation.formatter.SimpleEvaluatedExpressionFormatter;

class ExceptionMessageGenerator {

    public String generate(EvaluatedExpression eval, String message, StackTraceElement[] trace) {

        EvaluatedExpressionFormatter formatter = new SimpleEvaluatedExpressionFormatter();

        return message +
            "\n\n" +
            formatter.format(eval) +
            "\n" +
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
