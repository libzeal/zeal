package io.github.libzeal.zeal.expression.evaluation;

import java.util.ArrayList;
import java.util.List;

public class TerminalEvaluatedExpression implements EvaluatedExpression {

    private final Result state;
    private final String name;
    private final Rationale rationale;

    public TerminalEvaluatedExpression(Result state, String name, Rationale rationale) {
        this.state = state;
        this.name = name;
        this.rationale = rationale;
    }

    @Override
    public Result result() {
        return state;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Rationale rationale() {
        return rationale;
    }

    @Override
    public List<EvaluatedExpression> children() {
        return new ArrayList<>(0);
    }
}
