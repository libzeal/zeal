package io.github.libzeal.zeal.expression.lang.condition;

import io.github.libzeal.zeal.expression.lang.compound.ConjunctiveExpression;
import io.github.libzeal.zeal.expression.lang.compound.DisjunctiveExpression;
import io.github.libzeal.zeal.expression.lang.Expression;
import io.github.libzeal.zeal.expression.lang.compound.NonDisjunctiveExpression;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Conditions {

    private Conditions() {
    }

    public static <T> Condition<T> of(final String name, final Predicate<T> predicate) {
        return new SimpleCondition<>(name, predicate);
    }

    public static <T> Condition<T> of(final Predicate<T> predicate) {
        return new SimpleCondition<>("unnamed", predicate);
    }

    public static <T> Condition<T> exactly(final T desired) {
        return new ExactlyCondition<>(desired);
    }

    public static <T> Condition<T> equalTo(final T desired) {
        return new EqualToCondition<>(desired);
    }

    @SafeVarargs
    public static <T> Condition<T> anyOf(final Condition<T>... conditions) {
        return subject -> {
            final List<Expression> collected = Stream.of(conditions)
                .map(c -> c.create(subject))
                .collect(Collectors.toList());

            return DisjunctiveExpression.withDefaultName(collected);
        };
    }

    @SafeVarargs
    public static <T> Condition<T> anyOf(final T... values) {
        return subject -> {
            final List<Expression> collected = Stream.of(values)
                .map(Conditions::exactly)
                .map(c -> c.create(subject))
                .collect(Collectors.toList());

            return DisjunctiveExpression.withDefaultName(collected);
        };
    }

    public static <T> Condition<T> anyOf(final Iterable<T> values) {
        return subject -> {
            final List<Expression> collected = streamOf(values)
                .map(Conditions::exactly)
                .map(c -> c.create(subject))
                .collect(Collectors.toList());

            return DisjunctiveExpression.withDefaultName(collected);
        };
    }

    private static <T> Stream<T> streamOf(final Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    @SafeVarargs
    public static <T> Condition<T> allOf(final Condition<T>... conditions) {
        return subject -> {
            final List<Expression> collected = Stream.of(conditions)
                .map(c -> c.create(subject))
                .collect(Collectors.toList());

            return ConjunctiveExpression.withDefaultName(collected);
        };
    }

    @SafeVarargs
    public static <T> Condition<T> allOf(final T... values) {
        return subject -> {
            final List<Expression> collected = Stream.of(values)
                .map(Conditions::exactly)
                .map(c -> c.create(subject))
                .collect(Collectors.toList());

            return ConjunctiveExpression.withDefaultName(collected);
        };
    }

    public static <T> Condition<T> allOf(final Iterable<T> values) {
        return subject -> {
            final List<Expression> collected = streamOf(values)
                .map(Conditions::exactly)
                .map(c -> c.create(subject))
                .collect(Collectors.toList());

            return ConjunctiveExpression.withDefaultName(collected);
        };
    }

    @SafeVarargs
    public static <T> Condition<T> noneOf(final Condition<T>... conditions) {
        return subject -> {
            final List<Expression> collected = Stream.of(conditions)
                .map(c -> c.create(subject))
                .collect(Collectors.toList());

            return NonDisjunctiveExpression.withDefaultName(collected);
        };
    }

    @SafeVarargs
    public static <T> Condition<T> noneOf(final T... values) {
        return subject -> {
            final List<Expression> collected = Stream.of(values).map(Conditions::exactly)
                .map(c -> c.create(subject))
                .collect(Collectors.toList());

            return NonDisjunctiveExpression.withDefaultName(collected);
        };
    }

    public static <T> Condition<T> noneOf(final Iterable<T> values) {
        return subject -> {
            final List<Expression> collected = streamOf(values)
                .map(Conditions::exactly)
                .map(c -> c.create(subject))
                .collect(Collectors.toList());

            return NonDisjunctiveExpression.withDefaultName(collected);
        };
    }
}
