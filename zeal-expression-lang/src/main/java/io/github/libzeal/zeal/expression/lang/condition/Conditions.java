package io.github.libzeal.zeal.expression.lang.condition;

import io.github.libzeal.zeal.expression.lang.ConjunctiveExpression;
import io.github.libzeal.zeal.expression.lang.DisjunctiveExpression;
import io.github.libzeal.zeal.expression.lang.Expression;
import io.github.libzeal.zeal.expression.lang.NegatedExpression;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Conditions {

    private Conditions() {
    }

    public static <T> Condition<T> of(final String name, final Predicate<T> predicate) {
        return new SimpleCondition<>(name, predicate);
    }

    public static <T> Condition<T> exactly(final T desired) {
        return new ExactlyCondition<>(desired);
    }

    public static <T> Condition<T> exactly(final T desired, final String name) {
        return new ExactlyCondition<>(desired, name);
    }

    public static <T> Condition<T> equalTo(final T desired) {
        return new EqualToCondition<>(desired);
    }

    public static <T> Condition<T> equalTo(final T desired, final String name) {
        return new EqualToCondition<>(desired, name);
    }

    public static <T> Condition<T> not(final Condition<T> condition) {
        return subject -> new NegatedExpression(condition.create(subject));
    }

    public static <T> Condition<T> not(final Condition<T> condition, final String name) {
        return subject -> new NegatedExpression(condition.create(subject), name);
    }

    @SafeVarargs
    public static <T> Condition<T> anyOf(final Condition<T>... conditions) {
        return create(
            Stream.of(conditions),
            UnaryOperator.identity(),
            Conditions::disjunctiveCollector
        );
    }

    private static <T> Condition<T> create(final Stream<Condition<T>> inputs,
                                           final UnaryOperator<Condition<T>> transformer,
                                           final Collector collector) {
        return subject -> {
            final List<Expression> collected = inputs.map(c -> transformer.apply(c).create(subject))
                .collect(Collectors.toList());

            return collector.collect(collected);
        };
    }

    static Expression disjunctiveCollector(final List<Expression> expressions) {
        return new DisjunctiveExpression("Any (OR)", expressions);
    }

    @SafeVarargs
    public static <T> Condition<T> anyOf(final T... options) {
        return create(
            Stream.of(options).map(Conditions::exactly),
            UnaryOperator.identity(),
            Conditions::disjunctiveCollector
        );
    }

    @SafeVarargs
    public static <T> Condition<T> allOf(final Condition<T>... conditions) {
        return create(
            Stream.of(conditions),
            UnaryOperator.identity(),
            Conditions::conjunctiveCollector
        );
    }

    static Expression conjunctiveCollector(final List<Expression> expressions) {
        return new ConjunctiveExpression("All (AND)", expressions);
    }

    @SafeVarargs
    public static <T> Condition<T> allOf(final T... options) {
        return create(
            Stream.of(options).map(Conditions::exactly),
            UnaryOperator.identity(),
            Conditions::conjunctiveCollector
        );
    }

    @SafeVarargs
    public static <T> Condition<T> noneOf(final Condition<T>... conditions) {
        return create(
            Stream.of(conditions),
            Conditions::not,
            Conditions::conjunctiveCollector
        );
    }

    @SafeVarargs
    public static <T> Condition<T> noneOf(final T... options) {
        return create(
            Stream.of(options).map(Conditions::exactly),
            Conditions::not,
            Conditions::conjunctiveCollector
        );
    }

    private interface Collector {
        Expression collect(List<Expression> expressions);
    }
}
