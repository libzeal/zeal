package io.github.libzeal.zeal.expression.criteria;

import io.github.libzeal.zeal.expression.evaluation.*;

import java.util.function.Predicate;

public class Criterion<T> implements Criteria<T> {

    private final String name;
    private final Predicate<T> predicate;
    private final RationaleGenerator<T> rationaleGenerator;

    public static <A> Criterion<A> of(String name, Predicate<A> predicate,
                                      RationaleGenerator<A> rationaleGenerator) {
        return new Criterion<>(
            name,
            s -> s != null && predicate.test(s),
            rationaleGenerator
        );
    }

    public static <A> Criterion<A> ofNullable(String name, Predicate<A> predicate, RationaleGenerator<A> rationaleGenerator) {
        return new Criterion<>(
            name,
            predicate,
            rationaleGenerator
        );
    }

    public Criterion(String name, Predicate<T> predicate, RationaleGenerator<T> rationaleGenerator) {
        this.name = name;
        this.predicate = predicate;
        this.rationaleGenerator = rationaleGenerator;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Evaluation evaluate(final T subject, final EvaluationContext context) {

        return new TerminalEvaluation(
            computeEvaluationState(subject, context),
            name,
            computeReason(subject, context)
        );
    }

    private Result computeEvaluationState(final T subject, final EvaluationContext context) {

        if (context.skip()) {
            return Result.SKIPPED;
        }

        if (predicate.test(subject)) {
            return Result.PASSED;
        } else {
            return Result.FAILED;
        }
    }

    private Rationale computeReason(final T subject, final EvaluationContext context) {

        if (context.skip()) {
            return Rationale.empty();
        } else {
            return rationaleGenerator.generate(subject);
        }
    }
}
