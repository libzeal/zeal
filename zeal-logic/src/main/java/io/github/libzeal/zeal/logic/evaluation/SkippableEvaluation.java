package io.github.libzeal.zeal.logic.evaluation;

import io.github.libzeal.zeal.logic.Expression;

import java.util.List;

import static java.util.Objects.requireNonNull;

public sealed interface SkippableEvaluation {

    String name();

    Cause cause();

    List<Cause.Trace> causeTraces();

    List<Evaluation> rootCauses();

    record Evaluated(Evaluation evaluation) implements SkippableEvaluation {

        public Evaluated {
            requireNonNull(evaluation);
        }

        @Override
        public String name() {
            return evaluation.name();
        }

        @Override
        public Cause cause() {
            return evaluation.cause();
        }

        @Override
        public List<Cause.Trace> causeTraces() {
            return evaluation.causeTraces();
        }

        @Override
        public List<Evaluation> rootCauses() {
            return evaluation.rootCauses();
        }
    }

    record Skipped(Expression expression, Cause.Delegated cause) implements SkippableEvaluation {

        public Skipped {
            requireNonNull(expression);
            requireNonNull(cause);
        }

        @Override
        public String name() {
            return expression.name();
        }

        @Override
        public List<Cause.Trace> causeTraces() {
            return cause.traces();
        }

        @Override
        public List<Evaluation> rootCauses() {
            return cause.rootCauses();
        }
    }
}
