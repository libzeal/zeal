package io.github.libzeal.zeal.expression.evaluation;

public interface Evaluation<T> {

    EvaluatedExpression evaluate(T subject, boolean skip);
}
