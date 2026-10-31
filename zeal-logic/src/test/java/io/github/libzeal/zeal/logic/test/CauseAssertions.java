package io.github.libzeal.zeal.logic.test;

import io.github.libzeal.zeal.logic.CompoundExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.DirectCause;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CauseAssertions {

    private CauseAssertions() {}

    public static Evaluation child(final Evaluation evaluation, final int... path) {
        return assertInstanceOf(Evaluated.class, slot(evaluation, path)).evaluation();
    }

    public static Skipped skipped(final Evaluation evaluation, final int... path) {
        return assertInstanceOf(Skipped.class, slot(evaluation, path));
    }

    private static SkippableEvaluation slot(final Evaluation evaluation, final int... path) {

        final Evaluation parent = path.length == 1 ? evaluation : child(evaluation, Arrays.copyOf(path,
            path.length - 1));
        final CompoundEvaluation compound = assertInstanceOf(CompoundEvaluation.class, parent);

        return compound.children().get(path[path.length - 1]);
    }

    public static List<Skipped> skippedDescendants(final Evaluation evaluation) {

        final List<Skipped> found = new ArrayList<>();

        if (evaluation instanceof final CompoundEvaluation compound) {
            for (final SkippableEvaluation child : compound.children()) {
                switch (child) {
                    case Evaluated(Evaluation evaluated) -> found.addAll(skippedDescendants(evaluated));
                    case Skipped skipped -> addSkipped(skipped, found);
                }
            }
        }

        return found;
    }

    private static void addSkipped(final Skipped skipped, final List<Skipped> found) {

        found.add(skipped);

        if (skipped.expression() instanceof final CompoundExpression compound) {
            for (final Expression child : compound.children()) {
                addSkipped(new Skipped(child, skipped.directCause()), found);
            }
        }
    }

    public static void assertCauseIsSelf(final Evaluation evaluation) {
        assertInstanceOf(DirectCause.Self.class, evaluation.cause().direct());
    }

    public static void assertCause(final Evaluation evaluation, final Evaluation... expected) {

        final DirectCause.Delegated cause = assertInstanceOf(DirectCause.Delegated.class, evaluation.cause().direct());

        assertSameElements(List.of(expected), cause.evaluations());
    }

    public static void assertCause(final Skipped skipped, final Evaluation... expected) {
        assertSameElements(List.of(expected), skipped.directCause().evaluations());
    }

    public static void assertTraces(final Evaluation evaluation, final String... expected) {
        assertTraceNames(evaluation.cause().traces(), expected);
    }

    public static void assertTraces(final Skipped skipped, final String... expected) {
        assertTraceNames(tracesOf(skipped), expected);
    }

    public static void assertRootCauses(final Evaluation evaluation, final Evaluation... expected) {
        assertSameElements(List.of(expected), evaluation.cause().rootCauses());
    }

    public static void assertRootCauses(final Skipped skipped, final Evaluation... expected) {

        final List<Evaluation> rootCauses = tracesOf(skipped)
            .stream()
            .map(Cause.Trace::rootCause)
            .toList();

        assertSameElements(List.of(expected), rootCauses);
    }

    private static List<Cause.Trace> tracesOf(final Skipped skipped) {
        return skipped.directCause()
            .evaluations()
            .stream()
            .flatMap(decider -> decider.cause().traces().stream())
            .toList();
    }

    private static void assertTraceNames(final List<Cause.Trace> traces, final String... expected) {

        final List<String> names = traces.stream()
            .map(Cause.Trace::toString)
            .toList();

        assertEquals(List.of(expected), names);
    }

    private static void assertSameElements(final List<Evaluation> expected, final List<Evaluation> actual) {

        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            assertSame(expected.get(i), actual.get(i));
        }
    }
}
