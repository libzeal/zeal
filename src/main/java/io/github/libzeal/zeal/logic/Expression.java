package io.github.libzeal.zeal.logic;

public sealed interface Expression
    permits NegationExpression, TerminalExpression, CompoundExpression {

    String DEFAULT_NAME = "(unnamed)";

    default String name() {
        return DEFAULT_NAME;
    }
}
