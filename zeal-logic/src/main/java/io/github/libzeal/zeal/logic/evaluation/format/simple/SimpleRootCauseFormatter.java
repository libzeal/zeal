package io.github.libzeal.zeal.logic.evaluation.format.simple;

import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.rationale.Rationale;

class SimpleRootCauseFormatter implements ComponentFormatter<Evaluation> {

    static final int KEY_WIDTH = 11;
    static final String TRUE_ROOT_CAUSE_NOTE = "A true expression may be a root cause if it belongs to a negated expression, such as " +
        "NOT, NAND, or NOR";
    private final int indentation;
    private final ComponentFormatter<Rationale> rationaleFormatter;
    private final ComponentFormatter<Heading> headingFormatter;

    public SimpleRootCauseFormatter(final int indentation) {
        this.indentation = indentation;
        this.rationaleFormatter = new SimpleRationaleFormatter(indentation, KEY_WIDTH);
        this.headingFormatter = new SimpleHeadingFormatter(indentation);
    }

    @Override
    public String format(final Evaluation evaluation, final SimpleFormatterContext context) {

        if (!evaluation.result().isFalse()) {
            return "";
        }

        final StringBuilder builder = new StringBuilder();
        final Heading heading = new Heading("Root Causes");

        builder.append(headingFormatter.format(heading, context));

        for (final Cause.Trace trace : evaluation.causeTraces()) {
            builder.append(format(trace, context))
                .append("\n\n");
        }

        return builder.toString();
    }

    private String format(final Cause.Trace trace, final SimpleFormatterContext context) {

        final StringBuilder builder = new StringBuilder();
        final Evaluation rootCause = trace.rootCause();

        builder.append(rootCauseLine("Expression", rootCause.name(), context))
            .append("\n")
            .append(rationaleFormatter.format(rootCause.rationale(), context))
            .append("\n")
            .append(rootCauseLine("Cause Trace", trace.toString(), context));

        if (rootCause.result().isTrue()) {
            builder.append("\n")
                .append(rootCauseLine("Notes", TRUE_ROOT_CAUSE_NOTE, context));
        }

        return builder.toString();
    }

    private String rootCauseLine(final String key, final String value, final SimpleFormatterContext context) {
        return Line.keyValue(key, value, indentation, KEY_WIDTH).asString(context);
    }
}
