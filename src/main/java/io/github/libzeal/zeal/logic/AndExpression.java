package io.github.libzeal.zeal.logic;

import java.util.ArrayList;
import java.util.List;

public record AndExpression(String name, List<Expression> children) implements CompoundExpression {

    public AndExpression(final List<Expression> children) {
        this("AND", children);
    }

    public static AndExpression empty() {
        return new AndExpression(new ArrayList<>());
    }

    public static AndExpression empty(final String name) {
        return new AndExpression(name, new ArrayList<>());
    }

    @Override
    public void append(final Expression expression) {
        children.add(expression);
    }
}
