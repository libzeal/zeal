package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;

import java.util.List;

import static java.util.Objects.requireNonNull;

public record NorExpression(String name,
                            List<Expression> children) implements CompoundExpression {

    static final String DEFAULT_NAME = "NOR";

    public NorExpression {
        requireNonNull(name);
        children = List.copyOf(children);
    }

    public static NorExpression unnamed(final List<Expression> children) {
        return new NorExpression(DEFAULT_NAME, children);
    }

    public static NorExpression unnamed(final Expression... children) {
        return new NorExpression(DEFAULT_NAME, List.of(children));
    }

    @Override
    public CompoundEvaluation evaluate() {
        return CompoundEvaluator.NOR.evaluate(this);
    }
}
