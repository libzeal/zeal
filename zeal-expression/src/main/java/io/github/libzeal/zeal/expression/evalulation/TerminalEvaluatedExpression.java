package io.github.libzeal.zeal.expression.evalulation;

import java.util.ArrayList;
import java.util.List;

public class TerminalEvaluatedExpression implements EvaluatedExpression {

    private final EvaluationState state;
    private final String name;
    private final DeferredValue expected;
    private final DeferredValue actual;

    public TerminalEvaluatedExpression(EvaluationState state, String name, DeferredValue expected,
                                       DeferredValue actual) {
        this.state = state;
        this.name = name;
        this.expected = expected;
        this.actual = actual;
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
    public String expected() {
        return expected.value();
    }

    @Override
    public String actual() {
        return actual.value();
    }

    @Override
    public List<EvaluatedExpression> children() {
        return new ArrayList<>(0);
    }

    @Override
    public final boolean failsNotNullCheck() {
        return false;
    }
}
