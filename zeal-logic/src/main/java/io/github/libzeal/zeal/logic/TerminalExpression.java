package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;

/**
 * An expression that directly evaluates to a result (its result does not depend
 * on the results of any sub-expressions).
 *
 * @author Justin Albano
 * @since 0.5.0
 */
public non-sealed interface TerminalExpression extends Expression {

    @Override
    TerminalEvaluation evaluate();
}
