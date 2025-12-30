package io.github.libzeal.zeal.logic;

import java.util.List;

public interface CompoundExpression extends Expression {

    List<Expression> children();
    void append(Expression expression);
}
