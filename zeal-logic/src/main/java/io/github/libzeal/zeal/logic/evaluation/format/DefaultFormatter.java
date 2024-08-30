package io.github.libzeal.zeal.logic.evaluation.format;

import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.TraversalContext;
import io.github.libzeal.zeal.logic.evaluation.Traverser;

import static java.util.Objects.requireNonNull;

/**
 * A formatter that includes the root cause at the top of the formatted string, followed by the evaluation tree.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class DefaultFormatter implements Formatter {

    private final ComponentFormatter formatter;

    /**
     * Creates a new formatter.
     *
     * @param formatter
     *     The formatter used to format the components of the evaluation.
     *
     * @throws NullPointerException
     *     The supplied formatter is {@code null}.
     */
    public DefaultFormatter(final ComponentFormatter formatter) {
        this.formatter = requireNonNull(formatter);
    }

    @Override
    public String format(final Evaluation evaluation) {

        final Cause cause = evaluation.cause();
        final Cause rootCause = cause.rootCause();
        final ComponentContext context = new ComponentContext(cause, 0);
        final StringBuilder builder = new StringBuilder();
        final Traverser traverser = new FormattingTraverser(evaluation, builder, formatter);

        builder.append("Root cause:\n")
            .append(formatter.formatRootCause(rootCause, cause.rootCauseChain(), context))
            .append("Evaluation:\n");

        evaluation.traverseDepthFirst(traverser);

        return builder.toString();
    }

    private static final class FormattingTraverser implements Traverser {

        private final Evaluation evaluation;
        private final StringBuilder builder;
        private final ComponentFormatter componentFormatter;

        public FormattingTraverser(final Evaluation evaluation, final StringBuilder builder,
                                   final ComponentFormatter componentFormatter) {
            this.evaluation = requireNonNull(evaluation);
            this.builder = requireNonNull(builder);
            this.componentFormatter = requireNonNull(componentFormatter);
        }

        @Override
        public void on(final Evaluation evaluation, final TraversalContext context) {

            final ComponentContext componentContext = createContext(context);
            final String formattedText = componentFormatter.formatEvaluation(evaluation, componentContext);

            builder.append(formattedText)
                .append("\n");
        }

        private ComponentContext createContext(final TraversalContext context) {
            return new ComponentContext(evaluation.cause(), context.depth());
        }
    }
}
