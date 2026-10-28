package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;

import java.util.List;

import static java.util.Objects.requireNonNull;

public record NandExpression(String name,
                             List<Expression> children) implements CompoundExpression {

    static final String DEFAULT_NAME = "NAND";

    public NandExpression {
        requireNonNull(name);
        children = List.copyOf(children);
    }

    public static NandExpression unnamed(final List<Expression> children) {
        return new NandExpression(DEFAULT_NAME, children);
    }

    public static NandExpression unnamed(final Expression... children) {
        return new NandExpression(DEFAULT_NAME, List.of(children));
    }

    @Override
    public CompoundEvaluation evaluate() {
        return CompoundEvaluator.NAND.evaluate(this);
    }
}
