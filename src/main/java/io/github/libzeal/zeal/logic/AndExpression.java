package io.github.libzeal.zeal.logic;

import java.util.List;

public record AndExpression(String name, List<Expression> children) implements CompoundExpression {

    public AndExpression(final List<Expression> children) {
        this(DEFAULT_NAME, children);
    }
}
