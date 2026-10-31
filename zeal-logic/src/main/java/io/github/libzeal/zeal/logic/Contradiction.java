package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import java.time.Duration;

/**
 * An expression that is always false.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
public record Contradiction() implements TerminalExpression {

    static final String NAME = "Contradiction";
    private static final String FALSE = "false";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public TerminalEvaluation evaluate() {
        return new TerminalEvaluation(
                false,
                NAME,
                new SimpleRationale(FALSE, FALSE),
                Duration.ZERO
        );
    }
}
