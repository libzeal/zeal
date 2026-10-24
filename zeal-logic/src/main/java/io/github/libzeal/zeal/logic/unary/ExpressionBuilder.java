package io.github.libzeal.zeal.logic.unary;

import io.github.libzeal.zeal.logic.format.Formatter;
import io.github.libzeal.zeal.logic.unary.PredicateExpression.Field;

import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public final class ExpressionBuilder<T, O> {

    private final Function<T, O> observe;
    private final Predicate<O> condition;
    private boolean nullable = false;
    private String name = "<unnamed>";
    private Field<T, O> expected = context -> "<not set>";
    private Field<T, O> actual = context -> Formatter.stringify(context.observed());
    private Field<T, O> hint = null;

    private ExpressionBuilder(final Function<T, O> observe, final Predicate<O> condition) {
        this.observe = requireNonNull(observe);
        this.condition = requireNonNull(condition);
    }

    public static <T> ExpressionBuilder<T, T> of(final Predicate<T> condition) {
        return new ExpressionBuilder<>(Function.identity(), condition);
    }

    public static <T, O> Observing<T, O> observing(final Function<T, O> observe) {
        return new Observing<>(observe);
    }

    public ExpressionBuilder<T, O> nullable() {
        this.nullable = true;
        return this;
    }

    public ExpressionBuilder<T, O> name(final String name) {
        this.name = requireNonNull(name);
        return this;
    }

    public ExpressionBuilder<T, O> expected(final Field<T, O> expected) {
        this.expected = requireNonNull(expected);
        return this;
    }

    public ExpressionBuilder<T, O> expected(final String expected) {
        return expected(context -> expected);
    }

    public ExpressionBuilder<T, O> expected(final long expected) {
        return expected(context -> String.valueOf(expected));
    }

    public ExpressionBuilder<T, O> actual(final Field<T, O> actual) {
        this.actual = requireNonNull(actual);
        return this;
    }

    public ExpressionBuilder<T, O> actual(final String actual) {
        return actual(context -> actual);
    }

    public ExpressionBuilder<T, O> hint(final Field<T, O> hint) {
        this.hint = hint;
        return this;
    }

    public ExpressionBuilder<T, O> hint(final String hint) {
        return hint(context -> hint);
    }

    public ComputableExpression<T> build() {

        final String name = this.name;
        final boolean nullable = this.nullable;
        final Field<T, O> expected = this.expected;
        final Field<T, O> actual = this.actual;
        final Field<T, O> hint = this.hint;

        return subject -> new PredicateExpression<>(name, subject, nullable, observe, condition, expected, actual,
            hint);
    }

    public record Observing<T, O>(Function<T, O> observe) {

        public Observing {
            requireNonNull(observe);
        }

        public ExpressionBuilder<T, O> passesIf(final Predicate<O> condition) {
            return new ExpressionBuilder<>(observe, condition);
        }
    }
}
