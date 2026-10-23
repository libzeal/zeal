package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;

import java.time.Duration;

/**
 * ` An expression that is always true.
 *
 * @author Justin Albano
 */
final class Tautology implements Expression {

    static final String NAME = "Tautology";
    private static final String TRUE = "true";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public Evaluation evaluate() {
        return TerminalEvaluation.of(Result.TRUE, NAME, new SimpleRationale(TRUE, TRUE), Duration.ZERO);
    }
}
