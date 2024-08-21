package io.github.libzeal.zeal.logic.condition;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.compound.ConjunctiveExpression;
import io.github.libzeal.zeal.logic.compound.DisjunctiveExpression;
import io.github.libzeal.zeal.logic.compound.NonDisjunctiveExpression;
import io.github.libzeal.zeal.logic.rationale.RationaleGenerator;
import io.github.libzeal.zeal.logic.rationale.SimpleRationaleGenerator;
import io.github.libzeal.zeal.logic.unary.TerminalUnaryExpression;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static io.github.libzeal.zeal.logic.util.Formatter.stringify;

public class Conditions {

    static final String UNNAMED = "unnamed";

    private Conditions() {
    }

    public static <T> Condition<T> of(final String name, final Predicate<T> predicate) {
        return new SimpleCondition<>(name, predicate);
    }

    public static <T> Condition<T> of(final Predicate<T> predicate) {
        return new SimpleCondition<>(UNNAMED, predicate);
    }

    public static <T> Condition<T> exactly(final T desired) {

        final RationaleGenerator<T> generator = new SimpleRationaleGenerator<>(
            (s, passed) -> stringify(desired),
            (s, passed) -> stringify(s),
            (s, passed) -> "Actual should be identical to " + desired + " (using ==)"
        );

        return subject -> TerminalUnaryExpression.ofNullable(
            "is[" + stringify(desired) + "]",
            subject,
            o -> o == desired,
            generator
        );
    }

    public static <T> Condition<T> equalTo(final T desired) {

        final RationaleGenerator<T> generator = new SimpleRationaleGenerator<>(
            (s, passed) -> stringify(desired),
            (s, passed) -> stringify(s),
            (s, passed) -> "Actual should be equal to " + desired + " (using subject.equals(" + desired + "))"
        );

        return subject -> TerminalUnaryExpression.ofNullable(
            "isEqualTo[" + stringify(desired) + "]",
            subject,
            o -> Objects.equals(o, desired),
            generator
        );
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
