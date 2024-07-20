package io.github.libzeal.zeal.expression.evalulation.formatter;

import io.github.libzeal.zeal.expression.evalulation.EvaluatedExpression;
import io.github.libzeal.zeal.expression.evalulation.EvaluationState;
import io.github.libzeal.zeal.expression.evalulation.Reason;

import java.util.Optional;

public class SimpleEvaluatedExpressionFormatter implements EvaluatedExpressionFormatter {

    private static final String INDENT = "       ";

    @Override
    public String format(EvaluatedExpression eval) {

        final StringBuilder builder = new StringBuilder();
        final RootCauseFinder finder = new RootCauseFinder(eval);

        finder.find().ifPresent(builder::append);

        return builder.append(formatExpression(eval, 0))
            .toString();
    }

    private static String formatExpression(EvaluatedExpression eval, int nestedLevel) {

        final StringBuilder builder = new StringBuilder();

        builder.append(intent(nestedLevel))
            .append("[")
            .append(formatState(eval.state()))
            .append("] ")
            .append(eval.name())
            .append("\n");

        if (eval.children().isEmpty() && eval.state().equals(EvaluationState.FAILED)) {
            builder.append(formatReason(eval.reason(), nestedLevel));
        }

        for (EvaluatedExpression child : eval.children()) {
            builder.append(formatExpression(child, nestedLevel + 1));
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

    private static String formatReason(Reason reason, int nestedLevel) {

        StringBuilder builder = new StringBuilder();

        builder.append(intent(nestedLevel + 1))
            .append("- Expected : ").append(reason.expected()).append("\n")
            .append(intent(nestedLevel + 1))
            .append("- Actual   : ").append(reason.actual()).append("\n");

        reason.hint().ifPresent(hint ->
            builder.append(intent(nestedLevel + 1))
                .append("- Hint     : ")
                .append(hint).append("\n")
        );

        return builder.toString();
    }

    private static class RootCauseFinder {

        private final EvaluatedExpression eval;

        public RootCauseFinder(EvaluatedExpression eval) {
            this.eval = eval;
        }

        public Optional<RootCause> find() {
            return find(eval);
        }

        private static Optional<RootCause> find(EvaluatedExpression eval) {

            if (eval.state().equals(EvaluationState.PASSED)) {
                return Optional.empty();
            }
            else if (eval.state().equals(EvaluationState.FAILED) && eval.children().isEmpty()) {
                return Optional.of(new RootCause(eval.name(), eval.reason()));
            }

            RootCause rc = null;

            for (EvaluatedExpression child : eval.children()) {
                rc = find(child).orElse(null);

                if (rc != null) {
                    break;
                }
            }

            return Optional.ofNullable(rc);
        }
    }

    private static class RootCause {

        private final String name;
        private final Reason reason;

        public RootCause(String name, Reason reason) {
            this.name = name;
            this.reason = reason;
        }

        @Override
        public String toString() {

            StringBuilder builder = new StringBuilder();

            builder.append("Root cause:\n")
                .append("- Evaluation : ")
                .append(name)
                .append("\n")
                .append("- Expected   : ")
                .append(reason.expected())
                .append("\n")
                .append("- Actual     : ")
                .append(reason.actual())
                .append("\n");

            reason.hint().ifPresent(hint ->
                builder.append("- Hint       : ")
                    .append(hint)
                    .append("\n")
            );

            return builder.append("\n")
                .toString();
        }
    }
}
