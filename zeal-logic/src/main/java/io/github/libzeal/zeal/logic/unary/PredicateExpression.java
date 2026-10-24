package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public record PredicateExpression<T, O>(
        String name,
        T subject,
        boolean nullable,
        Function<T, O> observe,
        Predicate<O> condition,
        Field<T, O> expected,
        Field<T, O> actual,
        Field<T, O> hint) implements TerminalUnaryExpression<T> {

    public PredicateExpression {
        requireNonNull(name);
        requireNonNull(observe);
        requireNonNull(condition);
        requireNonNull(expected);
        requireNonNull(actual);
    }

    @Override
    public TerminalEvaluation evaluate() {

        final StopWatch stopWatch = StopWatch.started();
        final boolean observable = subject != null || nullable;
        final O observed = observable ? observe.apply(subject) : null;
        final boolean passed = observable && condition.test(observed);
        final Duration elapsedTime = stopWatch.stop();

        final Context<T, O> context = new Context<>(subject, observed, passed);
        final Rationale rationale = new SimpleRationale(
            expected.compute(context),
            actual.compute(context),
            hint == null ? null : hint.compute(context)
        );

        return TerminalEvaluation.of(Result.from(passed), name, rationale, elapsedTime);
    }

    public record Context<T, O>(T subject, O observed, boolean passed) {

        public <A> A ifPassedOrElse(final A ifPassed, final A orElse) {
            return passed ? ifPassed : orElse;
        }
    }

    @FunctionalInterface
    public interface Field<T, O> {
        String compute(Context<T, O> context);
    }
}
