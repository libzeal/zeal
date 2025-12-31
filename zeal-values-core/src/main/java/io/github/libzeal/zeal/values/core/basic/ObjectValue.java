package io.github.libzeal.zeal.values.core.basic;

import io.github.libzeal.zeal.logic.AndExpression;
import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.condition.Condition;
import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.unary.ValueSupplier;
import io.github.libzeal.zeal.logic.unary.UnaryExpression;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

import static io.github.libzeal.zeal.logic.condition.Conditions.equalTo;
import static io.github.libzeal.zeal.logic.condition.Conditions.exactly;
import static io.github.libzeal.zeal.logic.util.Formatter.stringify;
import static java.util.Objects.requireNonNull;

/**
 * The abstract base class for all Object-based (non-primitive) expressions.
 * <p>
 * This base class should be extended when creating expressions for any specialized class. For example, if creating a
 * {@code StringExpression} class, the {@code StringExpression} class should extend this class, with {@code T} set to
 * {@link String}. An example of a proper extension of this class (to create a {@code StringExpression} for example)
 * would be:
 * <pre><code>class StringExpression extends ObjectExpression&lt;String, StringExpression&gt;</code></pre>
 *
 * @param <T>
 *     The type of the subject.
 * @param <E>
 *     The type of the subclass. This type is an example of the
 *     <a href="https://stackoverflow.com/q/4173254/2403253">Curiously Recurring Template Pattern (CRTP)</a>
 *     . This type allows for the fluent interface methods of this class to return the subtype object, rather than
 *     {@link ObjectValue}. If {@link ObjectValue} were returned from the fluent interface methods,
 *     then the subclass type would be lost, resulting in the methods available on the subtype to no longer be available
 *     as methods from {@link ObjectValue} are called. For example, if a {@code StringExpression} (extending
 *     this class) had a method called {@code includesCharacter(char)}, the following would cause an error:
 *     <pre><code>new StringExpression("foo").isNotNull().includeCharacter('c');</code></pre>
 *     Since the {@code isNotNull()} method would return a {@link ObjectValue} object, rather than a
 *     {@code StringExpression}, thus hiding the {@code includesCharacter(char)} method of {@code  StringExpression}.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public class ObjectValue<T, E extends ObjectValue<T, E>> implements UnaryExpression<T> {

    private static final String PREDICATE_SATISFIED = "Predicate satisfied";
    private static final String PREDICATE_UNSATISFIED = "Predicate unsatisfied";
    private static final String ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE = "Always fail: cannot compare to (null) type";
    private static final String EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE =
        "Evaluation will always fail when compared to a (null) type";
    static final String ALWAYS_FAIL_CANNOT_CHECK_TO_NULL_CONDITION = "Always fail: cannot compare to (null) condition";
    static final String CANNOT_COMPARE_USING_NULL_COMPARATOR =
        "Evaluation will always fail when using (null) comparator";
    static final String CANNOT_CHECK_USING_NULL_CONDITION =
        "Evaluation will always fail when checking a (null) condition";

    private final String name;
    private final T subject;
    private final AndExpression children;

    /**
     * Creates an object expression with the supplied subject. This constructor uses a default name for the expression.
     *
     * @param subject
     *     The subject of the expression.
     */
    protected ObjectValue(T subject) {
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
    protected ObjectValue(T subject, String name) {
        this.name = requireNonNull(name);
        this.subject = subject;
        this.children = new AndExpression(name);
    }

    @SuppressWarnings("unchecked")
    private E append(final Expression expression) {

        children.append(expression);

        return (E) ObjectValue.this;
    }

    @SuppressWarnings("unchecked")
    protected final E append(final ValueBuilder<T, E> builder) {

        final UnaryExpression<T> expression = builder.build();

        children.append(expression);

        return (E) ObjectValue.this;
    }

    @SuppressWarnings("unchecked")
    private E prepend(final ValueBuilder<T, E> builder) {

        final UnaryExpression<T> expression = builder.build();

        children.prepend(expression);

        return (E) ObjectValue.this;
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
    public final Evaluation evaluate() {
        return children.evaluate();
    }

    /**
     * Creates a builder for a new evaluation.
     *
     * @param test
     *     The predicate (test) for the evaluation. The supplied predicate can assume that the subject will never be
     *     {@code null} (a {@code null} check will be performed before the predicate is evaluated.
     *
     * @return The evaluation builder.
     *
     * @throws NullPointerException
     *     The supplied test was {@code null}.
     */
    protected final ValueBuilder<T, E> expression(Predicate<T> test) {
        return ValueBuilder.notNullable(subject, requireNonNull(test));
    }

    /**
     * Creates a builder for a new evaluation, where the supplied test may receive a {@code null} subject.
     * <p>
     * For example, if the supplied test is <code>subject, passed) -&gt; subject.toString().equals("hi")</code>, the
     * value of {@code o} may be {@code null} and should be handled accordingly.
     *
     * @param test
     *     The predicate (test) for the evaluation. The subject supplied to the predicate <em>may be {@code null}</em>.
     *
     * @return The evaluation builder.
     *
     * @throws NullPointerException
     *     The supplied test was {@code null}.
     */
    private final ValueBuilder<T, E> nullableExpression(Predicate<T> test) {
        return ValueBuilder.nullable(subject, requireNonNull(test));
    }

    /**
     * Adds a predicate to the expression that checks if the subject is {@code null}.
     *
     * @return This expression (fluent interface).
     */
    public E isNull() {
        return append(
            nullableExpression(Objects::isNull)
                .name("isNull")
                .expected("(null)")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not {@code null}.
     * <p>
     * Note: This evaluation takes precedent over all other evaluations. For example, if the chain
     * <code>expression.someFooCheck().isNotNull()</code> were created, the {@code #isNotNull()} evaluation would
     * be evaluated before the {@code someFooCheck()} evaluation when the expression is evaluated (using the
     * {@link #evaluate()} method), even though it was second in the chain.
     * <p>
     * Note: The precedence of this evaluation is due to the nature of evaluations on objects. Every evaluation, expect
     * for nullity evaluations (such as "is @{code null}" or "is not {@code null}") or equality checks assume that the
     * subject of the evaluation is not {@code null}. For example, assume the following chain is called:
     * <pre><code>expression.toStringIs("foo").isNotNull()</code></pre>
     * <p>
     * If the subject of the expression is {@code null}, then the {@code toStringIs("foo")} evaluation will fail, but
     * not because the {@link Object#toString()} value of the expression is not equal to {@code "foo"}, but because the
     * subject of the expression is {@code null} (the {@link Object#toString} method could not be called on the subject,
     * or else a {@link NullPointerException} would be thrown).
     * <p>
     * Therefore, if an expression has a {@code #isNotNull()} evaluation, this evaluation is automatically evaluated
     * first. If the expression does not include {@code #isNotNull()}, then the evaluations are evaluated in the order
     * in which they are chained on the expression.
     *
     * @return This expression (fluent interface).
     */
    public final E isNotNull() {
        return prepend(
            nullableExpression(Objects::nonNull)
                .name("isNotNull")
                .expected("not[(null)]")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject exactly matches the supplied type.
     * <p>
     * Note: If the subject is a subtype of the supplied type, this check will fail.
     *
     * @param type
     *     The type to test the subject against. This evaluation will always fail if the supplied type is {@code null}
     *
     * @return This expression (fluent interface).
     */
    public E isType(final Class<?> type) {

        final ValueBuilder<T, E> builder = expression(s -> s.getClass().equals(type));

        if (type == null) {
            builder.name("isType[(null)]")
                .expected(ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE)
                .hint(EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE);
        }
        else {
            builder.name("isType[" + type + "]")
                .expected(type.toString())
                .hint("Actual should be exactly of type " + type);
        }

        return append(
            builder.actual((s, passed) -> s.getClass().toString())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not an exact match of the supplied type.
     *
     * @param type
     *     The type to test the subject against. This evaluation will always fail if the supplied type is {@code null}.
     *
     * @return This expression (fluent interface).
     */
    public E isNotType(final Class<?> type) {

        final ValueBuilder<T, E> builder = expression(o -> type != null && !o.getClass().equals(type));

        if (type == null) {
            builder.name("isNotType[(null)]")
                .expected(ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE)
                .hint(EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE);
        }
        else {
            builder.name("isNotType[" + type + "]")
                .expected("not[" + type + "]")
                .hint("Actual should be any type other than " + type);
        }

        return append(
            builder.actual((s, passed) -> s.getClass().toString())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject matches or is a subtype of the supplied type.
     * <p>
     * Note: This check is equivalent to the following statement, where {@code type} is the supplied type and
     * {@code subject} is the subject of the expression:
     * <pre><code>
     * type.isAssignableFrom(subject.getClass())
     * </code></pre>
     *
     * @param type
     *     The type to test the subject against. This evaluation will always fail if the supplied type is {@code null}.
     *
     * @return This expression (fluent interface).
     *
     * @see Class#isAssignableFrom(Class)
     */
    public E isInstanceOf(final Class<?> type) {

        final ValueBuilder<T, E> builder = expression(s -> type != null && type.isAssignableFrom(s.getClass()));

        if (type == null) {
            builder.name("isInstanceOf[(null)]")
                .expected(ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE)
                .hint(EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE);
        }
        else {
            builder.name("isInstanceOf[" + type + "]")
                .expected("instanceof[" + type + "]")
                .hint("Actual should be exactly of type " + type + " or a subtype of type " + type);
        }

        return append(
            builder.actual((s, passed) -> s.getClass().toString())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject does not exactly match or is not a subtype of the
     * supplied type.
     * <p>
     * Note: This check is equivalent to the following statement, where {@code type} is the supplied type and
     * {@code subject} is the subject of the expression:
     * <pre><code>
     * !type.isAssignableFrom(subject.getClass())
     * </code></pre>
     *
     * @param type
     *     The type to test the subject against. This evaluation will always fail if the supplied type is {@code null}.
     *
     * @return This expression (fluent interface).
     *
     * @see Class#isAssignableFrom(Class)
     */
    public E isNotInstanceOf(final Class<?> type) {

        final ValueBuilder<T, E> builder = expression(s -> type != null && !type.isAssignableFrom(s.getClass()));

        if (type == null) {
            builder.name("isNotInstanceOf[(null)]")
                .expected(ALWAYS_FAIL_CANNOT_COMPARE_TO_NULL_TYPE)
                .hint(EVALUATION_WILL_ALWAYS_FAIL_WHEN_COMPARED_TO_A_NULL_TYPE);
        }
        else {
            builder.name("isNotInstanceOf[" + type + "]")
                .expected("not[instanceof[" + type + "]]")
                .hint("Actual should be any type other than " + type + " and not a subtype of type " + type);
        }

        return append(
            builder.actual((s, passed) -> s.getClass().toString())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is the same as the supplied object.
     * <p>
     * This evaluation will pass if the subject and supplied object are both {@code null}.
     * <p>
     * Note: This check is equivalent to the following statement, where {@code other} is the supplied object and
     * {@code subject} is the subject of the expression:
     * <pre><code>
     * subject == object
     * </code></pre>
     *
     * @param other
     *     The object to test against.
     *
     * @return This expression (fluent interface).
     */
    public E isExactly(final Object other) {
        return append(exactly(other).create(subject));
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not the same as the supplied object.
     * <p>
     * This evaluation will fail if the subject and supplied object are both {@code null}.
     * <p>
     * Note: This check is equivalent to the following statement, where {@code other} is the supplied object and
     * {@code subject} is the subject of the expression:
     * <pre><code>
     * subject != object
     * </code></pre>
     *
     * @param other
     *     The object to test against.
     *
     * @return This expression (fluent interface).
     */
    public E isNotExactly(final Object other) {
        return append(
            nullableExpression(s -> s != other)
                .name("isNotExactly[" + stringify(other) + "]")
                .expected("not[" + stringify(other) + "]")
                .hint("Actual should not be identical to " + other + " (using !=)")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject satisfies the supplied condition.
     *
     * @param condition
     *     The condition to check.
     *
     * @return This expression (fluent interface).
     */
    public E is(final Condition<T> condition) {

        if (condition == null) {
            return append(
                expression(s -> false)
                    .name("is[condition: (null)]")
                    .expected(ALWAYS_FAIL_CANNOT_CHECK_TO_NULL_CONDITION)
                    .hint(CANNOT_CHECK_USING_NULL_CONDITION)
            );
        }
        else {
            return append(condition.create(subject));
        }
    }

    /**
     * Adds a predicate to the expression that checks if the subject is equal to the supplied object.
     * <p>
     * This evaluation will pass if the subject and supplied object are both {@code null}.
     * <p>
     * Note: This check is equivalent to the following statement, where {@code other} is the supplied object and
     * {@code subject} is the subject of the expression:
     * <pre><code>
     * Objects.equals(subject, other);
     * </code></pre>
     *
     * @param other
     *     The object to test against.
     *
     * @return This expression (fluent interface).
     *
     * @see Objects#equals(Object, Object)
     */
    public E isEqualTo(final Object other) {
        return append(equalTo(other).create(subject));
    }

    /**
     * Adds a predicate to the expression that checks if the subject is equal to the supplied object using the supplied
     * comparator.
     * <p>
     * This evaluation will fail if the supplied comparator is {@code null}. If the comparator is not {@code null}, this
     * evaluation will pass if the subject and supplied object are both {@code null}.
     *
     * @param other
     *     The object to test against.
     * @param comparator
     *     The {@link Comparator} to use when checking the equality of the supplied object and the subject.
     *
     * @return This expression (fluent interface).
     *
     * @see Comparator#compare(Object, Object)
     */
    public E isEqualTo(final T other, final Comparator<T> comparator) {
        return append(
            comparableEquals(other, comparator)
                .name("isEqualTo[" + stringify(other) + ", comparator: " + stringify(comparator) + "]")
                .expected(stringify(other))
        );
    }

    private ValueBuilder<T, E> comparableEquals(final T other, final Comparator<T> comparator) {

        if (comparator == null) {
            return nullableExpression(s -> false)
                .hint(CANNOT_COMPARE_USING_NULL_COMPARATOR);
        }
        else {
            return nullableExpression(s -> comparator.compare(s, other) == 0);
        }
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not equal to the supplied object.
     * <p>
     * This evaluation will fail if the subject and supplied object are both {@code null}.
     * <p>
     * Note: This check is equivalent to the following statement, where {@code other} is the supplied object and
     * {@code subject} is the subject of the expression:
     * <pre><code>
     * !Objects.equals(subject, other);
     * </code></pre>
     *
     * @param other
     *     The object to test against.
     *
     * @return This expression (fluent interface).
     *
     * @see Objects#equals(Object, Object)
     */
    public E isNotEqualTo(final Object other) {
        return append(
            nullableExpression(s -> !Objects.equals(s, other))
                .name("isNotEqualTo[" + stringify(other) + "]")
                .expected("not[" + stringify(other) + "]")
                .hint("Actual should be equal to " + other + " (using !subject.equals(" + other + "))")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the subject is not equal to the supplied object using the
     * supplied comparator.
     * <p>
     * This evaluation will fail if the supplied comparator is {@code null}. If the comparator is not {@code null}, this
     * evaluation will fail if the subject and supplied object are both {@code null}.
     *
     * @param other
     *     The object to test against.
     * @param comparator
     *     The {@link Comparator} to use when checking the equality of the supplied object and the subject.
     *
     * @return This expression (fluent interface).
     *
     * @see Comparator#compare(Object, Object)
     */
    public E isNotEqualTo(final T other, final Comparator<T> comparator) {
        return append(
            comparableNotEquals(other, comparator)
                .name("isNotEqualTo[" + stringify(other) + ", comparator: " + stringify(comparator) + "]")
                .expected(stringify(other))
        );
    }

    private ValueBuilder<T, E> comparableNotEquals(final T other, final Comparator<T> comparator) {

        if (comparator == null) {
            return nullableExpression(s -> false)
                .hint(CANNOT_COMPARE_USING_NULL_COMPARATOR);
        }
        else {
            return nullableExpression(s -> comparator.compare(s, other) != 0);
        }
    }

    /**
     * Adds a predicate to the expression that checks if the hash code of the subject is equal to the supplied hash
     * code.
     * <p>
     * Note: This check is equivalent to the following statement, where {@code hashCode} is the supplied hash code and
     * {@code subject} is the subject of the expression:
     * <pre><code>
     * subject.hashCode() == hashCode
     * </code></pre>
     *
     * @param hashCode
     *     The hash code to test against.
     *
     * @return This expression (fluent interface).
     */
    public E hashCodeIs(final int hashCode) {
        return append(
            expression(s -> s.hashCode() == hashCode)
                .name("hashCode == " + hashCode)
                .expected(hashCode)
                .actual(hashCodeSupplier())
        );
    }

    private static <T> ValueSupplier<T> hashCodeSupplier() {
        return (s, passed) -> String.valueOf(s.hashCode());
    }

    /**
     * Adds a predicate to the expression that checks if the hash code of the subject is not equal to the supplied hash
     * code.
     * <p>
     * Note: This check is equivalent to the following statement, where {@code hashCode} is the supplied hash code and
     * {@code subject} is the subject of the expression:
     * <pre><code>
     * subject.hashCode() != hashCode
     * </code></pre>
     *
     * @param hashCode
     *     The hash code to test against.
     *
     * @return This expression (fluent interface).
     */
    public E hashCodeIsNot(final int hashCode) {
        return append(
            expression(s -> s.hashCode() != hashCode)
                .name("hashCode != " + hashCode)
                .expected("not[" + hashCode + "]")
                .actual(hashCodeSupplier())
        );
    }

    /**
     * Adds a predicate to the expression that checks if the {@code toString()} value of the subject is equal to the
     * supplied value.
     * <p>
     * Note: This check is equivalent to the following statement, where {@code hashCode} is the supplied hash code and
     * {@code subject} is the subject of the expression:
     * <pre><code>
     * subject.toString().equals(expected)
     * </code></pre>
     *
     * @param expected
     *     The string to test against.
     *
     * @return This expression (fluent interface).
     */
    public E toStringIs(final String expected) {
        return append(
            expression(s -> s.toString().equals(expected))
                .name("toString().equals(" + expected + ")")
                .expected(expected)
                .hint("Subject's toString() value should equal " + expected + " (using subject.toString().equals(" + expected + "))")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the {@code toString()} value of the subject is not equal to the
     * supplied value.
     * <p>
     * Note: This check is equivalent to the following statement, where {@code hashCode} is the supplied hash code and
     * {@code subject} is the subject of the expression:
     * <pre><code>
     * !subject.toString().equals(expected)
     * </code></pre>
     *
     * @param expected
     *     The string to test against.
     *
     * @return This expression (fluent interface).
     */
    public E toStringIsNot(final String expected) {
        return append(
            expression(s -> !s.toString().equals(expected))
                .name("not[toString().equals(" + expected + ")]")
                .expected("not[" + expected + "]")
                .hint("Subject's toString() value should not equal " + expected + " (using !subject.toString().equals(" + expected + "))")
        );
    }

    /**
     * Adds a predicate to the expression that checks if the supplied predicate is true.
     *
     * @param predicate
     *     The predicate to test.
     *
     * @return This expression (fluent interface).
     */
    public E satisfies(final Predicate<T> predicate) {
        return append(
            expression(predicate)
                .name("predicate")
                .expected(PREDICATE_SATISFIED)
                .actual((o, passed) -> passed ? PREDICATE_SATISFIED : PREDICATE_UNSATISFIED)
        );
    }

    /**
     * Adds a predicate to the expression that checks if the supplied predicate is false.
     *
     * @param predicate
     *     The predicate to test.
     *
     * @return This expression (fluent interface).
     */
    public E doesNotSatisfy(final Predicate<T> predicate) {
        return append(
            expression(o -> !predicate.test(o))
                .name("not[predicate]")
                .expected(PREDICATE_UNSATISFIED)
                .actual((o, passed) -> passed ? PREDICATE_UNSATISFIED : PREDICATE_SATISFIED)
        );
    }
}