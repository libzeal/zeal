package io.github.libzeal.zeal.expression.evalulation.formatter;

import io.github.libzeal.zeal.expression.evalulation.EvaluatedExpression;
import io.github.libzeal.zeal.expression.evalulation.EvaluationState;

public class SimpleEvaluatedExpressionFormatter implements EvaluatedExpressionFormatter {

    private static final String INDENT = "       ";

    @Override
    public String format(EvaluatedExpression evaluatedExpression) {
        return formatExpression(evaluatedExpression, 0);
    }

    private static String formatExpression(EvaluatedExpression expression, int nestedLevel) {

        StringBuilder builder = new StringBuilder();

        builder.append(intent(nestedLevel));

        builder.append("[")
                .append(formatState(expression.state()))
                .append("] ")
                .append(expression.name())
                .append("\n");

        if (expression.children().isEmpty() && expression.state().equals(EvaluationState.FAILED)) {

            builder.append(intent(nestedLevel + 1))
                    .append("- Expected: ").append(expression.expected()).append("\n")
                    .append(intent(nestedLevel + 1))
                    .append("- Actual:   ").append(expression.actual()).append("\n");
        }

        int childNestedLevel = nestedLevel + 1;

        for (EvaluatedExpression child: expression.children()) {

            String formattedChild = formatExpression(child, childNestedLevel);

            builder.append(formattedChild);
        }

        return builder.toString();
    }

    private static String intent(int level) {

        StringBuilder intent = new StringBuilder();

        for (int i = 0; i < level; i++) {
            intent.append(INDENT);
        }

        return intent.toString();
    }

    private static String formatState(EvaluationState state) {

        switch (state) {
            case PASSED:
                return "PASS";
            case FAILED:
                return "FAIL";
            case SKIPPED:
                return "    ";
        }

        return "";
    }
}
