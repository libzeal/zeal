package io.github.libzeal.zeal.logic.format;

import io.github.libzeal.zeal.logic.CompoundExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.*;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;

import java.util.*;

/**
 * A simple formatter that provides information about the root cause of a failed
 * evaluation, as well as a tree-based view of the evaluation.
 *
 * @author Justin Albano
 */
public final class SimpleEvaluationFormatter implements EvaluationFormatter {

    private static final String INDENT = "    ";
    private static final String FIELD_FORMAT = "%-11s : %s\n";
    private static final String SINGLE_ROOT_CAUSE_INDENT = "  ";
    private static final int MAX_LISTED_ROOT_CAUSES = 9;
    private static final String ROOT_CAUSE_MARKER = "  <-- Root cause";
    private static final String DECIDED_BY_MARKER = "  <-- Decided by";

    /**
     * Creates a new formatter.
     */
    public SimpleEvaluationFormatter() {
    }

    @Override
    public String format(final Evaluation evaluation) {

        final StringBuilder builder = new StringBuilder();
        final List<Cause.Trace> traces = evaluation.cause().traces();
        final Map<Evaluation, String> names = displayNamesOf(evaluation);

        if (!evaluation.isTrue()) {
            appendRootCauses(traces, names, builder);
        }

        builder.append("Evaluation:\n");
        appendEvaluation(evaluation, markersOf(evaluation, traces), names, 0, builder);

        return builder.toString().trim();
    }

    private static void appendRootCauses(
            final List<Cause.Trace> traces,
            final Map<Evaluation, String> names,
            final StringBuilder builder
    ) {

        if (traces.size() == 1) {
            builder.append("Root Cause:\n");
            appendRootCause(traces.getFirst(), names, SINGLE_ROOT_CAUSE_INDENT, SINGLE_ROOT_CAUSE_INDENT, builder);
        }
        else {
            appendNumberedRootCauses(traces, names, builder);
        }

        builder.append("\n");
    }

    private static void appendNumberedRootCauses(
            final List<Cause.Trace> traces,
            final Map<Evaluation, String> names,
            final StringBuilder builder
    ) {

        final int listed = Math.min(traces.size(), MAX_LISTED_ROOT_CAUSES);
        final int numberWidth = String.valueOf(listed).length() + ". ".length();
        final String indent = " ".repeat(numberWidth);

        builder.append("Root Causes (")
                .append(traces.size())
                .append("):\n");

        for (int i = 0; i < listed; i++) {

            final String format = "%-" + numberWidth + "s";
            final String number = String.format(format, (i + 1) + ".");

            appendRootCause(traces.get(i), names, number, indent, builder);
        }

        if (traces.size() > listed) {
            builder.append("...")
                    .append(traces.size() - listed)
                    .append(" more...\n");
        }
    }

    private static void appendRootCause(
            final Cause.Trace trace,
            final Map<Evaluation, String> names,
            final String firstLinePrefix,
            final String prefix,
            final StringBuilder builder
    ) {

        final Rationale rationale = trace.rootCause().rationale();
        final List<String> traceNames = trace.evaluations()
                .stream()
                .map(names::get)
                .toList();

        appendField(firstLinePrefix, "Expression", traceNames.getLast(), builder);

        if (trace.isNegated()) {
            appendField(prefix, "Expected", rationale.negatedExpected(), builder);
            appendField(prefix, "Actual", rationale.actual(), builder);
            rationale.negatedHint().ifPresent(hint -> appendField(prefix, "Hint", hint, builder));
        }
        else {
            appendField(prefix, "Expected", rationale.expected(), builder);
            appendField(prefix, "Actual", rationale.actual(), builder);
            rationale.hint().ifPresent(hint -> appendField(prefix, "Hint", hint, builder));
        }

        appendField(prefix, "Cause trace", String.join(" -> ", traceNames), builder);
    }

    private static void appendField(
            final String prefix, final String key, final String value,
            final StringBuilder builder
    ) {
        builder.append(prefix)
                .append(String.format(FIELD_FORMAT, key, value));
    }

    private static Map<Evaluation, String> markersOf(final Evaluation root, final List<Cause.Trace> traces) {

        // An identity map is used so that the root causes are keyed by
        // identity rather than according to equals(), which might match for
        // Evaluations with the same field values, like two Tautologies. This
        // is critical when marking the exact instance that is a root cause
        // during formatting, since the comparison will be done by identity,
        // not by field comparisons.
        //
        // This map also reduces lookup to O(1) rather than O(n) for a List. A
        // HashMap is not used because it relies on equals() and hashCode() to
        // compare, requiring a CompoundEvaluation to hash its entire tree.
        final Map<Evaluation, String> markers = new IdentityHashMap<>();

        for (int i = 0; i < traces.size(); i++) {

            final Evaluation rootCause = traces.get(i).rootCause();

            // The root is never marked, since its result is already shown on
            // its own line and, when it fails, it is explained under the root
            // cause heading
            if (rootCause != root) {
                markers.putIfAbsent(rootCause, markerOf(root, traces.size(), i + 1));
            }
        }

        return markers;
    }

    private static String markerOf(final Evaluation root, final int rootCauseCount, final int number) {

        if (root.isTrue()) {
            return DECIDED_BY_MARKER;
        }
        else if (rootCauseCount == 1) {
            return ROOT_CAUSE_MARKER;
        }
        else {
            return ROOT_CAUSE_MARKER + " " + number;
        }
    }

    private static void appendEvaluation(
            final Evaluation evaluation, final Map<Evaluation, String> markers,
            final Map<Evaluation, String> names, final int depth, final StringBuilder builder
    ) {

        builder.repeat(INDENT, depth)
                .append(evaluation.isTrue() ? "[T] " : "[F] ")
                .append(names.get(evaluation))
                .append(" (")
                .append(DurationFormatter.format(evaluation.duration()))
                .append(")");

        final String marker = markers.get(evaluation);

        if (marker != null) {
            builder.append(marker);
        }

        builder.append("\n");

        if (evaluation instanceof final CompoundEvaluation compound) {

            final List<SkippableEvaluation> children = compound.children();
            final List<String> childNames = displayNamesOfChildren(compound);

            for (int i = 0; i < children.size(); i++) {
                switch (children.get(i)) {
                    case Evaluated(Evaluation evaluated) ->
                            appendEvaluation(evaluated, markers, names, depth + 1, builder);
                    case Skipped skipped ->
                            appendSkipped(skipped.expression(), childNames.get(i), depth + 1, builder);
                }
            }
        }
    }

    private static void appendSkipped(
            final Expression expression, final String displayName,
            final int depth, final StringBuilder builder
    ) {

        builder.repeat(INDENT, depth)
                .append("[-] ")
                .append(displayName)
                .append(" (skipped)\n");

        if (expression instanceof final CompoundExpression compound) {

            final List<Expression> children = compound.children();
            final List<String> names = children.stream().map(Expression::name).toList();
            final List<String> displayNames = displayNames(names);

            for (int i = 0; i < children.size(); i++) {
                appendSkipped(
                        children.get(i),
                        displayNames.get(i),
                        depth + 1,
                        builder
                );
            }
        }
    }

    /**
     * Obtains the display name of every evaluation in the supplied tree. The
     * root keeps its own name, while the children of each compound evaluation
     * are numbered among their siblings (see {@link #displayNames(List)}).
     * <p>
     * The display names are keyed by identity, rather than by
     * {@link Object#equals(Object)}. Each occurrence of a repeated expression
     * (e.g., OR(e, e)) is evaluated separately, so each occurrence has its own
     * evaluation and its own key, even though the evaluations may be equal to
     * one another (according to {@link Object#equals(Object)}.
     *
     * @param root
     *         The root of the evaluation tree.
     *
     * @return The display name of every evaluation in the tree, keyed by
     * identity.
     */
    private static Map<Evaluation, String> displayNamesOf(final Evaluation root) {

        final Map<Evaluation, String> names = new IdentityHashMap<>();

        names.put(root, root.name());
        addDisplayNamesOfChildren(root, names);

        return names;
    }

    private static void addDisplayNamesOfChildren(final Evaluation evaluation, final Map<Evaluation, String> names) {

        if (evaluation instanceof final CompoundEvaluation compound) {

            final List<SkippableEvaluation> children = compound.children();
            final List<String> childNames = displayNamesOfChildren(compound);

            for (int i = 0; i < children.size(); i++) {
                if (children.get(i) instanceof Evaluated(Evaluation child)) {
                    names.put(child, childNames.get(i));
                    addDisplayNamesOfChildren(child, names);
                }
            }
        }
    }

    private static List<String> displayNamesOfChildren(final CompoundEvaluation compound) {

        final List<String> names = compound.children()
                .stream()
                .map(SkippableEvaluation::name)
                .toList();

        return displayNames(names);
    }

    /**
     * Creates display names for each of the supplied names, appending numeric
     * identifiers for repeated sibling names. For example, the names [anyOf,
     * isNull, anyOf] are displayed as [anyOf #1, isNull, anyOf #2].
     * <p>
     * Numbers are assigned by position, rather than stored per expression (such
     * as a map from each expression to its display name). The same expression
     * can appear more than once among siblings (e.g., OR(e, e)), and storing
     * numbers per expression would give each occurrence the same number. For
     * example, the map would look like {@code [e => "e #1"]}, which would cause
     * the display names to be {@code ["e #1", "e #1"]} rather than
     * {@code ["e #1", "e #2"]}.
     *
     * @param names
     *         The names of the siblings, in order.
     *
     * @return The display names of the siblings, in the same order as the
     * supplied names.
     */
    private static List<String> displayNames(final List<String> names) {

        final Map<String, Integer> totals = new HashMap<>();
        final Map<String, Integer> seen = new HashMap<>();

        for (final String name : names) {
            totals.merge(name, 1, Integer::sum);
        }

        final List<String> displayNames = new ArrayList<>();

        for (final String name : names) {

            if (totals.getOrDefault(name, 0) > 1) {

                final Integer seenSoFar = seen.merge(name, 1, Integer::sum);

                displayNames.add(name + " #" + seenSoFar);
            }
            else {
                displayNames.add(name);
            }
        }

        return displayNames;
    }
}
