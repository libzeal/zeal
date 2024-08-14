package io.github.libzeal.zeal.expression.lang;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExpressionAssertions {

    private ExpressionAssertions() {}

    public static void assertHasChildWithName(final Evaluation evaluation, final String name) {
        assertTrue(evaluation.children().stream().anyMatch(e -> e.name().equals(name)));
    }
}
