package io.github.libzeal.zeal.logic;

import java.util.function.BooleanSupplier;

public record TerminalExpression(String name, BooleanSupplier expression) implements Expression {

    public TerminalExpression(final BooleanSupplier expression) {
        this(DEFAULT_NAME, expression);
    }
}
