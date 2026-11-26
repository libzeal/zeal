package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.evaluation.Rationale;
import io.github.libzeal.zeal.logic.evaluation.SimpleRationale;
import io.github.libzeal.zeal.logic.evaluation.TerminalEvaluation;
import io.github.libzeal.zeal.logic.util.StopWatch;
import org.jspecify.annotations.Nullable;

import java.time.Duration;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.github.libzeal.zeal.logic.format.ObjectFormatter.stringify;
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
 * @param negatedExpected
 *         The optional expected value associated with the rationale of the
 *         evaluated expression when the evaluation is negated. If {@code null},
 *         the default of {@link Rationale#negatedExpected()} is used.
 * @param negatedHint
 *         The optional hint associated with the rationale of the evaluated
 *         expression when the evaluation is negated. If {@code null}, the
 *         default of {@link Rationale#negatedHint()} is used.
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
        @Nullable S subject,
        boolean nullable,
        Function<? super S, ? extends O> observe,
        Predicate<? super O> condition,
        Field<S, O> expected,
        Field<S, O> actual,
        @Nullable Field<S, O> hint,
        @Nullable Field<S, O> negatedExpected,
        @Nullable Field<S, O> negatedHint
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
     * @param negatedExpected
     *         The optional expected value associated with the rationale of the
     *         evaluated expression when the evaluation is negated. If
     *         {@code null}, the default of {@link Rationale#negatedExpected()}
     *         is used.
     * @param negatedHint
     *         The optional hint associated with the rationale of the evaluated
     *         expression when the evaluation is negated. If {@code null}, the
     *         default of {@link Rationale#negatedHint()} is used.
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

    /**
     * Creates a new expression without a negated expected value or negated
     * hint. When the evaluation is negated, the defaults of
     * {@link Rationale#negatedExpected()} and {@link Rationale#negatedHint()}
     * are used.
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
    public ObservableUnaryExpression(
            final String name,
            final @Nullable S subject,
            final boolean nullable,
            final Function<? super S, ? extends O> observe,
            final Predicate<? super O> condition,
            final Field<S, O> expected,
            final Field<S, O> actual,
            final @Nullable Field<S, O> hint
    ) {
        this(name, subject, nullable, observe, condition, expected, actual, hint, null, null);
    }

    @Override
    public TerminalEvaluation evaluate() {

        final StopWatch stopWatch = StopWatch.started();

        if (subject == null && !nullable) {
            final Duration elapsedTime = stopWatch.stop();
            final Context<S, O> context = unobservedContext();
            final Rationale rationale = new SimpleRationale(
                    expected.compute(context),
                    computeIfPresent(negatedExpected, context),
                    stringify(null),
                    null,
                    null
            );

            return new TerminalEvaluation(false, name, rationale, elapsedTime);
        }

        final O observed = observeSubject();
        final boolean passed = condition.test(observed);
        final Duration elapsedTime = stopWatch.stop();

        final Context<S, O> context = new Context<>(subject, observed, passed);
        final Rationale rationale = new SimpleRationale(
                expected.compute(context),
                computeIfPresent(negatedExpected, context),
                actual.compute(context),
                computeIfPresent(hint, context),
                computeIfPresent(negatedHint, context)
        );

        return new TerminalEvaluation(passed, name, rationale, elapsedTime);
    }

    private static <S, O> @Nullable String computeIfPresent(final @Nullable Field<S, O> field, final Context<S, O> context) {
        return field == null ? null : field.compute(context);
    }

    // The subject is only null here when the condition is nullable, and nullable conditions observe using functions
    // that accept null
    @SuppressWarnings("NullAway")
    private O observeSubject() {
        return observe.apply(subject);
    }

    // A null subject is never observed by a non-nullable condition, so only the expected fields are computed, and
    // expected fields do not use the observed value
    @SuppressWarnings("NullAway")
    private Context<S, O> unobservedContext() {
        return new Context<>(subject, null, false);
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
    public record Context<S, O>(
            @Nullable S subject, O observed, boolean passed
    ) {

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
