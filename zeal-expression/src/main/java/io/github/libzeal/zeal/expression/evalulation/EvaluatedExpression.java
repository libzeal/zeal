package io.github.libzeal.zeal.expression.evalulation;

import java.util.List;

public interface EvaluatedExpression {

    EvaluationState state();
    String name();
    Reason reason();
    List<EvaluatedExpression> children();
}
