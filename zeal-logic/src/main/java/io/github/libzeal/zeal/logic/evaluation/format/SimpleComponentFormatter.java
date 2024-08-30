package io.github.libzeal.zeal.logic.evaluation.format;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.cause.RootCauseChain;
import io.github.libzeal.zeal.logic.rationale.Rationale;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A component formatter that creates a simple, text-based formatted string for each of the evaluation components.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class SimpleComponentFormatter implements ComponentFormatter {

    static final int INDENT_WIDTH = 4;
    static final int RATIONALE_INDENT_WIDTH = 2;
    static final int ROOT_CAUSE_RATIONALE_KEY_WIDTH = 10;
    static final int DEFAULT_RATIONALE_KEY_WIDTH = 8;

    @Override
    public String formatRationale(final Rationale rationale, final ComponentContext context) {
        return formatRationale(rationale, context, DEFAULT_RATIONALE_KEY_WIDTH);
    }

    private static String formatRationale(final Rationale rationale, final ComponentContext context, final int keyWidth) {

        final StringBuilder builder = new StringBuilder();

        builder.append(indent(context))
            .append(rationaleLine("Expected", rationale.expected(), keyWidth))
            .append("\n")
            .append(indent(context))
            .append(rationaleLine("Actual", rationale.actual(), keyWidth));

        rationale.hint().ifPresent(hint ->
            builder.append("\n")
                .append(indent(context))
                .append(rationaleLine("Hint", hint, keyWidth))
        );

        return builder.toString();
    }

    private static String rationaleLine(final String key, final String value, final int keyWidth) {
        return line(key, value, RATIONALE_INDENT_WIDTH, keyWidth);
    }

    private static String indent(final ComponentContext context) {
        return indent(context.depth(), INDENT_WIDTH);
    }

    private static String indent(final int level, final int width) {

        final StringBuilder indent = new StringBuilder();
        final String format = "%-" + width + "s";

        for (int i = 0; i < level; i++) {
            indent.append(String.format(format, " "));
        }

        return indent.toString();
    }

    private static String line(final String key, final String value, final int indent, final int keyWidth) {

        final String format = "%-" + keyWidth + "s : %s";

        return indent(1, indent) + String.format(format, key, value);
    }

    @Override
    public String formatRootCause(final Cause cause, final RootCauseChain chain, final ComponentContext context) {

        final StringBuilder builder = new StringBuilder();
        final Evaluation evaluation = cause.evaluation();

        builder.append(rootCauseLine("Expression", evaluation.name()))
            .append("\n")
            .append(formatRationale(evaluation.rationale(), context, ROOT_CAUSE_RATIONALE_KEY_WIDTH))
            .append("\n")
            .append(rootCauseLine("Chain", chain.toString()));

        if (evaluation.result().isTrue()) {
            builder.append("\n")
                .append(
                    rootCauseLine(
                        "Notes",
                        "A true expression may be a root cause if it belongs to a negated expression, such as " +
                            "NOT, NAND, or NOR"
                    )
            );
        }

        builder.append("\n\n");

        return builder.toString();
    }

    private static String rootCauseLine(final String key, final String value) {
        return line(key, value, RATIONALE_INDENT_WIDTH, ROOT_CAUSE_RATIONALE_KEY_WIDTH);
    }

    @Override
    public String formatEvaluation(final Evaluation evaluation, final ComponentContext context) {

        final StringBuilder builder = new StringBuilder();

        builder.append(indent(context))
            .append("[").append(format(evaluation.result())).append("] ")
            .append(evaluation.name())
            .append(" ")
            .append(format(evaluation.elapsedTime()));

        if (context.isRootCause(evaluation)) {
            builder.append("  <---[ Root Cause ]");
        }

        return builder.toString();
    }

    private static String format(final Result result) {

        switch (result) {
            case TRUE:
                return "T";
            case FALSE:
                return "F";
            default:
                return " ";
        }
    }

    private static String format(final Duration elapsedTime) {

        final StringBuilder builder = new StringBuilder();
        final long hoursPart = elapsedTime.toHours();
        final long minutesPart = elapsedTime.toMinutes() - (hoursPart * 60);
        final long secondsPart = (elapsedTime.toMillis() / 1000) - (hoursPart * 3600) - (minutesPart * 60);
        final long millisPart =
            elapsedTime.toMillis() - (hoursPart * 3600_000l) - (minutesPart * 60_000l) - (secondsPart * 1000);
        final long nanosPart =
            elapsedTime.toNanos() - (hoursPart * 3600_000_000_000l) - (minutesPart * 60_000_000_000l) - (secondsPart * 1000_000_000l) - (millisPart * 1000_000l);

        if (hoursPart > 0) {
            builder.append(hoursPart)
                .append("h ");
        }

        if (minutesPart > 0) {
            builder.append(minutesPart)
                .append("m ");
        }

        if (secondsPart > 0) {
            builder.append(secondsPart)
                .append("s ");
        }

        if (hoursPart == 0 && minutesPart == 0 && secondsPart == 0) {

            if (millisPart > 0) {
                builder.append(millisPart)
                    .append("ms");
            }
            else {
                if (nanosPart > 1000) {
                    builder.append(nanosPart / 1000)
                        .append("us");
                }
                else {
                    builder.append(nanosPart)
                        .append("ns");
                }
            }
        }

        return "(" + builder.toString().trim() + ")";
    }
}
