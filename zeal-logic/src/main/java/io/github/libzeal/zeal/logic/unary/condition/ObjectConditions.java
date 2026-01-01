package io.github.libzeal.zeal.logic.unary.condition;

import io.github.libzeal.zeal.logic.AndExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.Expressions;
import io.github.libzeal.zeal.logic.OrExpression;
import io.github.libzeal.zeal.logic.unary.future.rationale.ComputableRationale;
import io.github.libzeal.zeal.logic.unary.future.rationale.SimpleComputableRationale;
import io.github.libzeal.zeal.logic.unary.future.ComputedExpression;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static io.github.libzeal.zeal.logic.unary.condition.Condition.DEFAULT_NAME;
import static io.github.libzeal.zeal.logic.util.Formatter.stringify;
import static java.util.Objects.requireNonNull;

final class ObjectConditions {

    private ObjectConditions() {
    }

    public static <T> Condition<T> of(final String name, final Predicate<T> predicate) {
        return new SimpleCondition<>(name, predicate);
    }

    public static <T> Condition<T> of(final Predicate<T> predicate) {
        return new SimpleCondition<>(DEFAULT_NAME, predicate);
    }

    public static <T> Condition<T> exactly(final Object desired) {

        final ComputableRationale<T> generator = new SimpleComputableRationale<>(
            (s, passed) -> stringify(desired),
            (s, passed) -> stringify(s),
            (s, passed) -> exactlyHint(desired)
        );

        return subject -> new ComputedExpression<>(
            exactlyName(desired),
            subject,
            o -> o == desired,
            generator
        );
    }

    static <T> String exactlyName(final T desired) {
        return "isExactly[" + stringify(desired) + "]";
    }

    static <T> String exactlyHint(final T desired) {
        return "Actual should be identical to " + desired + " (using ==)";
    }

    public static <T> Condition<T> equalTo(final Object desired) {

        final ComputableRationale<T> generator = new SimpleComputableRationale<>(
            (s, passed) -> stringify(desired),
            (s, passed) -> stringify(s),
            (s, passed) -> equalToHint(desired)
        );

        return subject -> new ComputedExpression<>(
            equalToName(desired),
            subject,
            o -> Objects.equals(o, desired),
            generator
        );
    }

    static <T> String equalToName(final T desired) {
        return "isEqualTo[" + stringify(desired) + "]";
    }

    static <T> String equalToHint(final T desired) {
        return "Actual should be equal to " + desired + " (using subject.equals(" + desired + "))";
    }

    @SafeVarargs
    public static <T> Condition<T> anyOf(final Condition<T>... conditions) {

        requireNonNull(conditions);

        return subject -> {
            final List<Expression> collected = Stream.of(conditions)
                .map(c -> c.compute(subject))
                .collect(Collectors.toList());

            return OrExpression.unnamed(collected);
        };
    }

    @SafeVarargs
    public static <T> Condition<T> anyOf(final T... values) {

        requireNonNull(values);

        return subject -> {
            final List<Expression> collected = Stream.of(values)
                .map(ObjectConditions::equalTo)
                .map(c -> c.compute(subject))
                .collect(Collectors.toList());

            return OrExpression.unnamed(collected);
        };
    }

    public static <T> Condition<T> anyOf(final Iterable<T> values) {

        requireNonNull(values);

        return subject -> {
            final List<Expression> collected = streamOf(values)
                .map(ObjectConditions::equalTo)
                .map(c -> c.compute(subject))
                .collect(Collectors.toList());

            return OrExpression.unnamed(collected);
        };
    }

    private static <T> Stream<T> streamOf(final Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    @SafeVarargs
    public static <T> Condition<T> allOf(final Condition<T>... conditions) {

        requireNonNull(conditions);

        return subject -> {
            final List<Expression> collected = Stream.of(conditions)
                .map(c -> c.compute(subject))
                .collect(Collectors.toList());

            return AndExpression.unnamed(collected);
        };
    }

    @SafeVarargs
    public static <T> Condition<T> allOf(final T... values) {

        requireNonNull(values);

        return subject -> {
            final List<Expression> collected = Stream.of(values)
                .map(ObjectConditions::equalTo)
                .map(c -> c.compute(subject))
                .collect(Collectors.toList());

            return AndExpression.unnamed(collected);
        };
    }

    public static <T> Condition<T> allOf(final Iterable<T> values) {

        requireNonNull(values);

        return subject -> {
            final List<Expression> collected = streamOf(values)
                .map(ObjectConditions::equalTo)
                .map(c -> c.compute(subject))
                .collect(Collectors.toList());

            return AndExpression.unnamed(collected);
        };
    }

    @SafeVarargs
    public static <T> Condition<T> noneOf(final Condition<T>... conditions) {

        requireNonNull(conditions);

        return subject -> Expressions.nor(Stream.of(conditions)
                .map(c -> c.compute(subject)).toArray(Expression[]::new));
    }

    @SafeVarargs
    public static <T> Condition<T> noneOf(final T... values) {

        requireNonNull(values);

        return subject -> Expressions.nor(Stream.of(values)
                .map(ObjectConditions::equalTo)
                .map(c -> c.compute(subject)).toArray(Expression[]::new));
    }

    public static <T> Condition<T> noneOf(final Iterable<T> values) {

        requireNonNull(values);

        return subject -> Expressions.nor(streamOf(values)
                .map(ObjectConditions::equalTo)
                .map(c -> c.compute(subject)).toArray(Expression[]::new));
    }
}
