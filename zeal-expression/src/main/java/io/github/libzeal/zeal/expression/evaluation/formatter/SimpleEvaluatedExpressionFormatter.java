package io.github.libzeal.zeal.expression.evaluation.formatter;

import io.github.libzeal.zeal.expression.evaluation.EvaluatedExpression;
import io.github.libzeal.zeal.expression.evaluation.Result;
import io.github.libzeal.zeal.expression.evaluation.Rationale;

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
            .append(formatState(eval.result()))
            .append("] ")
            .append(eval.name())
            .append("\n");

        if (eval.children().isEmpty() && eval.result().equals(Result.FAILED)) {
            builder.append(formatReason(eval.rationale(), nestedLevel));
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

    private static String formatState(Result state) {

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

    private static String formatReason(Rationale rationale, int nestedLevel) {

        StringBuilder builder = new StringBuilder();

        builder.append(intent(nestedLevel + 1))
            .append("- Expected : ").append(rationale.expected()).append("\n")
            .append(intent(nestedLevel + 1))
            .append("- Actual   : ").append(rationale.actual()).append("\n");

        rationale.hint().ifPresent(hint ->
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

            if (eval.result().equals(Result.PASSED)) {
                return Optional.empty();
            }
            else if (eval.result().equals(Result.FAILED) && eval.children().isEmpty()) {
                return Optional.of(new RootCause(eval.name(), eval.rationale()));
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
        private final Rationale rationale;

        public RootCause(String name, Rationale rationale) {
            this.name = name;
            this.rationale = rationale;
        }

        @Override
        public String toString() {

            StringBuilder builder = new StringBuilder();

            builder.append("Root cause:\n")
                .append("- Evaluation : ")
                .append(name)
                .append("\n")
                .append("- Expected   : ")
                .append(rationale.expected())
                .append("\n")
                .append("- Actual     : ")
                .append(rationale.actual())
                .append("\n");

            rationale.hint().ifPresent(hint ->
                builder.append("- Hint       : ")
                    .append(hint)
                    .append("\n")
            );

            return builder.append("\n")
                .toString();
        }
    }
}
