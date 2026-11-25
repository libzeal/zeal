package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.AndExpression;
import io.github.libzeal.zeal.logic.NorExpression;
import io.github.libzeal.zeal.logic.OrExpression;
import io.github.libzeal.zeal.logic.format.ObjectFormatter;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.github.libzeal.zeal.logic.Expression.DEFAULT_NAME;
import static io.github.libzeal.zeal.logic.format.ObjectFormatter.stringify;
import static java.util.Objects.requireNonNull;

/**
 * A set of common conditions.
 *
 * @author Justin Albano
 */
public final class Conditions {

    private static final String ANY_OF = "anyOf";
    private static final String ALL_OF = "allOf";
    private static final String NONE_OF = "noneOf";
    private static final String ONE_OF = "oneOf";
    private static final String NOT_ONE_OF = "notOneOf";
    private static final String IN = "in";
    private static final String NOT_IN = "notIn";

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
    public static <T> Condition<T> of(final String name, final Predicate<? super T> predicate) {
        return ConditionBuilder.<T>of(predicate)
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
    public static <T> Condition<T> of(final Predicate<? super T> predicate) {
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
    public static <T> Condition<T> exactly(final @Nullable Object desired) {
        return ConditionBuilder.of((T o) -> o == desired)
                .nullable()
                .name(exactlyName(desired))
                .expected(stringify(desired))
                .hint(context -> context.ifPassedOrElse(exactlyPassedHint(desired), exactlyHint(desired)))
                .negatedHint(exactlyNegatedHint(desired))
                .build();
    }

    static <T> String exactlyName(final @Nullable T desired) {
        return "isExactly[" + stringify(desired) + "]";
    }

    static <T> String exactlyHint(final @Nullable T desired) {
        return "Actual should be identical to " + stringify(desired) + " (using ==)";
    }

    static <T> String exactlyPassedHint(final @Nullable T desired) {
        return "Actual is identical to " + stringify(desired) + " (using ==)";
    }

    static <T> String exactlyNegatedHint(final @Nullable T desired) {
        return "Actual should not be identical to " + stringify(desired) + " (using ==)";
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
    public static <T> Condition<T> equalTo(final @Nullable Object desired) {
        return ConditionBuilder.of((T o) -> Objects.deepEquals(o, desired))
                .nullable()
                .name(equalToName(desired))
                .expected(stringify(desired))
                .hint(context -> context.ifPassedOrElse(equalToPassedHint(desired), equalToHint(desired)))
                .negatedHint(equalToNegatedHint(desired))
                .build();
    }

    static <T> String equalToName(final @Nullable T desired) {
        return "isEqualTo[" + stringify(desired) + "]";
    }

    static <T> String equalToHint(final @Nullable T desired) {
        return "Actual should be equal to " + stringify(desired) + " (using subject.equals(" + stringify(desired) + "))";
    }

    static <T> String equalToPassedHint(final @Nullable T desired) {
        return "Actual is equal to " + stringify(desired) + " (using subject.equals(" + stringify(desired) + "))";
    }

    static <T> String equalToNegatedHint(final @Nullable T desired) {
        return "Actual should not be equal to " + stringify(desired) + " (using subject.equals(" + stringify(desired) + "))";
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
    public static <T> Condition<T> anyOf(final Condition<? super T>... conditions) {

        requireNonNull(conditions);

        return Conditions.anyOf(Arrays.<Condition<? super T>>asList(conditions));
    }

    /**
     * Creates a condition that evaluates if any of the supplied conditions are
     * true.
     *
     * @param conditions
     *         The conditions to check.
     * @param <T>
     *         The type of the subject.
     *
     * @return A new condition that evaluates to true if any of the supplied
     * conditions are true.
     *
     * @throws NullPointerException
     *         The supplied {@link List} is {@code null}.
     */
    public static <T> Condition<T> anyOf(
            final List<? extends Condition<? super T>> conditions
    ) {
        requireNonNull(conditions);

        return subject -> {
            final var collected = conditions.stream()
                    .map(c -> c.bind(subject))
                    .toList();

            return new UnaryCompoundAdapter<>(
                    subject,
                    OrExpression.named(ANY_OF, collected)
            );
        };
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
    public static <T> Condition<T> allOf(final Condition<? super T>... conditions) {

        requireNonNull(conditions);

        return Conditions.allOf(Arrays.<Condition<? super T>>asList(conditions));
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
     *
     * @throws NullPointerException
     *         The supplied {@link List} is {@code null}.
     */
    public static <T> Condition<T> allOf(
            final List<? extends Condition<? super T>> conditions
    ) {
        requireNonNull(conditions);

        return subject -> {
            final var collected = conditions.stream()
                    .map(c -> c.bind(subject))
                    .toList();

            return new UnaryCompoundAdapter<>(
                    subject,
                    AndExpression.named(ALL_OF, collected)
            );
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
    public static <T> Condition<T> noneOf(final Condition<? super T>... conditions) {

        requireNonNull(conditions);

        return Conditions.noneOf(Arrays.<Condition<? super T>>asList(conditions));
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
     *
     * @throws NullPointerException
     *         The supplied {@link List} is {@code null}.
     */
    public static <T> Condition<T> noneOf(
            final List<? extends Condition<? super T>> conditions
    ) {
        requireNonNull(conditions);

        return subject -> {
            final var collected = conditions.stream()
                    .map(c -> c.bind(subject))
                    .toList();

            return new UnaryCompoundAdapter<>(
                    subject,
                    NorExpression.named(NONE_OF, collected)
            );
        };
    }

    /**
     * Creates a condition that evaluates if the subject is equal to one of the
     * supplied values. Values are compared to the subject using
     * {@link Objects#deepEquals(Object, Object)}.
     *
     * @param values
     *         The values to check.
     * @param <T>
     *         The type of the subject.
     *
     * @return A new condition that evaluates to true if the subject is equal to
     * one of the supplied values.
     *
     * @throws NullPointerException
     *         The supplied array is {@code null}.
     */
    @SafeVarargs
    public static <T> Condition<T> oneOf(final @Nullable T... values) {

        requireNonNull(values);

        return equalToAnyOf(ONE_OF, Arrays.asList(values));
    }

    /**
     * Creates a condition that evaluates if the subject is equal to one of the
     * values in the supplied list. Values are compared to the subject using
     * {@link Objects#deepEquals(Object, Object)}.
     *
     * @param values
     *         The values to check.
     * @param <T>
     *         The type of the subject.
     *
     * @return A new condition that evaluates to true if the subject is equal to
     * one of the values in the supplied list.
     *
     * @throws NullPointerException
     *         The supplied {@link List} is {@code null}.
     */
    public static <T> Condition<T> in(final List<? extends @Nullable T> values) {

        requireNonNull(values);

        return equalToAnyOf(IN, values);
    }

    /**
     * Creates a condition that evaluates if the subject is not equal to any of
     * the supplied values. Values are compared to the subject using
     * {@link Objects#deepEquals(Object, Object)}.
     *
     * @param values
     *         The values to check.
     * @param <T>
     *         The type of the subject.
     *
     * @return A new condition that evaluates to true if the subject is not
     * equal to any of the supplied values.
     *
     * @throws NullPointerException
     *         The supplied array is {@code null}.
     */
    @SafeVarargs
    public static <T> Condition<T> notOneOf(final @Nullable T... values) {

        requireNonNull(values);

        return equalToNoneOf(NOT_ONE_OF, Arrays.asList(values));
    }

    /**
     * Creates a condition that evaluates if the subject is not equal to any of
     * the values in the supplied list. Values are compared to the subject using
     * {@link Objects#deepEquals(Object, Object)}.
     *
     * @param values
     *         The values to check.
     * @param <T>
     *         The type of the subject.
     *
     * @return A new condition that evaluates to true if the subject is not
     * equal to any of the values in the supplied list.
     *
     * @throws NullPointerException
     *         The supplied {@link List} is {@code null}.
     */
    public static <T> Condition<T> notIn(final List<? extends @Nullable T> values) {

        requireNonNull(values);

        return equalToNoneOf(NOT_IN, values);
    }

    private static <T> Condition<T> equalToAnyOf(final String name, final List<? extends @Nullable T> values) {
        return subject -> new UnaryCompoundAdapter<>(
                subject,
                OrExpression.named(name, equalToAll(values, subject)),
                oneOfHint(subject, values),
                notOneOfHint(subject, values)
        );
    }

    private static <T> Condition<T> equalToNoneOf(final String name, final List<? extends @Nullable T> values) {
        return subject -> new UnaryCompoundAdapter<>(
                subject,
                NorExpression.named(name, equalToAll(values, subject)),
                notOneOfHint(subject, values),
                oneOfHint(subject, values)
        );
    }

    static String oneOfHint(final @Nullable Object subject, final List<?> values) {
        return stringify(subject) + " should be one of " + stringifyAll(values);
    }

    static String notOneOfHint(final @Nullable Object subject, final List<?> values) {
        return stringify(subject) + " should not be one of " + stringifyAll(values);
    }

    private static String stringifyAll(final List<?> values) {
        return values.stream()
                .map(ObjectFormatter::stringify)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    private static <T> List<UnaryExpression<T>> equalToAll(
            final List<? extends @Nullable T> values,
            final @Nullable T subject
    ) {
        return values.stream()
                .map(value -> Conditions.<T>equalTo(value).bind(subject))
                .toList();
    }
}
