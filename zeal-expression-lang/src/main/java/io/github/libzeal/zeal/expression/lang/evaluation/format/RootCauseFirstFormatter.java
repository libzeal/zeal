package io.github.libzeal.zeal.expression.lang.evaluation.format;

import io.github.libzeal.zeal.expression.lang.evaluation.*;

import static java.util.Objects.requireNonNull;

public class RootCauseFirstFormatter implements Formatter {

    private final EvaluationFormatter evaluationFormatter;

    public RootCauseFirstFormatter(final EvaluationFormatter evaluationFormatter) {
        this.evaluationFormatter = requireNonNull(evaluationFormatter);
    }

    @Override
    public String format(final Evaluation evaluation) {

        final FormatterContext context = new FormatterContext(0);
        final StringBuilder builder = new StringBuilder();
        final Traverser traverser = new RootCauseFirstTraverser(evaluation, builder, evaluationFormatter);

        final RootCause rootCause = evaluation.rootCause();
        final Evaluation rootCauseEvaluation = rootCause.evaluation();

        builder.append("Root cause: ")
            .append(evaluationFormatter.format(rootCauseEvaluation, context))
            .append("\n")
            .append(evaluationFormatter.format(rootCauseEvaluation.rationale(), context))
            .append("\n\n");

        evaluation.traverseDepthFirst(traverser);

        return builder.toString();
    }

    private static final class RootCauseFirstTraverser implements Traverser {

        private final Evaluation rootEvaluation;
        private final StringBuilder builder;
        private final EvaluationFormatter evaluationFormatter;

        public RootCauseFirstTraverser(final Evaluation rootEvaluation, final StringBuilder builder,
                                       final EvaluationFormatter evaluationFormatter) {
            this.rootEvaluation = requireNonNull(rootEvaluation);
            this.builder = requireNonNull(builder);
            this.evaluationFormatter = requireNonNull(evaluationFormatter);
        }

        @Override
        public void on(final Evaluation evaluation, final TraversalContext context) {

            final FormatterContext formatterContext = new FormatterContext(context.depth());
            final String formattedText = formattedText(evaluation, formatterContext);

            builder.append(formattedText)
                .append("\n");
        }

        private String formattedText(final Evaluation evaluation, final FormatterContext formatterContext) {

            final RootCause rootCause = rootEvaluation.rootCause();

            if (rootCause.is(evaluation)) {
                return evaluationFormatter.format(rootCause, formatterContext);
            }
            else {
                return evaluationFormatter.format(evaluation, formatterContext);
            }
        }

    }
}
