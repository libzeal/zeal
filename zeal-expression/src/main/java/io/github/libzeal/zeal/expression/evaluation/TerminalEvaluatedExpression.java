package io.github.libzeal.zeal.expression.evaluation;

import java.util.ArrayList;
import java.util.List;

public class TerminalEvaluatedExpression implements EvaluatedExpression {

    private final EvaluationState state;
    private final String name;
    private final Reason reason;

    public TerminalEvaluatedExpression(EvaluationState state, String name, Reason reason) {
        this.state = state;
        this.name = name;
        this.reason = reason;
    }

    @Override
    public EvaluationState state() {
        return state;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Reason reason() {
        return reason;
    }

    @Override
    public List<EvaluatedExpression> children() {
        return new ArrayList<>(0);
    }
}
