package io.github.libzeal.zeal.values.sdk;

import io.github.libzeal.zeal.logic.AndExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.evaluation.CompoundEvaluation;
import io.github.libzeal.zeal.logic.unary.ComputableExpression;
import io.github.libzeal.zeal.logic.unary.ExpressionBuilder;
import io.github.libzeal.zeal.logic.unary.ExpressionBuilder.Observing;
import io.github.libzeal.zeal.logic.unary.condition.Condition;
import io.github.libzeal.zeal.values.core.ObjectValue;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.github.libzeal.zeal.logic.unary.condition.Conditions.equalTo;
import static io.github.libzeal.zeal.logic.unary.condition.Conditions.exactly;
import static io.github.libzeal.zeal.logic.format.Formatter.stringify;
import static io.github.libzeal.zeal.values.sdk.StandardMessages.PREDICATE_SATISFIED;
import static io.github.libzeal.zeal.values.sdk.StandardMessages.PREDICATE_UNSATISFIED;
import static java.util.Objects.requireNonNull;

/**
 * The abstract base class for all Object-based (non-primitive) expressions.
 * <p>
 * This base class should be extended when creating expressions for any
 * specialized class. For example, if creating a {@code StringExpression} class,
 * the {@code StringExpression} class should extend this class, with {@code T}
 * set to {@link String}. An example of a proper extension of this class (to
 * create a {@code StringExpression} for example) would be:
 * <pre><code>class StringExpression extends ObjectExpression&lt;String,
 * StringExpression&gt;</code></pre>
 *
 * @param <T>
 *     The type of the subject.
 * @param <E>
 *     The type of the subclass. This type is an example of the
 *     <a href="https://stackoverflow.com/q/4173254/2403253">Curiously Recurring
 *     Template Pattern (CRTP)</a>
 *     . This type allows for the fluent interface methods of this class to
 *     return the subtype object, rather than {@link BaseValue}. If
 *     {@link BaseValue} were returned from the fluent interface methods, then
 *     the subclass type would be lost, resulting in the methods available on
 *     the subtype to no longer be available as methods from {@link BaseValue}
 *     are called. For example, if a {@code StringExpression} (extending this
 *     class) had a method called {@code includesCharacter(char)}, the following
 *     would cause an error:
 *     <pre><code>new
 *     StringExpression("foo").isNotNull().includeCharacter('c');</code></pre>
 *     Since the {@code isNotNull()} method would return a {@link BaseValue}
 *     object, rather than a {@code StringExpression}, thus hiding the
 *     {@code includesCharacter(char)} method of {@code  StringExpression}.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
public abstract class BaseValue<T, E extends BaseValue<T, E>> implements ObjectValue<T, E> {

    private final String name;
    private final T subject;
    private final UnaryExpressionChain<T> chain;

    /**
     * Creates an object expression with the supplied subject. This constructor
     * uses a default name for the expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    protected BaseValue(final T subject) {
        this(subject, "Object[" + stringify(subject) + "] value");
    }

    /**
     * Creates an object expression with the supplied subject and name.
     *
     * @param subject
     *     The subject of the expression.
     * @param name
     *     The name of the expression.
     */
    protected BaseValue(final T subject, final String name) {
        this.name = requireNonNull(name) + ": " + stringify(subject);
        this.subject = subject;
        this.chain = new UnaryExpressionChain<>();
    }

    @SuppressWarnings("unchecked")
    protected final E append(final ComputableExpression<T> expression) {

        chain.append(expression);

        return (E) this;
    }

    @SuppressWarnings("unchecked")
    protected final E prepend(final ComputableExpression<T> expression) {

        chain.prepend(expression);

        return (E) this;
    }

    @Override
    public final T subject() {
        return subject;
    }

    @Override
    public final String name() {
        return name;
    }

    @Override
    public final List<Expression> children() {
        return chain.bind(subject);
    }

    @Override
    public final CompoundEvaluation evaluate() {
        return new AndExpression(name, children()).evaluate();
    }

    /**
     * Creates a builder for a new expression.
     *
     * @param test
     *     The predicate (test) for the expression. The supplied predicate can
     *     assume that the subject will never be {@code null} (a {@code null}
     *     check will be performed before the predicate is evaluated.
     *
     * @return The expression builder.
     *
     * @throws NullPointerException
     *     The supplied test was {@code null}.
     */
    protected final ExpressionBuilder<T, T> expression(final Predicate<T> test) {
        return ExpressionBuilder.of(test);
    }

    /**
     * Creates a builder for a new expression, where the supplied test may
     * receive a {@code null} subject.
     * <p>
     * For example, if the supplied test is <code>subject, passed) -&gt;
     * subject.toString().equals("hi")</code>, the value of {@code o} may be
     * {@code null} and should be handled accordingly.
     *
     * @param test
     *     The predicate (test) for the expression. The subject supplied to the
     *     predicate <em>may be {@code null}</em>.
     *
     * @return The expression builder.
     *
     * @throws NullPointerException
     *     The supplied test was {@code null}.
     */
    protected final ExpressionBuilder<T, T> nullableExpression(final Predicate<T> test) {
        return ExpressionBuilder.of(test).nullable();
    }

    /**
     * Creates a builder for a new expression that tests a value observed from
     * the subject (such as its size), rather than the subject itself. The
     * observed value is computed once and is available to the rationale.
     *
     * @param observe
     *     The function that observes the tested value from the subject. The
     *     supplied function can assume that the subject will never be
     *     {@code null} (a {@code null} check will be performed before the
     *     function is applied).
     *
     * @return The first step of the expression builder, which accepts the
     * condition for the observed value.
     *
     * @throws NullPointerException
     *     The supplied function was {@code null}.
     */
    protected final <O> Observing<T, O> observing(final Function<T, O> observe) {
        return ExpressionBuilder.observing(observe);
    }

    private ExpressionBuilder<T, Class<?>> typeExpression(final Predicate<Class<?>> condition) {
        return ExpressionBuilder.<T, Class<?>>observing(Object::getClass).passesIf(condition);
    }

    @Override
    public E isNull() {
        return append(
            nullableExpression(Objects::isNull)
                .name("isNull")
                .expected("(null)")
                .build()
        );
    }

    @Override
    public final E isNotNull() {
        return prepend(
            nullableExpression(Objects::nonNull)
                .name("isNotNull")
                .expected("not[(null)]")
                .build()
        );
    }

    @Override
    public E isType(final Class<?> type) {

        final ExpressionBuilder<T, Class<?>> builder = typeExpression(c -> c.equals(type));

        if (type == null) {
            builder.name("isType[(null)]")
                .expected(StandardMessages.ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE)
                .hint(StandardMessages.EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE);
        }
        else {
            builder.name("isType[" + type + "]")
                .expected(type.toString())
                .hint("Actual should be exactly of type " + type);
        }

        return append(
            builder.build()
        );
    }

    @Override
    public E isNotType(final Class<?> type) {

        final ExpressionBuilder<T, Class<?>> builder = typeExpression(c -> type != null && !c.equals(type));

        if (type == null) {
            builder.name("isNotType[(null)]")
                .expected(StandardMessages.ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE)
                .hint(StandardMessages.EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE);
        }
        else {
            builder.name("isNotType[" + type + "]")
                .expected("not[" + type + "]")
                .hint("Actual should be any type other than " + type);
        }

        return append(
            builder.build()
        );
    }

    @Override
    public E isInstanceOf(final Class<?> type) {

        final ExpressionBuilder<T, Class<?>> builder = typeExpression(c -> type != null && type.isAssignableFrom(c));

        if (type == null) {
            builder.name("isInstanceOf[(null)]")
                .expected(StandardMessages.ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE)
                .hint(StandardMessages.EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE);
        }
        else {
            builder.name("isInstanceOf[" + type + "]")
                .expected("instanceof[" + type + "]")
                .hint("Actual should be exactly of type " + type + " or a subtype of type " + type);
        }

        return append(
            builder.build()
        );
    }

    @Override
    public E isNotInstanceOf(final Class<?> type) {

        final ExpressionBuilder<T, Class<?>> builder = typeExpression(c -> type != null && !type.isAssignableFrom(c));

        if (type == null) {
            builder.name("isNotInstanceOf[(null)]")
                .expected(StandardMessages.ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE)
                .hint(StandardMessages.EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE);
        }
        else {
            builder.name("isNotInstanceOf[" + type + "]")
                .expected("not[instanceof[" + type + "]]")
                .hint("Actual should be any type other than " + type + " and not a subtype of type " + type);
        }

        return append(
            builder.build()
        );
    }

    @Override
    public E isExactly(final Object other) {
        return append(exactly(other));
    }

    @Override
    public E isNotExactly(final Object other) {
        return append(
            nullableExpression(s -> s != other)
                .name("isNotExactly[" + stringify(other) + "]")
                .expected("not[" + stringify(other) + "]")
                .hint("Actual should not be identical to " + other + " (using !=)")
                .build()
        );
    }

    @Override
    public E is(final Condition<T> condition) {

        if (condition == null) {
            return append(
                expression(s -> false)
                    .name("is[condition: (null)]")
                    .expected(StandardMessages.ALWAYS_FAIL_CANNOT_CHECK_TO_NULL_CONDITION)
                    .hint(StandardMessages.CANNOT_CHECK_USING_NULL_CONDITION)
                    .build()
            );
        }
        else {
            return append(condition);
        }
    }

    @Override
    public E isEqualTo(final Object other) {
        return append(equalTo(other));
    }

    @Override
    public E isEqualTo(final T other, final Comparator<T> comparator) {
        return append(
            comparableEquals(other, comparator)
                .name("isEqualTo[" + stringify(other) + ", comparator: " + stringify(comparator) + "]")
                .expected(stringify(other))
                .build()
        );
    }

    @Override
    public E isNotEqualTo(final Object other) {
        return append(
            nullableExpression(s -> !Objects.equals(s, other))
                .name("isNotEqualTo[" + stringify(other) + "]")
                .expected("not[" + stringify(other) + "]")
                .hint("Actual should be equal to " + other + " (using !subject.equals(" + other + "))")
                .build()
        );
    }

    @Override
    public E isNotEqualTo(final T other, final Comparator<T> comparator) {
        return append(
            comparableNotEquals(other, comparator)
                .name("isNotEqualTo[" + stringify(other) + ", comparator: " + stringify(comparator) + "]")
                .expected(stringify(other))
                .build()
        );
    }

    @Override
    public E hashCodeIs(final int hashCode) {
        return append(
            observing(Object::hashCode)
                .passesIf(h -> h == hashCode)
                .name("hashCode == " + hashCode)
                .expected(hashCode)
                .build()
        );
    }

    @Override
    public E hashCodeIsNot(final int hashCode) {
        return append(
            observing(Object::hashCode)
                .passesIf(h -> h != hashCode)
                .name("hashCode != " + hashCode)
                .expected("not[" + hashCode + "]")
                .build()
        );
    }

    @Override
    public E toStringIs(final String expected) {
        return append(
            expression(s -> s.toString().equals(expected))
                .name("toString().equals(" + expected + ")")
                .expected(expected)
                .hint("Subject's toString() value should equal " + expected + " (using subject.toString().equals(" + expected + "))")
                .build()
        );
    }

    @Override
    public E toStringIsNot(final String expected) {
        return append(
            expression(s -> !s.toString().equals(expected))
                .name("not[toString().equals(" + expected + ")]")
                .expected("not[" + expected + "]")
                .hint("Subject's toString() value should not equal " + expected + " (using !subject.toString().equals(" + expected + "))")
                .build()
        );
    }

    @Override
    public E satisfies(final Predicate<T> predicate) {
        return append(
            expression(predicate)
                .name("predicate")
                .expected(StandardMessages.PREDICATE_SATISFIED)
                .actual(context -> context.ifPassedOrElse(PREDICATE_SATISFIED, PREDICATE_UNSATISFIED))
                .build()
        );
    }

    @Override
    public E doesNotSatisfy(final Predicate<T> predicate) {
        return append(
            expression(o -> !predicate.test(o))
                .name("not[predicate]")
                .expected(StandardMessages.PREDICATE_UNSATISFIED)
                .actual(context -> context.ifPassedOrElse(PREDICATE_UNSATISFIED, PREDICATE_SATISFIED))
                .build()
        );
    }
}