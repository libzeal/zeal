package io.github.libzeal.zeal.expression;

import io.github.libzeal.zeal.expression.evalulation.EvaluatedExpression;

public interface Expression {

    EvaluatedExpression evaluate();
}
