package io.github.libzeal.zeal.expression.predicate;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PredicateAssertions {

    private PredicateAssertions() {}

    public static void assertHasChildWithName(final Evaluation evaluation, final String name) {
        assertTrue(evaluation.children().stream().anyMatch(e -> e.name().equals(name)));
    }
}
