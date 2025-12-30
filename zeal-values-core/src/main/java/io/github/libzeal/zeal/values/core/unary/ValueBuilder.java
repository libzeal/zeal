package io.github.libzeal.zeal.values.core.unary;

import io.github.libzeal.zeal.logic.unary.*;
import io.github.libzeal.zeal.logic.util.Formatter;

import java.util.function.Predicate;

/**
 * A builder used to create {@link UnaryExpression} objects.
 *
 * @param <T>
 *     The type of the subject.
 * @param <E>
 *     The type of the expression.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class ValueBuilder<T, E extends ObjectValue<T, E>> {

    private final T subject;
    private final boolean nullable;
    private final Predicate<T> test;
    private String name = "<unnamed>";
    private ValueSupplier<T> expected = (s, passed) -> "<not set>";
    private ValueSupplier<T> actual = (s, passed) -> Formatter.stringify(s);
    private ValueSupplier<T> hint = null;

    /**
     * Creates a builder for expressions that can accept a null subject.
     *
     * @param subject
     *     The subject of the expression.
     * @param test
     *     The predicate.
     * @param <T>
     *     The type of the subject.
     * @param <E>
     *     The type of the expression.
     *
     * @return The builder.
     */
    public static <T, E extends ObjectValue<T, E>> ValueBuilder<T, E> notNullable(final T subject,
                                                                                  final Predicate<T> test) {
        return new ValueBuilder<>(subject, false, test);
    }

    /**
     * Creates a builder for expressions that cannot accept a null subject.
     *
     * @param subject
     *     The subject of the expression.
     * @param test
     *     The predicate.
     * @param <T>
     *     The type of the subject.
     * @param <E>
     *     The type of the expression.
     *
     * @return The builder.
     */
    public static <T, E extends ObjectValue<T, E>> ValueBuilder<T, E> nullable(final T subject,
                                                                               final Predicate<T> test) {
        return new ValueBuilder<>(subject, true, test);
    }

    /**
     * Creates a new builder.
     *
     * @param subject
     *     The subject of the expression.
     * @param nullable
     *     A flag denoting if the predicate can be nullable.
     * @param test
     *     The predicate (test) to evaluate.
     */
    private ValueBuilder(final T subject, final boolean nullable,
                         final Predicate<T> test) {
        this.subject = subject;
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
    public ValueBuilder<T, E> name(final String name) {
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
    public ValueBuilder<T, E> expected(final ValueSupplier<T> expected) {
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
    public ValueBuilder<T, E> expected(final String expected) {
        return expected((s, passed) -> expected);
    }

    /**
     * Sets the expected valued of the evaluation.
     *
     * @param expected
     *     The expected value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public ValueBuilder<T, E> expected(final long expected) {
        return expected((s, passed) -> String.valueOf(expected));
    }

    /**
     * Sets the actual valued of the evaluation.
     *
     * @param actual
     *     The actual value of the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public ValueBuilder<T, E> actual(final ValueSupplier<T> actual) {
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
    public ValueBuilder<T, E> actual(final String actual) {
        return actual((s, passed) -> actual);
    }

    /**
     * Sets the hint for the evaluation.
     *
     * @param hint
     *     The hint for the evaluation.
     *
     * @return This builder (fluent interface).
     */
    public ValueBuilder<T, E> hint(final ValueSupplier<T> hint) {
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
    public ValueBuilder<T, E> hint(final String hint) {
        return hint((s, passed) -> hint);
    }

    public UnaryExpression<T> build() {

        final RationaleGenerator<T> rationaleGenerator = new SimpleRationaleGenerator<>(expected, actual, hint);
        final Predicate<T> predicate = predicate();

        return TerminalUnaryExpression.of(name, subject, predicate, rationaleGenerator);
    }

    private Predicate<T> predicate() {

        if (nullable) {
            return test;
        }
        else {
            return s -> s != null && test.test(s);
        }
    }
}
