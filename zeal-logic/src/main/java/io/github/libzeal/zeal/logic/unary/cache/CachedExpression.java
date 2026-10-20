package io.github.libzeal.zeal.logic.unary.cache;

import io.github.libzeal.zeal.logic.evaluation.EvaluatedTerminalEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;
import io.github.libzeal.zeal.logic.unary.cache.CachedField.Context;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

public record CachedExpression<T, C>(
        String name,
        T subject,
        CacheablePredicate<T, C> predicate,
        CachedField<T, C> expected,
        CachedField<T, C> actual,
        CachedField<T, C> hint) implements UnaryExpression<T> {

    public CachedExpression {
        requireNonNull(name);
        requireNonNull(predicate);
        requireNonNull(expected);
        requireNonNull(actual);
    }

    @Override
    public Evaluation evaluate() {

        final StopWatch stopWatch = StopWatch.started();
        final CachedResult<C> predicateResult = predicate.test(subject);
        final boolean passed = predicateResult.passed();
        final C cached = predicateResult.cache();
        final Duration elapsedTime = stopWatch.stop();
        final Rationale rationale = rationale(passed, cached);

        final Result result = Result.from(passed);

        return EvaluatedTerminalEvaluation.of(result, name, rationale, elapsedTime);
    }

    private SimpleRationale rationale(final boolean passed, final C cached) {

        final var context = new Context<>(subject, passed, cached);
        final String expected = this.expected.compute(context);
        final String actual = this.actual.compute(context);
        final String hint = this.hint == null ? null : this.hint.compute(context);

        return new SimpleRationale(expected, actual, hint);
    }
}
