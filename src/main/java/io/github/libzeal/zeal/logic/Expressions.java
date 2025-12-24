package io.github.libzeal.zeal.logic;

public final class Expressions {

    private Expressions() {}

    public static Expression tautology() {
        return new TerminalExpression(() -> true);
    }

    public static Expression contradiction() {
        return new TerminalExpression(() -> false);
    }
}
