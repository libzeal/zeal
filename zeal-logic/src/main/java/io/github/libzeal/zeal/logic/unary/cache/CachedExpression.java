package io.github.libzeal.zeal.logic.unary.cache;

import io.github.libzeal.zeal.logic.evaluation.EvaluatedTerminalEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

public record CachedExpression<T, C>(String name, T subject,
                                     CacheablePredicate<T, C> predicate,
                                     CachedComputableRationale<T, C> computableRationale) implements UnaryExpression<T> {

    public CachedExpression {
        requireNonNull(name);
        requireNonNull(predicate);
        requireNonNull(computableRationale);
    }

    @Override
    public Evaluation evaluate() {

        final StopWatch stopWatch = StopWatch.started();
        final CacheableResult<C> predicateResult = predicate.test(subject);
        final boolean passed = predicateResult.passed();
        final C cache = predicateResult.cache();
        final Duration elapsedTime = stopWatch.stop();
        final CachedComputableRationaleContext<T, C> context = new CachedComputableRationaleContext<>(subject, passed, cache);

        final Rationale rationale = computableRationale.compute(context);
        final Result result = Result.from(passed);

        return EvaluatedTerminalEvaluation.of(result, name, rationale, elapsedTime);
    }
}
