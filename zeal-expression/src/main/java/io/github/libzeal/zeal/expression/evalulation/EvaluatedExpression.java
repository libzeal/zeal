package io.github.libzeal.zeal.expression.evalulation;

import java.util.List;

public interface EvaluatedExpression {

    EvaluationState state();
    String name();
    String expected();
    String actual();
    List<EvaluatedExpression> children();

    default boolean failsNotNullCheck() {
        return false;
    }

    default boolean isPassed() {

        EvaluationState state = state();

        return state != null && state.equals(EvaluationState.PASSED);
    }

    default boolean isFailed() {

        EvaluationState state = state();

        return state != null && state.equals(EvaluationState.FAILED);
    }

    default boolean isSkipped() {

        EvaluationState state = state();

        return state != null && state.equals(EvaluationState.FAILED);
    }
}
