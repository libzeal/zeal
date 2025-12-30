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

        if (expression instanceof NotExpression) {

            final NotExpression notExpression = (NotExpression) expression;
            final Expression wrapped = notExpression.wrapped();
            final Evaluation wrappedEvaluation = skip(wrapped, cause);

            return new SkippedCompoundEvaluation(expression.name(), e -> cause,
                Collections.singletonList(wrappedEvaluation));
        }
        else if (expression instanceof CompoundExpression) {
            final CompoundExpression compoundExpression = (CompoundExpression) expression;

            final List<Evaluation> evaluations = compoundExpression.children()
                .stream()
                .map(child -> skip(child, cause))
                .collect(Collectors.toList());

            return new SkippedCompoundEvaluation(expression.name(), e -> cause, evaluations);
        }
        else {
            return new SkippedTerminalEvaluation(expression.name(), e -> cause);
        }
    }
}
