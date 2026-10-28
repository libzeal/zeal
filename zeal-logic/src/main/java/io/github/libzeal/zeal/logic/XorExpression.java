package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;

import java.util.List;

import static java.util.Objects.requireNonNull;

public record XorExpression(String name,
                            List<Expression> children) implements CompoundExpression {

    static final String DEFAULT_NAME = "XOR";

    public XorExpression {
        requireNonNull(name);
        children = List.copyOf(children);
    }

    public static XorExpression unnamed(final List<Expression> children) {
        return new XorExpression(DEFAULT_NAME, children);
    }

    public static XorExpression unnamed(final Expression... children) {
        return new XorExpression(DEFAULT_NAME, List.of(children));
    }

    @Override
    public CompoundEvaluation evaluate() {
        return CompoundEvaluator.evaluateXor(this);
    }
}
