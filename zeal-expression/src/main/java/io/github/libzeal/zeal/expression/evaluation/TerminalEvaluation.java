package io.github.libzeal.zeal.expression.evaluation;

import java.util.function.Predicate;

public class TerminalEvaluation<T> implements Evaluation<T> {

    private final String name;
    private final Predicate<T> predicate;
    private final ReasonGenerator<T> reasonGeneator;

    public static <A> TerminalEvaluation<A> of(String name, Predicate<A> predicate,
                                               ReasonGenerator<A> reasonGenerator) {
        return new TerminalEvaluation<>(
            name,
            s -> s != null && predicate.test(s),
            reasonGenerator
        );
    }

    public static <A> TerminalEvaluation<A> ofNullable(String name, Predicate<A> predicate, ReasonGenerator<A> reasonGenerator) {
        return new TerminalEvaluation<>(
            name,
            predicate,
            reasonGenerator
        );
    }

    public TerminalEvaluation(String name, Predicate<T> predicate, ReasonGenerator<T> reasonGenerator) {
        this.name = name;
        this.predicate = predicate;
        this.reasonGeneator = reasonGenerator;
    }

    @Override
    public EvaluatedExpression evaluate(T subject, boolean skip) {

        return new TerminalEvaluatedExpression(
            computeEvaluationState(subject, skip),
            name,
            computeReason(subject, skip)
        );
    }

    private EvaluationState computeEvaluationState(T subject, boolean skip) {

        if (skip) {
            return EvaluationState.SKIPPED;
        }

        if (predicate.test(subject)) {
            return EvaluationState.PASSED;
        } else {
            return EvaluationState.FAILED;
        }
    }

    private Reason computeReason(T subject, boolean skip) {

        if (skip) {
            return Reason.empty();
        } else {
            return reasonGeneator.generate(subject);
        }
    }
}
