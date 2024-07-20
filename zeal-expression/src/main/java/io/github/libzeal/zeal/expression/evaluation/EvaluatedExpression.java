package io.github.libzeal.zeal.expression.evaluation;

import java.util.List;

public interface EvaluatedExpression {

    EvaluationState state();
    String name();
    Reason reason();
    List<EvaluatedExpression> children();
}
