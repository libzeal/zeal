package io.github.libzeal.zeal.logic.evaluation.format;

import io.github.libzeal.zeal.logic.CompoundExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;
import io.github.libzeal.zeal.logic.rationale.Rationale;

import java.util.List;

/**
 * A simple formatter that provides information about the root cause of a failed evaluation, as well as a tree-based
 * view of the evaluation.
 *
 * @author Justin Albano
 */
public class SimpleFormatter implements Formatter {

    static final String TRUE_ROOT_CAUSE_NOTE = "A true expression may be a root cause if it belongs to a negated " +
        "expression, such as NOT, NAND, or NOR";
    private static final String INDENT = "    ";
    private static final String FIELD_FORMAT = "%-11s : %s\n";

    /**
     * Creates a new formatter.
     */
    public SimpleFormatter() {
    }

    @Override
    public String format(final Evaluation evaluation) {

        final StringBuilder builder = new StringBuilder();

        if (!evaluation.isTrue()) {
            appendRootCauses(evaluation, builder);
        }

        builder.append("Evaluation:\n");
        appendEvaluation(evaluation, evaluation.rootCauses(), 0, builder);

        return builder.toString().trim();
    }

    private static void appendRootCauses(final Evaluation evaluation, final StringBuilder builder) {

        builder.append("Root Causes:\n");

        for (final Cause.Trace trace : evaluation.causeTraces()) {

            final Evaluation rootCause = trace.rootCause();
            final Rationale rationale = rootCause.rationale();

            appendField("Expression", rootCause.name(), builder);
            appendField("Expected", rationale.expected(), builder);
            appendField("Actual", rationale.actual(), builder);
            rationale.hint().ifPresent(hint -> appendField("Hint", hint, builder));
            appendField("Cause Trace", trace.toString(), builder);

            if (rootCause.isTrue()) {
                appendField("Notes", TRUE_ROOT_CAUSE_NOTE, builder);
            }

            builder.append("\n");
        }
    }

    private static void appendField(final String key, final String value, final StringBuilder builder) {
        builder.append(String.format(FIELD_FORMAT, key, value));
    }

    private static void appendEvaluation(final Evaluation evaluation, final List<Evaluation> rootCauses,
                                         final int depth, final StringBuilder builder) {

        builder.repeat(INDENT, depth)
            .append(evaluation.isTrue() ? "[T] " : "[F] ")
            .append(evaluation.name())
            .append(" (")
            .append(SimpleDurationFormatter.format(evaluation.elapsedTime()))
            .append(")");

        if (rootCauses.stream().anyMatch(rootCause -> rootCause == evaluation)) {
            builder.append("  <---[ Root Cause ]");
        }

        builder.append("\n");

        if (evaluation instanceof final CompoundEvaluation compound) {
            for (final SkippableEvaluation child : compound.children()) {
                switch (child) {
                    case Evaluated(Evaluation evaluated) -> appendEvaluation(evaluated, rootCauses, depth + 1, builder);
                    case Skipped skipped -> appendSkipped(skipped.expression(), depth + 1, builder);
                }
            }
        }
    }

    private static void appendSkipped(final Expression expression, final int depth, final StringBuilder builder) {

        builder.repeat(INDENT, depth)
            .append("[ ] ")
            .append(expression.name())
            .append("\n");

        if (expression instanceof final CompoundExpression compound) {
            for (final Expression child : compound.children()) {
                appendSkipped(child, depth + 1, builder);
            }
        }
    }
}
