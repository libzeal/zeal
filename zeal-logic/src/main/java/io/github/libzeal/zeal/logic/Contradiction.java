package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.EvaluatedTerminalEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.evaluation.cause.CauseGenerator;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import java.time.Duration;

/**
 * An expression that is always false.
 *
 * @author Justin Albano
 */
class Contradiction implements Expression {

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
