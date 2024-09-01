package io.github.libzeal.zeal.logic.evaluation.format.simple;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.cause.RootCauseChain;
import io.github.libzeal.zeal.logic.rationale.Rationale;

class SimpleRootCauseFormatter implements ComponentFormatter<Cause> {

    static final int KEY_WIDTH = 10;
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
    public String format(final Cause cause, final SimpleFormatterContext context) {

        final Result result = cause.evaluation().result();

        if (result.isTrue() || result.isSkipped()) {
            return "";
        }

        final StringBuilder builder = new StringBuilder();
        final RootCauseChain chain = cause.rootCauseChain();
        final Cause rootCause = cause.rootCause();
        final Evaluation rootCauseEvaluation = rootCause.evaluation();
        final Heading heading = new Heading("Root Cause");

        builder.append(headingFormatter.format(heading, context))
            .append(rootCauseLine("Expression", rootCauseEvaluation.name(), context))
            .append("\n")
            .append(rationaleFormatter.format(rootCauseEvaluation.rationale(), context))
            .append("\n")
            .append(rootCauseLine("Chain", chain.toString(), context));

        if (rootCauseEvaluation.result().isTrue()) {
            builder.append("\n")
                .append(
                    rootCauseLine(
                        "Notes",
                        TRUE_ROOT_CAUSE_NOTE,
                        context
                    )
                );
        }

        builder.append("\n\n");

        return builder.toString();
    }

    private String rootCauseLine(final String key, final String value, final SimpleFormatterContext context) {
        return Line.keyValue(key, value, indentation, KEY_WIDTH).asString(context);
    }

}
