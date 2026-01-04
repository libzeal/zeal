package io.github.libzeal.zeal.logic.unary.condition;

import java.util.function.Predicate;

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
     *     The name of the condition.
     * @param predicate
     *     The predicate used to evaluate the subject of the condition.
     * @param <T>
     *     The type of the subject.
     *
     * @return A new condition.
     *
     * @throws NullPointerException
     *     Any of the supplied arguments are {@code null}.
     */
    public static <T> Condition<T> of(final String name, final Predicate<T> predicate) {
        return ObjectConditions.of(name, predicate);
    }


    /**
     * Creates a new condition with a default name and the supplied predicate.
     *
     * @param predicate
     *     The predicate used to evaluate the subject of the condition.
     * @param <T>
     *     The type of the subject.
     *
     * @return A new condition.
     *
     * @throws NullPointerException
     *     The supplied predicate is {@code null}.
     */
    public static <T> Condition<T> of(final Predicate<T> predicate) {
        return ObjectConditions.of(predicate);
    }

    /**
     * Creates a condition that evaluates to true if the subject exactly matches the supplied desired value.
     *
     * @param desired
     *     The desired value that the subject should exactly match.
     * @param <T>
     *     The type of the subject.
     *
     * @return A new condition that evaluates to true if the subject exactly matches the supplied desired value.
     */
    public static <T> Condition<T> exactly(final Object desired) {
        return ObjectConditions.exactly(desired);
    }

    /**
     * Creates a condition that evaluates to true if the subject matches the supplied desired value.
     *
     * @param desired
     *     The desired value that the subject should match.
     * @param <T>
     *     The type of the subject.
     *
     * @return A new condition that evaluates to true if the subject matches the supplied desired value.
     */
    public static <T> Condition<T> equalTo(final Object desired) {
        return ObjectConditions.equalTo(desired);
    }

    /**
     * Creates a condition that evaluates if any the supplied conditions are true.
     *
     * @param conditions
     *     The conditions to check.
     * @param <T>
     *     The type of the subject.
     *
     * @return A new condition that evaluates to true if any the supplied conditions are true.
     */
    @SafeVarargs
    public static <T> Condition<T> anyOf(final Condition<T>... conditions) {
        return ObjectConditions.anyOf(conditions);
    }

    /**
     * Creates a condition that evaluates if the subject matches any of the supplied values.
     *
     * @param values
     *     The values to check.
     * @param <T>
     *     The type of the subject.
     *
     * @return A new condition that evaluations to true if the subject matches any of the supplied values.
     *
     * @throws NullPointerException
     *     The supplied array is {@code null}.
     */
    @SafeVarargs
    public static <T> Condition<T> anyOf(final T... values) {
        return ObjectConditions.anyOf(values);
    }

    /**
     * Creates a condition that evaluates if the subject matches any of the supplied values.
     *
     * @param values
     *     The values to check.
     * @param <T>
     *     The type of the subject.
     *
     * @return A new condition that evaluations to true if the subject matches any of the supplied values.
     *
     * @throws NullPointerException
     *     The supplied {@link Iterable} is {@code null}.
     */
    public static <T> Condition<T> anyOf(final Iterable<T> values) {
        return ObjectConditions.anyOf(values);
    }

    /**
     * Creates a condition that evaluates if all the supplied conditions are true.
     *
     * @param conditions
     *     The conditions to check.
     * @param <T>
     *     The type of the subject.
     *
     * @return A new condition that evaluates to true if all the supplied conditions are true.
     */
    @SafeVarargs
    public static <T> Condition<T> allOf(final Condition<T>... conditions) {
        return ObjectConditions.allOf(conditions);
    }

    /**
     * Creates a condition that evaluates if the subject matches all the supplied values.
     *
     * @param values
     *     The values to check.
     * @param <T>
     *     The type of the subject.
     *
     * @return A new condition that evaluations to true if the subject matches all the supplied values.
     *
     * @throws NullPointerException
     *     The supplied array is {@code null}.
     */
    @SafeVarargs
    public static <T> Condition<T> allOf(final T... values) {
        return ObjectConditions.allOf(values);
    }

    /**
     * Creates a condition that evaluates if the subject matches all the supplied values.
     *
     * @param values
     *     The values to check.
     * @param <T>
     *     The type of the subject.
     *
     * @return A new condition that evaluations to true if the subject matches all the supplied values.
     *
     * @throws NullPointerException
     *     The supplied {@link Iterable} is {@code null}.
     */
    public static <T> Condition<T> allOf(final Iterable<T> values) {
        return ObjectConditions.allOf(values);
    }

    /**
     * Creates a condition that evaluates if none of the supplied conditions are true.
     *
     * @param conditions
     *     The conditions to check.
     * @param <T>
     *     The type of the subject.
     *
     * @return A new condition that evaluates to true if none of the supplied conditions are true.
     */
    @SafeVarargs
    public static <T> Condition<T> noneOf(final Condition<T>... conditions) {
        return ObjectConditions.noneOf(conditions);
    }

    /**
     * Creates a condition that evaluates if the subject matches none of the supplied values.
     *
     * @param values
     *     The values to check.
     * @param <T>
     *     The type of the subject.
     *
     * @return A new condition that evaluations to true if the subject does not match any of the supplied values.
     *
     * @throws NullPointerException
     *     The supplied array is {@code null}.
     */
    @SafeVarargs
    public static <T> Condition<T> noneOf(final T... values) {
        return ObjectConditions.noneOf(values);
    }

    /**
     * Creates a condition that evaluates if the subject matches none of the supplied values.
     *
     * @param values
     *     The values to check.
     * @param <T>
     *     The type of the subject.
     *
     * @return A new condition that evaluations to true if the subject matches none of the supplied values.
     *
     * @throws NullPointerException
     *     The supplied {@link Iterable} is {@code null}.
     */
    public static <T> Condition<T> noneOf(final Iterable<T> values) {
        return ObjectConditions.noneOf(values);
    }
}
