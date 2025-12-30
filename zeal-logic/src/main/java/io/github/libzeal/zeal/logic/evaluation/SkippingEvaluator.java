package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.Contradiction;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.NegatedExpression;
import io.github.libzeal.zeal.logic.Tautology;
import io.github.libzeal.zeal.logic.CompoundExpression;
import io.github.libzeal.zeal.logic.evaluation.cause.Cause;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SkippingEvaluator {

    public Evaluation skip(final Expression expression, final Cause cause) {

        if (expression instanceof NegatedExpression) {

            final NegatedExpression negatedExpression = (NegatedExpression) expression;
            final Expression wrapped = negatedExpression.wrapped();
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
