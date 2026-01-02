package io.github.libzeal.zeal.values.api;

import io.github.libzeal.zeal.logic.unary.future.ComputableExpression;
import io.github.libzeal.zeal.logic.unary.future.*;
import io.github.libzeal.zeal.logic.unary.*;
import io.github.libzeal.zeal.logic.unary.future.rationale.ComputableField;
import io.github.libzeal.zeal.logic.unary.future.rationale.ComputableRationale;
import io.github.libzeal.zeal.logic.unary.future.rationale.SimpleComputableRationale;
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
public class SimpleValueBuilder<T, E extends BaseObjectValue<T, E>> implements ValueBuilder<T> {

    private final boolean nullable;
    private final Predicate<T> test;
    private String name = "<unnamed>";
    private ComputableField<T> expected = (s, passed) -> "<not set>";
    private ComputableField<T> actual = (s, passed) -> Formatter.stringify(s);
    private ComputableField<T> hint = null;

    /**
     * Creates a builder for expressions that can accept a null subject.
     *
     * @param test
     *     The predicate.
     * @param <T>
     *     The type of the subject.
     * @param <E>
     *     The type of the expression.
     *
     * @return The builder.
     */
    public static <T, E extends BaseObjectValue<T, E>> SimpleValueBuilder<T, E> notNullable(final Predicate<T> test) {
        return new SimpleValueBuilder<>(false, test);
    }

    /**
     * Creates a builder for expressions that cannot accept a null subject.
     *
     * @param test
     *     The predicate.
     * @param <T>
     *     The type of the subject.
     * @param <E>
     *     The type of the expression.
     *
     * @return The builder.
     */
    public static <T, E extends BaseObjectValue<T, E>> SimpleValueBuilder<T, E> nullable(final Predicate<T> test) {
        return new SimpleValueBuilder<>(true, test);
    }

    /**
     * Creates a new builder.
     *
     * @param nullable
     *     A flag denoting if the predicate can be nullable.
     * @param test
     *     The predicate (test) to evaluate.
     */
    private SimpleValueBuilder(final boolean nullable,
                               final Predicate<T> test) {
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
    public SimpleValueBuilder<T, E> name(final String name) {
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
    public SimpleValueBuilder<T, E> expected(final ComputableField<T> expected) {
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
    public SimpleValueBuilder<T, E> expected(final String expected) {
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
    public SimpleValueBuilder<T, E> expected(final long expected) {
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
    public SimpleValueBuilder<T, E> actual(final ComputableField<T> actual) {
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
    public SimpleValueBuilder<T, E> actual(final String actual) {
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
    public SimpleValueBuilder<T, E> hint(final ComputableField<T> hint) {
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
    public SimpleValueBuilder<T, E> hint(final String hint) {
        return hint((s, passed) -> hint);
    }

    @Override
    public ComputableExpression<T> build() {

        final ComputableRationale<T> computableRationale = new SimpleComputableRationale<>(expected, actual, hint);
        final Predicate<T> predicate = predicate();

        return new SimpleComputableExpression<>(name, predicate, computableRationale);
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
