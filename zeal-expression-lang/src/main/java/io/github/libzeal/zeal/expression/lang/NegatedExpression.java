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
        final RootCause rootCause = wrappedEvaluation.rootCause();

        final List<Evaluation> children = new ArrayList<>(1);
        children.add(wrappedEvaluation);

        if (result.isFalse()) {
            return CompoundEvaluation.ofFalse(name(), rationale, rootCause, children);
        }
        else {
            return CompoundEvaluation.ofTrue(name(), rationale, rootCause, children);
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
    public Evaluation skip(final RootCause rootCause) {

        final List<Evaluation> children = skipWrapped(rootCause);

        return CompoundEvaluation.ofSkipped(name(), rootCause, children);
    }

    private List<Evaluation> skipWrapped(final RootCause rootCause) {

        final List<Evaluation> children = new ArrayList<>(1);

        children.add(wrapped.skip(rootCause));

        return children;
    }
}