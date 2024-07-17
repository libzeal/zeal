package io.github.libzeal.zeal.expression.evalulation;

public interface Evaluation<T> {

    EvaluatedExpression evaluate(T subject, boolean skip);
}
