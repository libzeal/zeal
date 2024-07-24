package io.github.libzeal.zeal.expression.evaluation;

import java.util.function.Predicate;

public class TerminalEvaluation<T> implements Evaluation<T> {

    private final String name;
    private final Predicate<T> predicate;
    private final RationaleGenerator<T> rationaleGenerator;

    public static <A> TerminalEvaluation<A> of(String name, Predicate<A> predicate,
                                               RationaleGenerator<A> rationaleGenerator) {
        return new TerminalEvaluation<>(
            name,
            s -> s != null && predicate.test(s),
            rationaleGenerator
        );
    }

    public static <A> TerminalEvaluation<A> ofNullable(String name, Predicate<A> predicate, RationaleGenerator<A> rationaleGenerator) {
        return new TerminalEvaluation<>(
            name,
            predicate,
            rationaleGenerator
        );
    }

    public TerminalEvaluation(String name, Predicate<T> predicate, RationaleGenerator<T> rationaleGenerator) {
        this.name = name;
        this.predicate = predicate;
        this.rationaleGenerator = rationaleGenerator;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public EvaluatedExpression evaluate(T subject, boolean skip) {

        return new TerminalEvaluatedExpression(
            computeEvaluationState(subject, skip),
            name,
            computeReason(subject, skip)
        );
    }

    private Result computeEvaluationState(T subject, boolean skip) {

        if (skip) {
            return Result.SKIPPED;
        }

        if (predicate.test(subject)) {
            return Result.PASSED;
        } else {
            return Result.FAILED;
        }
    }

    private Rationale computeReason(T subject, boolean skip) {

        if (skip) {
            return Rationale.empty();
        } else {
            return rationaleGenerator.generate(subject);
        }
    }
}
