package com.zeal.assertion.formatter;

import com.zeal.expression.Explanation;
import com.zeal.expression.ExplanationFormatter;

public class SimpleExplanationFormatter implements ExplanationFormatter {

    private final String title;

    public SimpleExplanationFormatter(String title) {
        this.title = title;
    }

    @Override
    public String format(Explanation explanation) {

        final StringBuilder builder = new StringBuilder();

        builder.append("\n\n");
        builder.append("================================================================================\n");
        builder.append(" ").append(title).append("\n");
        builder.append("================================================================================\n\n");
        builder.append("  Description: ").append(explanation.description()).append("\n");
        builder.append("  Expected:    ").append(explanation.expected()).append("\n");
        builder.append("  Actual:      ").append(explanation.actual()).append("\n");

        explanation.hint().ifPresent(hint -> {
            builder.append("\n  Hint:        ").append(hint).append("\n");
        });

        builder.append("\n================================================================================\n\n");

        return builder.toString();
    }
}
