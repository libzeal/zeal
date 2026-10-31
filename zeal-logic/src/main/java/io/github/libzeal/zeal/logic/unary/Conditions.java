package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.AndExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.Expressions;
import io.github.libzeal.zeal.logic.OrExpression;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static io.github.libzeal.zeal.logic.format.ObjectFormatter.stringify;
import static io.github.libzeal.zeal.logic.unary.Condition.DEFAULT_NAME;
import static java.util.Objects.requireNonNull;

/**
 * A set of common conditions.
 *
 * @author Justin Albano
 */
public final class Conditions {

    private Conditions() {
    }

    /**
     * Creates a new condition with the supplied name and predicate.
     *
     * @param name
     *         The name of the condition.
     * @param predicate
     *         The predicate used to evaluate the subject of the condition.
     * @param <T>
     *         The type of the subject.
     *
     * @return A new condition.
     *
     * @throws NullPointerException
     *         Any of the supplied arguments are {@code null}.
     */
    public static <T> Condition<T> of(final String name, final Predicate<T> predicate) {
        return ConditionBuilder.of(predicate)
                .nullable()
                .name(name)
                .expected("satisfied")
                .actual(context -> context.ifPassedOrElse("satisfied", "unsatisfied"))
                .build();
    }


    /**
     * Creates a new condition with a default name and the supplied predicate.
     *
     * @param predicate
     *         The predicate used to evaluate the subject of the condition.
     * @param <T>
     *         The type of the subject.
     *
     * @return A new condition.
     *
     * @throws NullPointerException
     *         The supplied predicate is {@code null}.
     */
    public static <T> Condition<T> of(final Predicate<T> predicate) {
        return of(DEFAULT_NAME, predicate);
    }

    /**
     * Creates a condition that evaluates to true if the subject exactly matches
     * the supplied desired value.
     *
     * @param desired
     *         The desired value that the subject should exactly match.
     * @param <T>
     *         The type of the subject.
     *
     * @return A new condition that evaluates to true if the subject exactly
     * matches the supplied desired value.
     */
    public static <T> Condition<T> exactly(final Object desired) {
        return ConditionBuilder.of((T o) -> o == desired)
                .nullable()
                .name(exactlyName(desired))
                .expected(stringify(desired))
                .hint(exactlyHint(desired))
                .build();
    }

    static <T> String exactlyName(final T desired) {
        return "isExactly[" + stringify(desired) + "]";
    }

    static <T> String exactlyHint(final T desired) {
        return "Actual should be identical to " + desired + " (using ==)";
    }

    /**
     * Creates a condition that evaluates to true if the subject matches the
     * supplied desired value.
     *
     * @param desired
     *         The desired value that the subject should match.
     * @param <T>
     *         The type of the subject.
     *
     * @return A new condition that evaluates to true if the subject matches the
     * supplied desired value.
     */
    public static <T> Condition<T> equalTo(final Object desired) {
        return ConditionBuilder.of((T o) -> Objects.equals(o, desired))
                .nullable()
                .name(equalToName(desired))
                .expected(stringify(desired))
                .hint(equalToHint(desired))
                .build();
    }

    static <T> String equalToName(final T desired) {
        return "isEqualTo[" + stringify(desired) + "]";
    }

    static <T> String equalToHint(final T desired) {
        return "Actual should be equal to " + desired + " (using subject.equals(" + desired + "))";
    }

    /**
     * Creates a condition that evaluates if any the supplied conditions are
     * true.
     *
     * @param conditions
     *         The conditions to check.
     * @param <T>
     *         The type of the subject.
     *
     * @return A new condition that evaluates to true if any the supplied
     * conditions are true.
     */
    @SafeVarargs
    public static <T> Condition<T> anyOf(final Condition<T>... conditions) {

        requireNonNull(conditions);

        return subject -> {
            final List<Expression> collected = Stream.of(conditions)
                    .map(c -> c.bind(subject))
                    .toList();

            return OrExpression.unnamed(collected);
        };
    }

    /**
     * Creates a condition that evaluates if the subject matches any of the
     * supplied values.
     *
     * @param values
     *         The values to check.
     * @param <T>
     *         The type of the subject.
     *
     * @return A new condition that evaluations to true if the subject matches
     * any of the supplied values.
     *
     * @throws NullPointerException
     *         The supplied array is {@code null}.
     */
    @SafeVarargs
    public static <T> Condition<T> anyOf(final T... values) {

        requireNonNull(values);

        return subject -> {
            final List<Expression> collected = Stream.of(values)
                    .map(Conditions::equalTo)
                    .map(c -> c.bind(subject))
                    .toList();

            return OrExpression.unnamed(collected);
        };
    }

    /**
     * Creates a condition that evaluates if the subject matches any of the
     * supplied values.
     *
     * @param values
     *         The values to check.
     * @param <T>
     *         The type of the subject.
     *
     * @return A new condition that evaluations to true if the subject matches
     * any of the supplied values.
     *
     * @throws NullPointerException
     *         The supplied {@link Iterable} is {@code null}.
     */
    public static <T> Condition<T> anyOf(final Iterable<T> values) {

        requireNonNull(values);

        return subject -> {
            final List<Expression> collected = streamOf(values)
                    .map(Conditions::equalTo)
                    .map(c -> c.bind(subject))
                    .toList();

            return OrExpression.unnamed(collected);
        };
    }

    private static <T> Stream<T> streamOf(final Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    /**
     * Creates a condition that evaluates if all the supplied conditions are
     * true.
     *
     * @param conditions
     *         The conditions to check.
     * @param <T>
     *         The type of the subject.
     *
     * @return A new condition that evaluates to true if all the supplied
     * conditions are true.
     */
    @SafeVarargs
    public static <T> Condition<T> allOf(final Condition<T>... conditions) {

        requireNonNull(conditions);

        return subject -> {
            final List<Expression> collected = Stream.of(conditions)
                    .map(c -> c.bind(subject))
                    .toList();

            return AndExpression.unnamed(collected);
        };
    }

    /**
     * Creates a condition that evaluates if the subject matches all the
     * supplied values.
     *
     * @param values
     *         The values to check.
     * @param <T>
     *         The type of the subject.
     *
     * @return A new condition that evaluations to true if the subject matches
     * all the supplied values.
     *
     * @throws NullPointerException
     *         The supplied array is {@code null}.
     */
    @SafeVarargs
    public static <T> Condition<T> allOf(final T... values) {

        requireNonNull(values);

        return subject -> {
            final List<Expression> collected = Stream.of(values)
                    .map(Conditions::equalTo)
                    .map(c -> c.bind(subject))
                    .toList();

            return AndExpression.unnamed(collected);
        };
    }

    /**
     * Creates a condition that evaluates if the subject matches all the
     * supplied values.
     *
     * @param values
     *         The values to check.
     * @param <T>
     *         The type of the subject.
     *
     * @return A new condition that evaluations to true if the subject matches
     * all the supplied values.
     *
     * @throws NullPointerException
     *         The supplied {@link Iterable} is {@code null}.
     */
    public static <T> Condition<T> allOf(final Iterable<T> values) {

        requireNonNull(values);

        return subject -> {
            final List<Expression> collected = streamOf(values)
                    .map(Conditions::equalTo)
                    .map(c -> c.bind(subject))
                    .toList();

            return AndExpression.unnamed(collected);
        };
    }

    /**
     * Creates a condition that evaluates if none of the supplied conditions are
     * true.
     *
     * @param conditions
     *         The conditions to check.
     * @param <T>
     *         The type of the subject.
     *
     * @return A new condition that evaluates to true if none of the supplied
     * conditions are true.
     */
    @SafeVarargs
    public static <T> Condition<T> noneOf(final Condition<T>... conditions) {

        requireNonNull(conditions);

        return subject -> Expressions.nor(Stream.of(conditions)
                .map(c -> c.bind(subject)).toArray(Expression[]::new));
    }

    /**
     * Creates a condition that evaluates if the subject matches none of the
     * supplied values.
     *
     * @param values
     *         The values to check.
     * @param <T>
     *         The type of the subject.
     *
     * @return A new condition that evaluations to true if the subject does not
     * match any of the supplied values.
     *
     * @throws NullPointerException
     *         The supplied array is {@code null}.
     */
    @SafeVarargs
    public static <T> Condition<T> noneOf(final T... values) {

        requireNonNull(values);

        return subject -> Expressions.nor(Stream.of(values)
                .map(Conditions::equalTo)
                .map(c -> c.bind(subject)).toArray(Expression[]::new));
    }

    /**
     * Creates a condition that evaluates if the subject matches none of the
     * supplied values.
     *
     * @param values
     *         The values to check.
     * @param <T>
     *         The type of the subject.
     *
     * @return A new condition that evaluations to true if the subject matches
     * none of the supplied values.
     *
     * @throws NullPointerException
     *         The supplied {@link Iterable} is {@code null}.
     */
    public static <T> Condition<T> noneOf(final Iterable<T> values) {

        requireNonNull(values);

        return subject -> Expressions.nor(streamOf(values)
                .map(Conditions::equalTo)
                .map(c -> c.bind(subject)).toArray(Expression[]::new));
    }
}
