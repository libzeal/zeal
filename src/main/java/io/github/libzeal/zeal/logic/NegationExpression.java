package io.github.libzeal.zeal.logic;

import java.util.function.BooleanSupplier;

public record NegationExpression(String name, Expression expression) implements Expression {

    public NegationExpression(final Expression expression) {
        this(DEFAULT_NAME, expression);
    }
}
