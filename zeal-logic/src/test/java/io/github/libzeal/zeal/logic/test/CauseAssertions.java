package io.github.libzeal.zeal.logic.test;

import io.github.libzeal.zeal.logic.evaluation.Cause;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CauseAssertions {

    private CauseAssertions() {}

    public static Evaluation child(final Evaluation evaluation, final int... path) {

        Evaluation current = evaluation;

        for (final int index : path) {
            current = ((CompoundEvaluation) current).children().get(index);
        }

        return current;
    }

    public static void assertCauseIsSelf(final Evaluation evaluation) {
        assertInstanceOf(Cause.Self.class, evaluation.cause());
    }

    public static void assertCause(final Evaluation evaluation, final Evaluation... expected) {

        final Cause.Delegated cause = assertInstanceOf(Cause.Delegated.class, evaluation.cause());

        assertSameElements(List.of(expected), cause.evaluations());
    }

    public static void assertTraces(final Evaluation evaluation, final String... expected) {

        final List<String> traces = evaluation.causeTraces()
            .stream()
            .map(Cause.Trace::toString)
            .toList();

        assertEquals(List.of(expected), traces);
    }

    public static void assertRootCauses(final Evaluation evaluation, final Evaluation... expected) {
        assertSameElements(List.of(expected), evaluation.rootCauses());
    }

    private static void assertSameElements(final List<Evaluation> expected, final List<Evaluation> actual) {

        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            assertSame(expected.get(i), actual.get(i));
        }
    }
}
