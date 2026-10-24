package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;

public non-sealed interface TerminalExpression extends Expression {

    @Override
    TerminalEvaluation evaluate();
}
