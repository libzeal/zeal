package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;

import java.util.List;

/**
 * An expression composed of sub-expressions.
 *
 * @author Justin Albano
 */
public non-sealed interface CompoundExpression extends Expression {

    List<Expression> children();

    @Override
    CompoundEvaluation evaluate();
}
