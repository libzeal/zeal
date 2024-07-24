package io.github.libzeal.zeal.expression.evaluation;

public interface Evaluation<T> {

    String name();
    EvaluatedExpression evaluate(T subject, boolean skip);
}
