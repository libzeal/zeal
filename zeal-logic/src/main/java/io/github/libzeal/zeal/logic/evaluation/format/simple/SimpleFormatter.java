package io.github.libzeal.zeal.logic.evaluation.format.simple;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.TraversalContext;
import io.github.libzeal.zeal.logic.evaluation.Traverser;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.format.Formatter;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

public class SimpleFormatter implements Formatter {

    static final int INDENTATION = 4;
    private final ComponentFormatter<Evaluation> evaluationFormatter;
    private final ComponentFormatter<Cause> causeFormatter;
    private final ComponentFormatter<Heading> headingFormatter;

    public SimpleFormatter() {

        final ComponentFormatter<Result> resultFormatter = new SimpleResultFormatter();
        final ComponentFormatter<Duration> durationFormatter = new SimpleDurationFormatter();

        this.evaluationFormatter = new SimpleEvaluationFormatter(INDENTATION, resultFormatter, durationFormatter);
        this.causeFormatter = new SimpleRootCauseFormatter(INDENTATION);
        this.headingFormatter = new SimpleHeadingFormatter(INDENTATION);
    }

    @Override
    public String format(final Evaluation evaluation) {

        final Cause cause = evaluation.cause();
        final SimpleFormatterContext context = new SimpleFormatterContext(cause, 0);
        final StringBuilder builder = new StringBuilder();
        final Traverser traverser = new FormattingTraverser(evaluation, builder, evaluationFormatter);
        final Heading heading = new Heading("Evaluation");

        builder.append(causeFormatter.format(cause, context))
            .append(headingFormatter.format(heading, context));

        evaluation.traverseDepthFirst(traverser);

        return builder.toString().trim();
    }

    private static final class FormattingTraverser implements Traverser {

        private final Evaluation evaluation;
        private final StringBuilder builder;
        private final ComponentFormatter<Evaluation> evaluationFormatter;

        public FormattingTraverser(final Evaluation evaluation, final StringBuilder builder,
                                   final ComponentFormatter<Evaluation> evaluationFormatter) {
            this.evaluation = requireNonNull(evaluation);
            this.builder = requireNonNull(builder);
            this.evaluationFormatter = requireNonNull(evaluationFormatter);
        }

        @Override
        public void on(final Evaluation evaluation, final TraversalContext context) {

            final SimpleFormatterContext componentContext = createContext(context);
            final String formattedText = evaluationFormatter.format(evaluation, componentContext);

            builder.append(formattedText)
                .append("\n");
        }

        private SimpleFormatterContext createContext(final TraversalContext context) {
            return new SimpleFormatterContext(evaluation.cause(), context.depth());
        }
    }
}
