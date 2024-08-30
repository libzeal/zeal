package io.github.libzeal.zeal.logic.evaluation.format.simple;

import io.github.libzeal.zeal.logic.rationale.Rationale;

public class SimpleRationaleFormatter implements ComponentFormatter<Rationale> {

    private final int indentation;
    private final int keyWidth;

    public SimpleRationaleFormatter(final int indentation, final int keyWidth) {
        this.indentation = indentation;
        this.keyWidth = keyWidth;
    }

    @Override
    public String format(final Rationale rationale, final SimpleFormatterContext context) {

        final StringBuilder builder = new StringBuilder();

        builder.append(line("Expected", rationale.expected(), context))
            .append("\n")
            .append(line("Actual", rationale.actual(), context));

        rationale.hint().ifPresent(hint ->
            builder.append("\n")
                .append(line("Hint", hint, context))
        );

        return builder.toString();
    }

    private String line(final String key, final String value, final SimpleFormatterContext context) {
        return Line.keyValue(key, value, indentation, keyWidth).asString(context);
    }
}
