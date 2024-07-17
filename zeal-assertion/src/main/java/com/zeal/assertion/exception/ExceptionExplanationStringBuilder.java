package com.zeal.assertion.exception;

import com.zeal.expression.Explanation;
import com.zeal.expression.ExplanationFormatter;

class ExceptionExplanationStringBuilder {

    private final Explanation explanation;
    private final ExplanationFormatter formatter;

    public ExceptionExplanationStringBuilder(Explanation explanation, ExplanationFormatter formatter) {
        this.explanation = explanation;
        this.formatter = formatter;
    }

    public String toString(String message, StackTraceElement[] stackTrace) {

        StringBuilder builder = new StringBuilder(message);

        if (explanation != null) {
            builder.append(formatter.format(explanation));
        }

        if (stackTrace != null && stackTrace.length > 1) {
            StackTraceElement ste = stackTrace[1];

            builder.append("At: ").append(ste.toString()).append("\n");
        }

        return builder.toString();
    }
}
