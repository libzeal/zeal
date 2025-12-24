package io.github.libzeal.zeal.logic;

import java.util.List;

public record OrExpression(String name, List<Expression> children) implements CompoundExpression {

    public OrExpression(final List<Expression> children) {
        this(DEFAULT_NAME, children);
    }
}
