package io.github.libzeal.zeal.logic.evaluation.format.simple;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.traverse.TraversalContext;
import io.github.libzeal.zeal.logic.evaluation.traverse.DepthFirstTraverser;
import io.github.libzeal.zeal.logic.evaluation.format.Formatter;
import io.github.libzeal.zeal.logic.evaluation.traverse.Traverser;
import io.github.libzeal.zeal.logic.evaluation.traverse.TraverserAction;

import java.time.Duration;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A simple formatter that provides information about the root cause of a failed evaluation, as well as a tree-based
 * view of the evaluation.
 *
 * @author Justin Albano
 */
public class SimpleFormatter implements Formatter {

    static final int INDENTATION = 4;
    private final ComponentFormatter<Evaluation> evaluationFormatter;
    private final ComponentFormatter<Evaluation> rootCauseFormatter;
    private final ComponentFormatter<Heading> headingFormatter;

    /**
     * Creates a new formatter.
     */
    public SimpleFormatter() {

        final ComponentFormatter<Result> resultFormatter = new SimpleResultFormatter();
        final ComponentFormatter<Duration> durationFormatter = new SimpleDurationFormatter();

        this.evaluationFormatter = new SimpleEvaluationFormatter(INDENTATION, resultFormatter, durationFormatter);
        this.rootCauseFormatter = new SimpleRootCauseFormatter(INDENTATION);
        this.headingFormatter = new SimpleHeadingFormatter(INDENTATION);
    }

    @Override
    public String format(final Evaluation evaluation) {

        final List<Evaluation> rootCauses = evaluation.rootCauses();
        final SimpleFormatterContext context = new SimpleFormatterContext(rootCauses, 0);
        final StringBuilder builder = new StringBuilder();
        final Traverser traverser = new DepthFirstTraverser();
        final TraverserAction action = new FormattingTraverser(rootCauses, builder,
            evaluationFormatter);
        final Heading heading = new Heading("Evaluation");

        builder.append(rootCauseFormatter.format(evaluation, context))
            .append(headingFormatter.format(heading, context));

        traverser.traverse(evaluation, action);

        return builder.toString().trim();
    }

    private record FormattingTraverser(List<Evaluation> rootCauses,
                                       StringBuilder builder,
                                       ComponentFormatter<Evaluation> evaluationFormatter) implements TraverserAction {

            private FormattingTraverser {
                requireNonNull(rootCauses);
                requireNonNull(builder);
                requireNonNull(evaluationFormatter);
            }

            @Override
            public void on(final Evaluation evaluation, final TraversalContext context) {

                final SimpleFormatterContext componentContext = createContext(context);
                final String formattedText = evaluationFormatter.format(evaluation, componentContext);

                builder.append(formattedText)
                    .append("\n");
            }

            private SimpleFormatterContext createContext(final TraversalContext context) {
                return new SimpleFormatterContext(rootCauses, context.depth());
            }
        }
}
