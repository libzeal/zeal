package io.github.libzeal.zeal.expression.lang.evaluation.format;

import io.github.libzeal.zeal.expression.lang.evaluation.*;

import static java.util.Objects.requireNonNull;

public class RootCauseFirstFormatter implements Formatter {

    private final EvaluationFormatter formatter;

    public RootCauseFirstFormatter(final EvaluationFormatter formatter) {
        this.formatter = requireNonNull(formatter);
    }

    @Override
    public String format(final Evaluation evaluation) {

        final Cause cause = evaluation.rootCause();
        final FormatterContext context = new FormatterContext(cause, 0);
        final StringBuilder builder = new StringBuilder();
        final Traverser traverser = new RootCauseFirstTraverser(evaluation, builder, formatter);

        builder.append(formatter.format(cause, context))
                .append("Evaluation:\n");

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

            final FormatterContext formatterContext = createContext(context);
            final String formattedText = evaluationFormatter.format(evaluation, formatterContext);

            builder.append(formattedText)
                .append("\n");
        }

        private FormatterContext createContext(final TraversalContext context) {
            return new FormatterContext(rootEvaluation.rootCause(), context.depth());
        }

    }
}
