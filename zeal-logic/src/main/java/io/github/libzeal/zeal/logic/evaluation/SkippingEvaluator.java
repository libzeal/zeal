package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.CompoundExpression;
import io.github.libzeal.zeal.logic.Expression;

import java.util.List;

public class SkippingEvaluator {

    public Evaluation skip(final Expression expression, final Evaluation decider) {
        return switch (expression) {
            case final CompoundExpression compoundExpression -> {
                final List<Evaluation> children = compoundExpression.children()
                    .stream()
                    .map(child -> skip(child, decider))
                    .toList();

                yield CompoundEvaluation.skipped(expression.name(), decider, children);
            }
            default -> TerminalEvaluation.skipped(expression.name(), decider);
        };
    }
}
