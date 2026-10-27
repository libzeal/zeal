package io.github.libzeal.zeal.logic.test;

import io.github.libzeal.zeal.logic.CompoundExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
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
                addSkipped(new Skipped(child, skipped.cause()), found);
            }
        }
    }

    public static void assertCauseIsSelf(final Evaluation evaluation) {
        assertInstanceOf(Cause.Self.class, evaluation.cause());
    }

    public static void assertCause(final Evaluation evaluation, final Evaluation... expected) {
        assertCause(new Evaluated(evaluation), expected);
    }

    public static void assertCause(final SkippableEvaluation evaluation, final Evaluation... expected) {

        final Cause.Delegated cause = assertInstanceOf(Cause.Delegated.class, evaluation.cause());

        assertSameElements(List.of(expected), cause.evaluations());
    }

    public static void assertTraces(final Evaluation evaluation, final String... expected) {
        assertTraces(new Evaluated(evaluation), expected);
    }

    public static void assertTraces(final SkippableEvaluation evaluation, final String... expected) {

        final List<String> traces = evaluation.causeTraces()
            .stream()
            .map(Cause.Trace::toString)
            .toList();

        assertEquals(List.of(expected), traces);
    }

    public static void assertRootCauses(final Evaluation evaluation, final Evaluation... expected) {
        assertRootCauses(new Evaluated(evaluation), expected);
    }

    public static void assertRootCauses(final SkippableEvaluation evaluation, final Evaluation... expected) {
        assertSameElements(List.of(expected), evaluation.rootCauses());
    }

    private static void assertSameElements(final List<Evaluation> expected, final List<Evaluation> actual) {

        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            assertSame(expected.get(i), actual.get(i));
        }
    }
}
