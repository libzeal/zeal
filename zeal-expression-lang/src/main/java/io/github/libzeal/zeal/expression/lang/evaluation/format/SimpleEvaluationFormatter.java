package io.github.libzeal.zeal.expression.lang.evaluation.format;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.RootCause;
import io.github.libzeal.zeal.expression.lang.rationale.Rationale;

public class SimpleEvaluationFormatter implements EvaluationFormatter {

    static final String INDENT = "    ";

    @Override
    public String format(final Rationale rationale, final FormatterContext context) {

        final StringBuilder builder = new StringBuilder();

        builder.append(indent(context.depth()))
            .append("- Expected : ")
            .append(rationale.expected())
            .append("\n")
            .append(indent(context.depth()))
            .append("- Actual   : ")
            .append(rationale.actual());

        rationale.hint().ifPresent(hint ->
            builder.append("\n")
                .append(indent(context.depth()))
                .append("- Hint     : ")
                .append(hint)
        );

        return builder.toString();
    }

    private static String indent(int level) {

        final StringBuilder indent = new StringBuilder();

        for (int i = 0; i < level; i++) {
            indent.append(INDENT);
        }

        return indent.toString();
    }

    @Override
    public String format(final Evaluation evaluation, final FormatterContext context) {
        return indent(context.depth()) +
            "[" + format(evaluation.result()) + "] " +
            evaluation.name();
    }

    @Override
    public String format(final RootCause rootCause, final FormatterContext context) {
        return format(rootCause.evaluation(), context) + "  <---[ Root Cause ]";
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
