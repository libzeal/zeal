package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.NotExpression;
import io.github.libzeal.zeal.logic.CompoundExpression;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SkippingEvaluator {

    public Evaluation skip(final Expression expression, final Cause cause) {
        return switch (expression) {
            case NotExpression(String name, Expression wrapped) -> {
                final Evaluation wrappedEvaluation = skip(wrapped, cause);

                yield new SkippedCompoundEvaluation(name, e -> cause,
                    Collections.singletonList(wrappedEvaluation));
            }
            case final CompoundExpression compoundExpression -> {
                final List<Evaluation> evaluations = compoundExpression.children()
                    .stream()
                    .map(child -> skip(child, cause))
                    .collect(Collectors.toList());

                yield new SkippedCompoundEvaluation(expression.name(), e -> cause, evaluations);
            }
            default -> new SkippedTerminalEvaluation(expression.name(), e -> cause);
        };
    }
}
