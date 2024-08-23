package io.github.libzeal.zeal.types.core.unary;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.rationale.RationaleGenerator;
import io.github.libzeal.zeal.logic.rationale.SimpleRationaleGenerator;
import io.github.libzeal.zeal.logic.rationale.ValueSupplier;
import io.github.libzeal.zeal.logic.unary.TerminalUnaryExpression;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;
import io.github.libzeal.zeal.logic.util.Formatter;

import java.util.function.Predicate;

/**
 * A builder used to create {@link Expression} objects.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class UnaryExpressionBuilder<T, E extends ObjectUnaryExpression<T, E>> {

    private final BuildableExpression<E> parent;
    private final T subject;
    private final boolean nullable;
    private final Predicate<T> test;
    private String name = "<unnamed>";
    private ValueSupplier<T> expected = (s, passed) -> "<not set>";
    private ValueSupplier<T> actual = (s, passed) -> Formatter.stringify(s);
    private ValueSupplier<T> hint = null;

    public static <T, E extends ObjectUnaryExpression<T, E>> UnaryExpressionBuilder<T, E> notNullable(final BuildableExpression<E> parent, final T subject,
                                                                                                      final Predicate<T> test) {
        return new UnaryExpressionBuilder<>(parent, subject, false, test);
    }

    public static <T, E extends ObjectUnaryExpression<T, E>> UnaryExpressionBuilder<T, E> nullable(final BuildableExpression<E> parent, final T subject,
                                                                                                   final Predicate<T> test) {
        return new UnaryExpressionBuilder<>(parent, subject, true, test);
    }

    /**
     * Creates a new builder.
     *
     * @param parent
     *     The parent of the builder where predicates will be prepended and appended.
     * @param subject
     *     The subject of the expression.
     * @param nullable
     *     A flag denoting if the predicate can be nullable.
     * @param test
     *     The predicate (test) to evaluate.
     */
    private UnaryExpressionBuilder(final BuildableExpression<E> parent, final T subject, final boolean nullable,
                                   final Predicate<T> test) {
        this.parent = parent;
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
    public UnaryExpressionBuilder<T, E> name(final String name) {
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
    public UnaryExpressionBuilder<T, E> expected(final ValueSupplier<T> expected) {
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
    public UnaryExpressionBuilder<T, E> expected(final String expected) {
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
    public UnaryExpressionBuilder<T, E> expected(final long expected) {
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
    public UnaryExpressionBuilder<T, E> actual(final ValueSupplier<T> actual) {
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
    public UnaryExpressionBuilder<T, E> actual(final String actual) {
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
    public UnaryExpressionBuilder<T, E> hint(final ValueSupplier<T> hint) {
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
    public UnaryExpressionBuilder<T, E> hint(final String hint) {
        return hint((s, passed) -> hint);
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

    private UnaryExpression<T> build() {

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

    public interface BuildableExpression<E> {
        E prepend(Expression predicate);
        E append(Expression predicate);
    }
}