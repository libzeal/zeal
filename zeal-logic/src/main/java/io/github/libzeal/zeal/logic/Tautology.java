package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import java.time.Duration;

/**
 * An expression that is always true.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
public record Tautology() implements TerminalExpression {

    static final String NAME = "Tautology";
    private static final String TRUE = "true";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public TerminalEvaluation evaluate() {
        return new TerminalEvaluation(
                true,
                NAME,
                new SimpleRationale(TRUE, TRUE),
                Duration.ZERO
        );
    }
}
