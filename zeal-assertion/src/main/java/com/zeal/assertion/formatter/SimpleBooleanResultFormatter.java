package com.zeal.assertion.formatter;

import com.zeal.expression.BooleanResult;
import com.zeal.expression.Explanation;
import com.zeal.expression.BooleanResultFormatter;

public class SimpleBooleanResultFormatter implements BooleanResultFormatter {

    private final String title;

    public SimpleBooleanResultFormatter(String title) {
        this.title = title;
    }

    @Override
    public String format(BooleanResult result, String location) {

        final StringBuilder builder = new StringBuilder();

        builder.append("\n\n");
        builder.append("================================================================================\n");
        builder.append(" ").append(title).append("\n");
        builder.append("================================================================================\n\n");

        result.explanation().ifPresent(explanation -> {
                    builder.append("  Description: ").append(explanation.description()).append("\n");
                    builder.append("  Expected:    ").append(explanation.expected()).append("\n");
                    builder.append("  Actual:      ").append(explanation.actual()).append("\n");

                    explanation.hint().ifPresent(hint -> {
                        builder.append("  Hint:        ").append(hint).append("\n");
                    });

                    if (location != null) {
                        builder.append("  At:          ").append(location).append("\n");
                    }
                });

        builder.append("\n================================================================================\n\n");

        return builder.toString();
    }
}
