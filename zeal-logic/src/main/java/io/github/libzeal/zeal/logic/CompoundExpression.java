package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;

import java.util.List;

/**
 * An expression composed of sub-expressions.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
public non-sealed interface CompoundExpression extends Expression {

    /**
     * Obtains the children of the compound expression.
     *
     * @return The children of the compound expression.
     */
    List<Expression> children();

    @Override
    CompoundEvaluation evaluate();
}
