package io.github.libzeal.zeal.logic;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;

public interface Expression {

    String DEFAULT_NAME = "(unnamed)";

    default String name() {
        return DEFAULT_NAME;
    }

    Evaluation evaluate();
}
