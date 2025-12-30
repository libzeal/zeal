package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;

import java.util.ArrayList;
import java.util.List;

public record OrExpression(String name, List<Expression> children) implements CompoundExpression {

    public OrExpression(final List<Expression> children) {
        this("OR", children);
    }

    public static OrExpression empty() {
        return new OrExpression(new ArrayList<>());
    }

    public static OrExpression empty(final String name) {
        return new OrExpression(name, new ArrayList<>());
    }

    @Override
    public void append(final Expression expression) {
        children.add(expression);
    }

    @Override
    public Evaluation evaluate() {
        return null;
    }
}
