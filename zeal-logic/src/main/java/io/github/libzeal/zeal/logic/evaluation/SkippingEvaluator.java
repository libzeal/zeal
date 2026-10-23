package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.CompoundExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.NotExpression;

import java.util.List;

public class SkippingEvaluator {

    public Evaluation skip(final Expression expression, final Evaluation decider) {
        return switch (expression) {
            case NotExpression(String name, Expression wrapped) ->
                CompoundEvaluation.skipped(name, decider, List.of(skip(wrapped, decider)));
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
