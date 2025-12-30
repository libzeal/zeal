package io.github.libzeal.zeal.logic.condition;

import io.github.libzeal.zeal.logic.AndExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.Operations;
import io.github.libzeal.zeal.logic.OrExpression;
import io.github.libzeal.zeal.logic.rationale.RationaleGenerator;
import io.github.libzeal.zeal.logic.rationale.SimpleRationaleGenerator;
import io.github.libzeal.zeal.logic.unary.TerminalUnaryExpression;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static io.github.libzeal.zeal.logic.condition.Condition.DEFAULT_NAME;
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

    public static <T> Condition<T> exactly(final T desired) {

        final RationaleGenerator<T> generator = new SimpleRationaleGenerator<>(
            (s, passed) -> stringify(desired),
            (s, passed) -> stringify(s),
            (s, passed) -> exactlyHint(desired)
        );

        return subject -> TerminalUnaryExpression.of(
            exactlyName(desired),
            subject,
            o -> o == desired,
            generator
        )
            .expression();
    }

    static <T> String exactlyName(final T desired) {
        return "isExactly[" + stringify(desired) + "]";
    }

    static <T> String exactlyHint(final T desired) {
        return "Actual should be identical to " + desired + " (using ==)";
    }

    public static <T> Condition<T> equalTo(final T desired) {

        final RationaleGenerator<T> generator = new SimpleRationaleGenerator<>(
            (s, passed) -> stringify(desired),
            (s, passed) -> stringify(s),
            (s, passed) -> equalToHint(desired)
        );

        return subject -> TerminalUnaryExpression.of(
            equalToName(desired),
            subject,
            o -> Objects.equals(o, desired),
            generator
        )
            .expression();
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
                .map(c -> c.create(subject))
                .collect(Collectors.toList());

            return OrExpression.withDefaultName(collected);
        };
    }

    @SafeVarargs
    public static <T> Condition<T> anyOf(final T... values) {

        requireNonNull(values);

        return subject -> {
            final List<Expression> collected = Stream.of(values)
                .map(ObjectConditions::equalTo)
                .map(c -> c.create(subject))
                .collect(Collectors.toList());

            return OrExpression.withDefaultName(collected);
        };
    }

    public static <T> Condition<T> anyOf(final Iterable<T> values) {

        requireNonNull(values);

        return subject -> {
            final List<Expression> collected = streamOf(values)
                .map(ObjectConditions::equalTo)
                .map(c -> c.create(subject))
                .collect(Collectors.toList());

            return OrExpression.withDefaultName(collected);
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
                .map(c -> c.create(subject))
                .collect(Collectors.toList());

            return AndExpression.withDefaultName(collected);
        };
    }

    @SafeVarargs
    public static <T> Condition<T> allOf(final T... values) {

        requireNonNull(values);

        return subject -> {
            final List<Expression> collected = Stream.of(values)
                .map(ObjectConditions::equalTo)
                .map(c -> c.create(subject))
                .collect(Collectors.toList());

            return AndExpression.withDefaultName(collected);
        };
    }

    public static <T> Condition<T> allOf(final Iterable<T> values) {

        requireNonNull(values);

        return subject -> {
            final List<Expression> collected = streamOf(values)
                .map(ObjectConditions::equalTo)
                .map(c -> c.create(subject))
                .collect(Collectors.toList());

            return AndExpression.withDefaultName(collected);
        };
    }

    @SafeVarargs
    public static <T> Condition<T> noneOf(final Condition<T>... conditions) {

        requireNonNull(conditions);

        return subject -> {
            final List<Expression> collected = Stream.of(conditions)
                .map(c -> c.create(subject))
                .collect(Collectors.toList());

            return Operations.nor(collected.toArray(new Expression[0]));
        };
    }

    @SafeVarargs
    public static <T> Condition<T> noneOf(final T... values) {

        requireNonNull(values);

        return subject -> {
            final List<Expression> collected = Stream.of(values)
                .map(ObjectConditions::equalTo)
                .map(c -> c.create(subject))
                .collect(Collectors.toList());

            return Operations.nor(collected.toArray(new Expression[0]));
        };
    }

    public static <T> Condition<T> noneOf(final Iterable<T> values) {

        requireNonNull(values);

        return subject -> {
            final List<Expression> collected = streamOf(values)
                .map(ObjectConditions::equalTo)
                .map(c -> c.create(subject))
                .collect(Collectors.toList());

            return Operations.nor(collected.toArray(new Expression[0]));
        };
    }
}
