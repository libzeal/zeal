package io.github.libzeal.zeal.expression.lang;

import io.github.libzeal.zeal.expression.lang.evaluation.Evaluation;
import io.github.libzeal.zeal.expression.lang.evaluation.Result;
import io.github.libzeal.zeal.expression.lang.evaluation.SimpleEvaluation;
import io.github.libzeal.zeal.expression.lang.rationale.Rationale;
import io.github.libzeal.zeal.expression.lang.rationale.SimpleRationale;

import static io.github.libzeal.zeal.expression.lang.evaluation.Result.*;
import static java.util.Objects.requireNonNull;

public class NegatedExpression implements Expression {

    private final Expression wrapped;

    public NegatedExpression(final Expression wrapped) {
        this.wrapped = requireNonNull(wrapped);
    }

    @Override
    public String name() {
        return not(wrapped.name());
    }

    private static String not(final String original) {
        return "not[" + original + "]";
    }

    @Override
    public Evaluation skip() {
        return wrapped.skip().withName(name());
    }

    @Override
    public Evaluation evaluate() {

        final Evaluation evaluation = wrapped.evaluate();
        final Rationale rationale = evaluation.rationale();

        return new SimpleEvaluation(
            not(evaluation.name()),
            negate(evaluation.result()),
            negate(rationale));
    }

    private static Result negate(final Result result) {

        switch (result) {
            case PASSED:
                return FAILED;
            case FAILED:
                return PASSED;
            default:
                return SKIPPED;
        }
    }

    private static Rationale negate(final Rationale rationale) {

        final String negatedHint = rationale.hint()
            .map(NegatedExpression::not)
            .orElse(null);

        return new SimpleRationale(
            not(rationale.expected()),
            rationale.actual(),
            negatedHint
        );
    }
}
