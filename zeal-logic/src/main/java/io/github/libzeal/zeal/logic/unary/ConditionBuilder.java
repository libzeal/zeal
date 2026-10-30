package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.format.Formatter;
import io.github.libzeal.zeal.logic.unary.ObservableUnaryExpression.Field;

import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * A builder used to create unary expressions.
 *
 * @param <S>
 *         The type of the subject.
 * @param <O>
 *         The type of the observed value. See
 *         {@link ObservableUnaryExpression}.
 */
public final class ConditionBuilder<S, O> {

    private final Function<S, O> observe;
    private final Predicate<O> condition;
    private boolean nullable = false;
    private String name = "<unnamed>";
    private Field<S, O> expected = context -> "<not set>";
    private Field<S, O> actual = context -> Formatter.stringify(context.observed());
    private Field<S, O> hint = null;

    private ConditionBuilder(final Function<S, O> observe, final Predicate<O> condition) {
        this.observe = requireNonNull(observe);
        this.condition = requireNonNull(condition);
    }

    /**
     * Creates a builder based on the supplied predicate. This method should be
     * used when observation (caching) is not needed. If observation is needed,
     * the {@link #observing(Function)} method should be used instead.
     *
     * @param condition
     *         The predicate used for evaluating the expression.
     * @param <S>
     *         The type of the subject.
     *
     * @return The builder (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied predicate is {@code null}.
     */
    public static <S> ConditionBuilder<S, S> of(final Predicate<S> condition) {
        return new ConditionBuilder<>(Function.identity(), condition);
    }

    /**
     * Creates a builder based on the supplied observation function. This method
     * should be used when observation (caching) is needed. If observation is
     * not needed, the {@link #of(Predicate)} method should be used instead.
     *
     * @param observe
     *         The function used to compute the observed value.
     * @param <S>
     *         The type of the subject.
     * @param <O>
     *         The type of the observed value.
     *
     * @return The observation builder (fluent interface). This observation
     * build will be used to select the condition in which the observed value
     * passes.
     *
     * @throws NullPointerException
     *         The supplied observation function is {@code null}.
     */
    public static <S, O> Observing<S, O> observing(final Function<S, O> observe) {
        return new Observing<>(observe);
    }

    /**
     * Allows the expression to have a null subject.
     *
     * @return The builder (fluent interface).
     */
    public ConditionBuilder<S, O> nullable() {
        this.nullable = true;
        return this;
    }

    /**
     * Sets the name of the expression.
     *
     * @param name
     *         The desired name.
     *
     * @return The builder (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied name is {@code null}.
     */
    public ConditionBuilder<S, O> name(final String name) {
        this.name = requireNonNull(name);
        return this;
    }

    /**
     * Sets the expected value based on the observed value.
     *
     * @param expected
     *         The desired expected value as a function of the observed value.
     *
     * @return The builder (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied expected value is {@code null}.
     */
    public ConditionBuilder<S, O> expected(final Field<S, O> expected) {
        this.expected = requireNonNull(expected);
        return this;
    }

    /**
     * Sets the expected value.
     *
     * @param expected
     *         The desired expected value.
     *
     * @return The builder (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied expected value is {@code null}.
     */
    public ConditionBuilder<S, O> expected(final String expected) {

        requireNonNull(expected);

        return expected(context -> expected);
    }

    /**
     * Sets the expected value. This method is a convenience method to allow
     * numeric values to be set as the expected value (common for indices,
     * sizes, and hash codes).
     *
     * @param expected
     *         The desired expected value.
     *
     * @return The builder (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied expected value is {@code null}.
     */
    public ConditionBuilder<S, O> expected(final long expected) {
        return expected(String.valueOf(expected));
    }

    /**
     * Sets the actual value based on the observed value.
     *
     * @param actual
     *         The desired actual value as a function of the observed value.
     *
     * @return The builder (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied actual value is {@code null}.
     */
    public ConditionBuilder<S, O> actual(final Field<S, O> actual) {
        this.actual = requireNonNull(actual);
        return this;
    }

    /**
     * Sets the actual value.
     *
     * @param actual
     *         The desired actual value.
     *
     * @return The builder (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied actual value is {@code null}.
     */
    public ConditionBuilder<S, O> actual(final String actual) {

        requireNonNull(actual);

        return actual(context -> actual);
    }

    /**
     * Sets the hint based on the observed value.
     *
     * @param hint
     *         The desired hint as a function of the observed value.
     *
     * @return The builder (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied hint is {@code null}.
     */
    public ConditionBuilder<S, O> hint(final Field<S, O> hint) {
        this.hint = requireNonNull(hint);
        return this;
    }

    /**
     * Sets the hint.
     *
     * @param hint
     *         The desired hint.
     *
     * @return The builder (fluent interface).
     *
     * @throws NullPointerException
     *         The supplied hint is {@code null}.
     */
    public ConditionBuilder<S, O> hint(final String hint) {

        requireNonNull(hint);

        return hint(context -> hint);
    }

    /**
     * Creates the condition based on the current state of the builder.
     *
     * @return The built condition.
     */
    public Condition<S> build() {

        final String name = this.name;
        final boolean nullable = this.nullable;
        final Field<S, O> expected = this.expected;
        final Field<S, O> actual = this.actual;
        final Field<S, O> hint = this.hint;

        return subject -> new ObservableUnaryExpression<>(
                name, subject, nullable, observe, condition, expected, actual,
                hint
        );
    }

    /**
     * Builder interface used to configure how the observed value should be
     * interpreted. Particularly, when the observed value is considered passed
     * (true).
     *
     * @param observe
     *         The observation function.
     * @param <S>
     *         The type of the subject.
     * @param <O>
     *         The type of the observed value.
     */
    public record Observing<S, O>(Function<S, O> observe) {

        /**
         * Creates a new observation builder.
         *
         * @param observe
         *         The observation function.
         *
         * @throws NullPointerException
         *         The supplied observation function is {@code null}.
         */
        public Observing {
            requireNonNull(observe);
        }

        /**
         * Defines when the observed value is considered passed (true).
         *
         * @param condition
         *         The predicate when the observed value is considered passed.
         *
         * @return A builder interface (fluent interface).
         *
         * @throws NullPointerException
         *         The supplied predicate is {@code null}.
         */
        public ConditionBuilder<S, O> passesIf(final Predicate<O> condition) {
            return new ConditionBuilder<>(observe, condition);
        }
    }
}
