package io.github.libzeal.zeal.logic.unary.future;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.EvaluatedTerminalEvaluation;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.unary.future.rationale.ComputableRationale;
import io.github.libzeal.zeal.logic.unary.future.rationale.ComputableRationaleContext;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * An expression that can be computed in the future.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.1
 */
public class ComputedExpression<T> implements Expression {

    private final String name;
    private final T subject;
    private final Predicate<T> predicate;
    private final ComputableRationale<T> computableRationale;

    /**
     * Creates a new unary expression.
     *
     * @param name
     *     The name of the expression.
     * @param subject
     *     The subject of the expression.
     * @param predicate
     *     The predicate for the expression. This predicate fails if the supplied subject is {@code null} (a
     *     non-nullable predicate).
     * @param computableRationale
     *     A generator used to create a rationale for the expression when it is evaluated.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments, other than the subject, are {@code null}.
     */
    public ComputedExpression(final String name, final T subject, final Predicate<T> predicate,
                              final ComputableRationale<T> computableRationale) {
        this.name = requireNonNull(name);
        this.subject = subject;
        this.predicate = requireNonNull(predicate, "Predicate cannot be null");
        this.computableRationale = requireNonNull(computableRationale);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Evaluation evaluate() {

        final StopWatch stopWatch = StopWatch.started();
        final boolean passed = predicate.test(subject);
        final Duration elapsedTime = stopWatch.stop();

        final ComputableRationaleContext<T> context = new ComputableRationaleContext<>(subject, passed);
        final Rationale rationale = computableRationale.compute(context);
        final Result result = Result.from(passed);

        return EvaluatedTerminalEvaluation.of(result, name, rationale, elapsedTime);
    }
}
