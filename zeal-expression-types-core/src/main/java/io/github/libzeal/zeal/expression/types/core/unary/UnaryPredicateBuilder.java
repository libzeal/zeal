package io.github.libzeal.zeal.expression.types.core.unary;

import io.github.libzeal.zeal.expression.lang.predicate.RationaleGenerator;
import io.github.libzeal.zeal.expression.lang.predicate.SimpleRationaleGenerator;
import io.github.libzeal.zeal.expression.lang.predicate.ValueSupplier;
import io.github.libzeal.zeal.expression.lang.predicate.unary.TerminalUnaryPredicate;
import io.github.libzeal.zeal.expression.lang.predicate.unary.UnaryPredicate;

import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * A builder used to create {@link UnaryPredicate} objects.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class UnaryPredicateBuilder<T, E extends ObjectUnaryExpression<T, E>> {

    private final BuildableExpression<T, E> parent;
    private final boolean nullable;
    private final Predicate<T> test;
    private String name = "<unnamed>";
    private ValueSupplier<T> expected = s -> "<not set>";
    private ValueSupplier<T> actual = ObjectUnaryExpression::stringify;
    private ValueSupplier<T> hint = null;

    public static <T, E extends ObjectUnaryExpression<T, E>> UnaryPredicateBuilder<T, E> notNullable(final BuildableExpression<T, E> parent,
                                                          final Predicate<T> test) {
        return new UnaryPredicateBuilder<>(parent, false, test);
    }

    public static <T, E extends ObjectUnaryExpression<T, E>> UnaryPredicateBuilder<T, E> nullable(final BuildableExpression<T, E> parent,
                                                                                                     final Predicate<T> test) {
        return new UnaryPredicateBuilder<>(parent, true, test);
    }

    /**
     * Creates a new builder.
     *
     * @param parent
     *     The parent of the builder where predicates will be prepended and appended.
     * @param nullable
     *     A flag denoting if the predicate can be nullable.
     * @param test
     *     The predicate (test) to evaluate.
     */
    private UnaryPredicateBuilder(final BuildableExpression<T, E> parent, final boolean nullable,
                                    final Predicate<T> test) {
        this.parent = parent;
        this.nullable = nullable;
        this.test = test;
    }

    /**
     * Sets the name of the evaluation.
     *
     * @param name
     *     The name of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the expected valued of the evaluation.
     *
     * @param expected
     *     The expected value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> expectedValue(ValueSupplier<T> expected) {
        this.expected = expected;
        return this;
    }

    /**
     * Sets the expected valued of the evaluation.
     *
     * @param expected
     *     The expected value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> expectedValue(String expected) {
        return expectedValue(ValueSupplier.of(expected));
    }

    /**
     * Sets the expected valued of the evaluation.
     *
     * @param expected
     *     The expected value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> expectedIntValue(ToIntFunction<T> expected) {
        this.expected = s -> String.valueOf(expected.applyAsInt(s));
        return this;
    }

    /**
     * Sets the expected valued of the evaluation.
     *
     * @param expected
     *     The expected value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> expectedLongValue(ToLongFunction<T> expected) {
        this.expected = s -> String.valueOf(expected.applyAsLong(s));
        return this;
    }

    /**
     * Sets the expected valued of the evaluation.
     *
     * @param expected
     *     The expected value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> expectedDoubleValue(ToDoubleFunction<T> expected) {
        this.expected = s -> String.valueOf(expected.applyAsDouble(s));
        return this;
    }

    /**
     * Sets the expected valued of the evaluation.
     *
     * @param expected
     *     The expected value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> expectedBooleanValue(Predicate<T> expected) {
        this.expected = s -> String.valueOf(expected.test(s));
        return this;
    }

    /**
     * Sets the expected valued of the evaluation.
     *
     * @param expected
     *     The expected value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> expectedIntValue(int expected) {
        this.expected = s -> String.valueOf(expected);
        return this;
    }

    /**
     * Sets the expected valued of the evaluation.
     *
     * @param expected
     *     The expected value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> expectedLongValue(long expected) {
        this.expected = s -> String.valueOf(expected);
        return this;
    }

    /**
     * Sets the expected valued of the evaluation.
     *
     * @param expected
     *     The expected value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> expectedDoubleValue(final double expected) {
        this.expected = s -> String.valueOf(expected);
        return this;
    }

    /**
     * Sets the expected valued of the evaluation.
     *
     * @param expected
     *     The expected value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> expectedBooleanValue(final boolean expected) {
        this.expected = s -> String.valueOf(expected);
        return this;
    }

    /**
     * Sets the actual valued of the evaluation.
     *
     * @param actual
     *     The actual value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> actualValue(ValueSupplier<T> actual) {
        this.actual = actual;
        return this;
    }

    /**
     * Sets the actual valued of the evaluation.
     *
     * @param actual
     *     The actual value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> actualIntValue(ToIntFunction<T> actual) {
        this.actual = s -> String.valueOf(actual.applyAsInt(s));
        return this;
    }

    /**
     * Sets the actual valued of the evaluation.
     *
     * @param actual
     *     The actual value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> actualLongValue(ToLongFunction<T> actual) {
        this.actual = s -> String.valueOf(actual.applyAsLong(s));
        return this;
    }

    /**
     * Sets the actual valued of the evaluation.
     *
     * @param actual
     *     The actual value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> actualDoubleValue(ToDoubleFunction<T> actual) {
        this.actual = s -> String.valueOf(actual.applyAsDouble(s));
        return this;
    }

    /**
     * Sets the actual valued of the evaluation.
     *
     * @param actual
     *     The actual value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> actualBooleanValue(Predicate<T> actual) {
        this.actual = s -> String.valueOf(actual.test(s));
        return this;
    }

    /**
     * Sets the actual valued of the evaluation.
     *
     * @param actual
     *     The actual value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> actualIntValue(int actual) {
        this.actual = s -> String.valueOf(actual);
        return this;
    }

    /**
     * Sets the actual valued of the evaluation.
     *
     * @param actual
     *     The actual value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> actualLongValue(long actual) {
        this.actual = s -> String.valueOf(actual);
        return this;
    }

    /**
     * Sets the actual valued of the evaluation.
     *
     * @param actual
     *     The actual value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> actualDoubleValue(final double actual) {
        this.actual = s -> String.valueOf(actual);
        return this;
    }

    /**
     * Sets the actual valued of the evaluation.
     *
     * @param actual
     *     The actual value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> actualBooleanValue(final boolean actual) {
        this.actual = s -> String.valueOf(actual);
        return this;
    }

    /**
     * Sets the hint for the evaluation.
     *
     * @param hint
     *     The hint for the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> hint(final ValueSupplier<T> hint) {
        this.hint = hint;
        return this;
    }

    /**
     * Sets the hint for the evaluation.
     *
     * @param hint
     *     The hint for the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public UnaryPredicateBuilder<T, E> hint(final String hint) {
        return hint(ValueSupplier.of(hint));
    }

    /**
     * Appends the evaluation being built to the evaluation chain.
     *
     * @return This builder (fluent interface).
     */
    public E append() {
        return parent.append(build());
    }

    E prepend() {
        return parent.prepend(build());
    }

    private UnaryPredicate<T> build() {

        final RationaleGenerator<T> rationaleGenerator = new SimpleRationaleGenerator<>(expected, actual, hint);

        if (nullable) {
            return TerminalUnaryPredicate.ofNullable(name, test, rationaleGenerator);
        }
        else {
            return TerminalUnaryPredicate.of(name, test, rationaleGenerator);
        }
    }

    public interface BuildableExpression<T, E> {
        E prepend(UnaryPredicate<T> predicate);

        E append(UnaryPredicate<T> predicate);
    }
}
