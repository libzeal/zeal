package io.github.libzeal.zeal.logic;

import java.util.List;

/**
 * An expression composed of sub-expressions.
 *
 * @author Justin Albano
 */
public sealed interface CompoundExpression extends Expression
        permits AndExpression, OrExpression {

    List<Expression> children();
}
