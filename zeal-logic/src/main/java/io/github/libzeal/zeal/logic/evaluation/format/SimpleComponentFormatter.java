package io.github.libzeal.zeal.logic.evaluation.format;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.cause.RootCauseChain;
import io.github.libzeal.zeal.logic.rationale.Rationale;

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
    public String format(final Rationale rationale, final ComponentContext context) {
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
    public String format(final Cause cause, final RootCauseChain chain, final ComponentContext context) {

        final StringBuilder builder = new StringBuilder();
        final Evaluation evaluation = cause.evaluation();
        final List<String> rootCauseChainElements = chain.stream()
            .map(Cause::toString)
            .collect(Collectors.toList());

        builder.append("Root cause:\n")
            .append(rootCauseLine("Expression", evaluation.name()))
            .append("\n")
            .append(formatRationale(evaluation.rationale(), context, ROOT_CAUSE_RATIONALE_KEY_WIDTH))
            .append("\n")
            .append(
                rootCauseLine(
                    "Chain",
                    String.join(" --> ", rootCauseChainElements)
                )
            );

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
    public String format(final Evaluation evaluation, final ComponentContext context) {

        final StringBuilder builder = new StringBuilder();

        builder.append(indent(context))
            .append("[").append(format(evaluation.result())).append("] ")
            .append(evaluation.name());

        if (context.isRootCause(evaluation)) {
            builder.append("  <---[ Root Cause ]");
        }

        return builder.toString();
    }

    private static String format(Result result) {

        switch (result) {
            case TRUE:
                return "T";
            case FALSE:
                return "F";
            default:
                return " ";
        }
    }
}