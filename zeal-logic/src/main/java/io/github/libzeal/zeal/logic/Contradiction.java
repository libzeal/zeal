package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
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

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public Evaluation evaluate() {
        return TerminalEvaluation.of(Result.FALSE, NAME, new SimpleRationale(FALSE, FALSE), Duration.ZERO);
    }
}
