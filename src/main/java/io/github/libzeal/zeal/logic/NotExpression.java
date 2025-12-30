package io.github.libzeal.zeal.logic;

public record NotExpression(String name, Expression expression) implements Expression {

    public NotExpression(final Expression expression) {
        this("NOT", expression);
    }
}
