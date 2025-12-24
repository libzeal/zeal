package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.Expression;

public interface Evaluator {

    Evaluation evaluate(Expression expression);
}
