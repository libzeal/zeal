package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.EvaluatedTerminalEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import java.time.Duration;

/**
 * An expression that is always false.
 *
 * @author Justin Albano
 */
final class Contradiction implements Expression {

    static final String NAME = "Contradiction";
    private static final String FALSE = "false";
    private static final Evaluation EVALUATION = evaluation();

    private static Evaluation evaluation() {
        return EvaluatedTerminalEvaluation.ofFalse(
            NAME,
            new SimpleRationale(FALSE, FALSE),
            Duration.ZERO
        );
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public Evaluation evaluate() {
        return EVALUATION;
    }
}
