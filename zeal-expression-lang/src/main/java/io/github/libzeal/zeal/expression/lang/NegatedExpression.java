package io.github.libzeal.zeal.expression.lang;

import io.github.libzeal.zeal.expression.lang.evaluation.*;
import io.github.libzeal.zeal.expression.lang.rationale.Rationale;
import io.github.libzeal.zeal.expression.lang.rationale.SimpleRationale;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class NegatedExpression implements Expression {

    private final Expression wrapped;

    public NegatedExpression(final Expression wrapped) {
        this.wrapped = requireNonNull(wrapped);
    }

    @Override
    public Evaluation evaluate() {

        final Evaluation wrappedEvaluation = wrapped.evaluate();
        final Result result = result(wrappedEvaluation);
        final String actualValue = actualValue(wrappedEvaluation);
        final Rationale rationale = new SimpleRationale("Wrapped: false", actualValue);

        final List<Evaluation> children = new ArrayList<>(1);
        children.add(wrappedEvaluation);

        if (result.isFalse()) {
            return new CompoundFalseEvaluation(name(), rationale, children);
        }
        else {
            return new CompoundTrueEvaluation(name(), rationale, children);
        }
    }

    private static Result result(final Evaluation wrappedEvaluation) {

        final Result result = wrappedEvaluation.result();

        if (result.isTrue()) {
            return Result.FALSE;
        }
        else if (result.isFalse()) {
            return Result.TRUE;
        }
        else {
            return Result.SKIPPED;
        }
    }

    private static String actualValue(final Evaluation wrappedEvaluation) {

        final Result result = wrappedEvaluation.result();

        if (result.isTrue()) {
            return "Wrapped: true";
        }
        else if (result.isFalse()) {
            return "Wrapped: false";
        }
        else {
            return "Wrapped: skipped";
        }
    }

    @Override
    public String name() {
        return "Not (NOT)";
    }

    @Override
    public SkippedEvaluation skip() {

        final List<Evaluation> children = skipWrapped();

        return new CompoundSkippedEvaluation(name(), children);
    }

    private List<Evaluation> skipWrapped() {

        final List<Evaluation> children = new ArrayList<>(1);

        children.add(wrapped.skip());

        return children;
    }
}
