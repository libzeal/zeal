package io.github.libzeal.zeal.logic.evaluation;

public sealed interface Evaluation
    permits TerminalEvaluation, CompoundEvaluation, NegationEvaluation, EmptyEvaluation {

    String name();
    Result result();
}
