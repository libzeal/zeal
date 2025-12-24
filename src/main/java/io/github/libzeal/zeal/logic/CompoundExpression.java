package io.github.libzeal.zeal.logic;

import java.util.List;

public sealed interface CompoundExpression extends Expression
    permits AndExpression, OrExpression {

    List<Expression> children();
}
