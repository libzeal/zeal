package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.evaluation.EvaluatedTerminalEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * An expression that observes a value from its subject and passes if that observed value satisfies a condition. The
 * rationale is computed after the test, from the subject, the observed value, and whether the test passed.
 * <p>
 * Unless the expression is nullable, a {@code null} subject fails without being observed (the observed value is
 * {@code null}).
 *
 * @param <T>
 *     The type of the subject.
 * @param <O>
 *     The type of the observed value.
 */
public record PredicateExpression<T, O>(
        String name,
        T subject,
        boolean nullable,
        Function<T, O> observe,
        Predicate<O> condition,
        Field<T, O> expected,
        Field<T, O> actual,
        Field<T, O> hint) implements UnaryExpression<T> {

    public PredicateExpression {
        requireNonNull(name);
        requireNonNull(observe);
        requireNonNull(condition);
        requireNonNull(expected);
        requireNonNull(actual);
    }

    @Override
    public Evaluation evaluate() {

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

        return EvaluatedTerminalEvaluation.of(Result.from(passed), name, rationale, elapsedTime);
    }

    /**
     * Everything a rationale field can draw on once the test has run.
     *
     * @param subject
     *     The subject of the expression.
     * @param observed
     *     The value observed from the subject and tested by the condition.
     * @param passed
     *     Whether the observed value satisfied the condition.
     */
    public record Context<T, O>(T subject, O observed, boolean passed) {

        public <A> A ifPassedOrElse(final A ifPassed, final A orElse) {
            return passed ? ifPassed : orElse;
        }
    }

    /**
     * A part of the rationale (expected, actual, or hint) computed from the context of an evaluation.
     */
    @FunctionalInterface
    public interface Field<T, O> {
        String compute(Context<T, O> context);
    }
}
