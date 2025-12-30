package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.*;

import java.util.ArrayList;
import java.util.List;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;

public record ShortCircuitEvaluator(int maxDepth) implements Evaluator {

    static final int DEFAULT_MAX_DEPTH = 100;

    public ShortCircuitEvaluator() {
        this(DEFAULT_MAX_DEPTH);
    }

    @Override
    public Evaluation evaluate(final Expression expression) {
        return evaluate(expression, EvaluationContext.create());
    }

    private Evaluation evaluate(final Expression expression, final EvaluationContext context) {

        if (context.depth() > maxDepth) {
            throw new IllegalStateException("Exceeded maximum depth of " + maxDepth + " during evaluation");
        }
        if (expression == null) {
            return new EmptyEvaluation();
        }

        return switch (expression) {
            case TerminalExpression e -> evaluate(e, context);
            case NotExpression e -> evaluate(e, context);
            case AndExpression e -> evaluate(e, context);
            case OrExpression e -> evaluate(e, context);
        };
    }

    private TerminalEvaluation evaluate(final TerminalExpression expression,
                                        final EvaluationContext context) {

        if (context.isComplete()) {
            return new TerminalEvaluation(expression.name(), Result.SKIPPED);
        }
        else {
            final Result result = Result.from(expression.expression().getAsBoolean());

            return new TerminalEvaluation(expression.name(), result);
        }
    }

    private NegationEvaluation evaluate(final NotExpression expression,
                                        final EvaluationContext context) {

        final Evaluation child = evaluate(expression.expression(), context);

        if (context.isComplete()) {

            return new NegationEvaluation(expression.name(), Result.SKIPPED, child);
        }
        else {
            return new NegationEvaluation(expression.name(), child.result().negate(), child);
        }
    }

    private CompoundEvaluation evaluate(final AndExpression expression,
                                        final EvaluationContext context) {

        if (context.isComplete()) {

            final List<Evaluation> children = expression.children()
                .stream()
                .map(e -> evaluate(e, context))
                .toList();

            return new CompoundEvaluation(expression.name(), Result.SKIPPED, children);
        }
        else {

            EvaluationContext iterationContext = context.incrementDepth();
            Result iterationResult = TRUE;
            final List<Expression> children = expression.children();
            final List<Evaluation> childEvaluations = new ArrayList<>(children.size());

            for (final Expression child : children) {

                final Evaluation evaluation = evaluate(child, iterationContext);
                final Result result = evaluation.result();

                childEvaluations.add(evaluation);

                if (result.equals(FALSE)) {
                    iterationResult = FALSE;
                    iterationContext = iterationContext.complete();
                }
            }

            return new CompoundEvaluation(expression.name(), iterationResult, childEvaluations);
        }
    }

    private CompoundEvaluation evaluate(final OrExpression expression,
                                        final EvaluationContext context) {

        if (context.isComplete()) {

            final List<Evaluation> children = expression.children()
                .stream()
                .map(e -> evaluate(e, context))
                .toList();

            return new CompoundEvaluation(expression.name(), Result.SKIPPED, children);
        }
        else {

            EvaluationContext iterationContext = context.incrementDepth();
            Result iterationResult = FALSE;
            final List<Expression> children = expression.children();
            final List<Evaluation> childEvaluations = new ArrayList<>(children.size());

            for (final Expression child : children) {

                final Evaluation evaluation = evaluate(child, iterationContext);
                final Result result = evaluation.result();

                childEvaluations.add(evaluation);

                if (result.equals(TRUE)) {
                    iterationResult = TRUE;
                    iterationContext = iterationContext.complete();
                }
            }

            return new CompoundEvaluation(expression.name(), iterationResult, childEvaluations);
        }
    }

    private record EvaluationContext(boolean isComplete, int depth) {

        public static EvaluationContext create() {
            return new EvaluationContext(false, 0);
        }

        public EvaluationContext incrementDepth() {
            return new EvaluationContext(isComplete, depth + 1);
        }

        public EvaluationContext complete() {
            return new EvaluationContext(true, depth);
        }
    }
}
