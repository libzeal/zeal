package io.github.libzeal.zeal.logic;

import java.util.List;

public final class Expressions {

    private Expressions() {}

    public static final TerminalExpression TRUE = tautology("true");
    public static final TerminalExpression FALSE = contradiction("false");

    public static TerminalExpression tautology() {
        return new TerminalExpression(() -> true);
    }

    public static TerminalExpression tautology(final String name) {
        return new TerminalExpression(name, () -> true);
    }

    public static TerminalExpression contradiction() {
        return new TerminalExpression(() -> false);
    }

    public static TerminalExpression contradiction(final String name) {
        return new TerminalExpression(name, () -> false);
    }

    public static NotExpression not(final Expression expression) {
        return new NotExpression(expression);
    }

    public static NotExpression not(final String name, final Expression expression) {
        return new NotExpression(name, expression);
    }

    public static AndExpression and(final Expression... expressions) {
        return new AndExpression(List.of(expressions));
    }

    public static AndExpression and(final String name, final Expression... expressions) {
        return new AndExpression(name, List.of(expressions));
    }

    public static OrExpression or(final Expression... expressions) {
        return new OrExpression(List.of(expressions));
    }

    public static OrExpression or(final String name, final Expression... expressions) {
        return new OrExpression(name, List.of(expressions));
    }

    public static NotExpression nand(final Expression... expressions) {
        return not(and(expressions));
    }

    public static NotExpression nand(final String name, final Expression... expressions) {
        return not(name, and(expressions));
    }

    public static NotExpression nor(final Expression... expressions) {
        return not(or(expressions));
    }

    public static NotExpression nor(final String name, final Expression... expressions) {
        return not(name, or(expressions));
    }
}
