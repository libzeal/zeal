package io.github.libzeal.zeal.expression.criteria;

import io.github.libzeal.zeal.expression.evaluation.Evaluation;

public interface Criteria<T> {

    String name();
    Evaluation evaluate(T subject, EvaluationContext context);
}
