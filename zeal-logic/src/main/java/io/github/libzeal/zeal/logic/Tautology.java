package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.EvaluatedTerminalEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import java.time.Duration;

/**
 * An expression that is always true.
 *
 * @author Justin Albano
 */
class Tautology implements Expression {

    static final String NAME = "Tautology";
    private static final String TRUE = "true";
    private static final Evaluation EVALUATION = evaluation();

    private static Evaluation evaluation() {
        return EvaluatedTerminalEvaluation.ofTrue(
            NAME,
            new SimpleRationale(TRUE, TRUE),
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
