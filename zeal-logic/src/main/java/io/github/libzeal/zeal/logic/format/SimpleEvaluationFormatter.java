package io.github.libzeal.zeal.logic.format;

import io.github.libzeal.zeal.logic.CompoundExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;
import io.github.libzeal.zeal.logic.rationale.Rationale;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

/**
 * A simple formatter that provides information about the root cause of a failed
 * evaluation, as well as a tree-based view of the evaluation.
 *
 * @author Justin Albano
 */
public class SimpleEvaluationFormatter implements EvaluationFormatter {

    static final String TRUE_ROOT_CAUSE_NOTE = "A true expression may be a root cause if it belongs to a negated " +
            "expression, such as NOT, NAND, or NOR";
    private static final String INDENT = "    ";
    private static final String FIELD_FORMAT = "%-13s : %s\n";

    /**
     * Creates a new formatter.
     */
    public SimpleEvaluationFormatter() {
    }

    @Override
    public String format(final Evaluation evaluation) {

        final StringBuilder builder = new StringBuilder();
        final List<Cause.Trace> traces = evaluation.cause().traces();

        if (!evaluation.isTrue()) {
            appendRootCauses(traces, builder);
        }

        builder.append("Evaluation:\n");
        appendEvaluation(evaluation, rootCausesOf(traces), 0, builder);

        return builder.toString().trim();
    }

    private static void appendRootCauses(final List<Cause.Trace> traces, final StringBuilder builder) {

        builder.append("Root Causes:\n");

        for (final Cause.Trace trace : traces) {

            final Evaluation rootCause = trace.rootCause();
            final Rationale rationale = rootCause.rationale();

            appendField("- Expression", rootCause.name(), builder);
            appendField("  Expected", rationale.expected(), builder);
            appendField("  Actual", rationale.actual(), builder);
            rationale.hint().ifPresent(hint -> appendField("  Hint", hint, builder));
            appendField("  Cause Trace", trace.toString(), builder);

            if (rootCause.isTrue()) {
                appendField("  Notes", TRUE_ROOT_CAUSE_NOTE, builder);
            }
        }

        builder.append("\n");
    }

    private static void appendField(
            final String key, final String value,
            final StringBuilder builder
    ) {
        builder.append(String.format(FIELD_FORMAT, key, value));
    }

    private static Set<Evaluation> rootCausesOf(final List<Cause.Trace> traces) {

        // An identity set is used so that the Set is constructed of unique
        // Evaluations by identity rather than according to equals(), which
        // might match for Evaluations with the same field values, like two
        // Tautologies. This is critical when marking the exact instance that is
        // a root cause during formatting, since the comparison will be done by
        // identity, not by field comparisons.
        //
        // This set also reduces lookup to O(1) rather than O(n) for a List. A
        // HashSet is not used because it relies on equals() and hashCode() to
        // compare, requiring a CompoundEvaluation to hash its entire tree.
        final Set<Evaluation> rootCauses = identitySet();

        for (final Cause.Trace trace : traces) {
            rootCauses.add(trace.rootCause());
        }

        return rootCauses;
    }

    private static Set<Evaluation> identitySet() {
        return Collections.newSetFromMap(new IdentityHashMap<>());
    }

    private static void appendEvaluation(
            final Evaluation evaluation, final Set<Evaluation> rootCauses,
            final int depth, final StringBuilder builder
    ) {

        builder.repeat(INDENT, depth)
                .append(evaluation.isTrue() ? "[T] " : "[F] ")
                .append(evaluation.name())
                .append(" (")
                .append(DurationFormatter.format(evaluation.duration()))
                .append(")");

        if (rootCauses.contains(evaluation)) {
            builder.append("  <---[ Root Cause ]");
        }

        builder.append("\n");

        if (evaluation instanceof final CompoundEvaluation compound) {
            for (final SkippableEvaluation child : compound.children()) {
                switch (child) {
                    case Evaluated(Evaluation evaluated) ->
                            appendEvaluation(evaluated, rootCauses, depth + 1, builder);
                    case Skipped skipped ->
                            appendSkipped(skipped.expression(), depth + 1, builder);
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
