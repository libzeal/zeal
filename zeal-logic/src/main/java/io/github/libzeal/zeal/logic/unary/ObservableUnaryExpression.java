package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.rationale.Rationale;
import io.github.libzeal.zeal.logic.rationale.SimpleRationale;
import io.github.libzeal.zeal.logic.util.StopWatch;

import java.time.Duration;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * An expression that caches an observed value to use during evaluation. The
 * observed value is the value computed during the evaluation of the expression.
 * Some evaluations can be expensive (such as finding which values are present
 * in a collection) and this caching allows for other fields, such as the actual
 * value or hint, to reuse the computed value.
 * <p>
 * For example, when computing if two values are present in a collection, the
 * observed value may be the desired elements that were found in the collection.
 * This allows other fields, such as expected, to reuse this computed value to
 * determine which values were not found (by subtracting the found values from
 * the desired values).
 * <p>
 * The condition is then used to inspect the observed value and determine when
 * the evaluation results in true. For example, if the observed value in the
 * previous example contained both of the desired values, then the condition
 * will evaluate to true.
 *
 * @param name
 *         The name of the expression.
 * @param subject
 *         The subject of the expression.
 * @param nullable
 *         A flag denoting if the subject of the expression can be
 *         {@code null}.
 * @param observe
 *         The observation function that captured the cached value. For more
 *         information, see the definition of the {@code <O>} generic type
 *         parameter.
 * @param condition
 *         The predicate used to determine when the observed value is passed
 *         (true).
 * @param expected
 *         The expected value associated with the rationale of the evaluated
 *         expression.
 * @param actual
 *         The actual value associated with the rationale of the evaluated
 *         expression.
 * @param hint
 *         The optional hint associated with the rationale of the evaluated
 *         expression.
 * @param <S>
 *         The type of the subject.
 * @param <O>
 *         The type of the observed output. In most cases, the observed output
 *         is the result of the computation performed during evaluation. For
 *         example, if an evaluation computes whether a one or more values is
 *         found in a collection, the observed value may be the values that are
 *         found. This ensures that the evaluation is only performed once. This
 *         observed value is then cached and can be leveraged by fields such as
 *         the actual value and hint without recomputing the observed value. In
 *         practice, this value can be thought of as a cached value.
 *
 * @author Justin Albano
 */
public record ObservableUnaryExpression<S, O>(
        String name,
        S subject,
        boolean nullable,
        Function<S, O> observe,
        Predicate<O> condition,
        Field<S, O> expected,
        Field<S, O> actual,
        Field<S, O> hint
) implements TerminalUnaryExpression<S> {

    /**
     * Creates a new expression.
     *
     * @param name
     *         The name of the expression.
     * @param subject
     *         The subject of the expression.
     * @param nullable
     *         A flag denoting if the subject of the expression can be
     *         {@code null}.
     * @param observe
     *         The observation function that captured the cached value. For more
     *         information, see the definition of the {@code <O>} generic type
     *         parameter.
     * @param condition
     *         The predicate used to determine when the observed value is passed
     *         (true).
     * @param expected
     *         The expected value associated with the rationale of the evaluated
     *         expression.
     * @param actual
     *         The actual value associated with the rationale of the evaluated
     *         expression.
     * @param hint
     *         The optional hint associated with the rationale of the evaluated
     *         expression.
     *
     * @throws NullPointerException
     *         The supplied name, observed value, predicate (condition),
     *         expected value, or actual value are {@code null}.
     */
    public ObservableUnaryExpression {
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

        final Context<S, O> context = new Context<>(subject, observed, passed);
        final Rationale rationale = new SimpleRationale(
                expected.compute(context),
                actual.compute(context),
                hint == null ? null : hint.compute(context)
        );

        return new TerminalEvaluation(passed, name, rationale, elapsedTime);
    }

    /**
     * The context used when computing {@link Field} values.
     *
     * @param subject
     *         The subject.
     * @param observed
     *         The observed value.
     * @param passed
     *         A boolean flag denoting if the evaluation passed.
     * @param <S>
     *         The type of the subject.
     * @param <O>
     *         The type of the observed value.
     */
    public record Context<S, O>(S subject, O observed, boolean passed) {

        /**
         * Returns the supplied value if the evaluation passed (the boolean flag
         * is true); otherwise returns the "orElse" (or default) value.
         *
         * @param ifPassed
         *         The value to return if the evaluation passed.
         * @param orElse
         *         The value to return if the evaluation failed.
         * @param <A>
         *         The type of the desired result.
         *
         * @return The value, based on whether the evaluation passed or failed.
         * The "ifPassed" argument is returned if the evaluation passed;
         * otherwise, the "orElse" value is returned.
         */
        public <A> A ifPassedOrElse(final A ifPassed, final A orElse) {
            return passed ? ifPassed : orElse;
        }
    }

    /**
     * A string-based field that can be computed using the context containing
     * the subject, the observed value, and a boolean denoting if the evaluation
     * passed.
     *
     * @param <S>
     *         The type of the subject.
     * @param <O>
     *         The type of the observed value.
     */
    @FunctionalInterface
    public interface Field<S, O> {

        /**
         * Computes the field based on the supplied context.
         *
         * @param context
         *         The context to use when computing the field. This context
         *         contained the cached data available to the field, including
         *         the subject, the observed value, and a boolean flag denoting
         *         if the evaluation passed.
         *
         * @return The computed field.
         */
        String compute(Context<S, O> context);
    }
}
