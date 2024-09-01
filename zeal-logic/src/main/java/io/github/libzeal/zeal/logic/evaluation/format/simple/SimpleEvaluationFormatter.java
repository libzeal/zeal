package io.github.libzeal.zeal.logic.evaluation.format.simple;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

class SimpleEvaluationFormatter implements ComponentFormatter<Evaluation> {

    private final int indentation;
    private final ComponentFormatter<Result> resultFormatter;
    private final ComponentFormatter<Duration> elapsedTimeFormatter;

    public SimpleEvaluationFormatter(final int indentation, final ComponentFormatter<Result> resultFormatter,
                                     final ComponentFormatter<Duration> elapsedTimeFormatter) {
        this.indentation = indentation;
        this.resultFormatter = requireNonNull(resultFormatter);
        this.elapsedTimeFormatter = requireNonNull(elapsedTimeFormatter);
    }

    @Override
    public String format(final Evaluation evaluation, final SimpleFormatterContext context) {

        final StringBuilder builder = new StringBuilder();

        builder.append(Line.indent(indentation).asString(context))
            .append(resultFormatter.format(evaluation.result(), context))
            .append(" ")
            .append(evaluation.name())
            .append(" (")
            .append(elapsedTimeFormatter.format(evaluation.elapsedTime(), context))
            .append(")");

        if (context.isRootCause(evaluation)) {
            builder.append("  <---[ Root Cause ]");
        }

        return builder.toString();
    }
}
