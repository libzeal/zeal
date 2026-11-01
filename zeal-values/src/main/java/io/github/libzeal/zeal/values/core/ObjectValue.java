package io.github.libzeal.zeal.values.core;

import io.github.libzeal.zeal.logic.Expression;
import io.github.libzeal.zeal.logic.unary.CompoundUnaryExpression;
import io.github.libzeal.zeal.logic.unary.Condition;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

public interface ObjectValue<T, E extends ObjectValue<T, E>>
        extends CompoundUnaryExpression<T> {

    /**
     * Checks if the subject is {@code null}.
     *
     * @return This value (fluent interface).
     */
    E isNull();

    /**
     * Checks if the subject is not {@code null}.
     * <p>
     * Note: This evaluation takes precedent over all other evaluations. For
     * example, if the chain {@code expression.someFooCheck().isNotNull()} were
     * created, the {@code #isNotNull()} evaluation would be evaluated before
     * the {@code someFooCheck()} evaluation when the expression is evaluated
     * (using the {@link Expression#evaluate()} method), even though it was
     * second in the chain.
     * <p>
     * Note: The precedence of this evaluation is due to the nature of
     * evaluations on objects. Every evaluation, expect for nullity evaluations
     * (such as "is @{code null}" or "is not {@code null}") or equality checks
     * assume that the subject of the evaluation is not {@code null}. For
     * example, assume the following chain is called:
     * <pre>{@code expression.toStringIs("foo").isNotNull()}</pre>
     * <p>
     * If the subject of the expression is {@code null}, then the
     * {@code toStringIs("foo")} evaluation will fail, but not because the
     * {@link Object#toString()} value of the expression is not equal to
     * {@code "foo"}, but because the subject of the expression is {@code null}
     * (the {@link Object#toString} method could not be called on the subject,
     * or else a {@link NullPointerException} would be thrown).
     * <p>
     * Therefore, if an expression has a {@code #isNotNull()} evaluation, this
     * evaluation is automatically evaluated first. If the expression does not
     * include {@code #isNotNull()}, then the evaluations are evaluated in the
     * order in which they are chained on the expression.
     *
     * @return This value (fluent interface).
     */
    E isNotNull();

    /**
     * Checks if the subject exactly matches the supplied type.
     * <p>
     * Note: If the subject is a subtype of the supplied type, this check will
     * fail.
     *
     * @param type
     *         The type to test the subject against. This evaluation will always
     *         fail if the supplied type is {@code null}
     *
     * @return This value (fluent interface).
     */
    E isType(Class<?> type);

    /**
     * Checks if the subject is not an exact match of the supplied type.
     *
     * @param type
     *         The type to test the subject against. This evaluation will always
     *         fail if the supplied type is {@code null}.
     *
     * @return This value (fluent interface).
     */
    E isNotType(Class<?> type);

    /**
     * Checks if the subject matches or is a subtype of the supplied type.
     * <p>
     * Note: This check is equivalent to the following statement, where
     * {@code type} is the supplied type and {@code subject} is the subject of
     * the expression:
     * <pre>{@code
     * type.isAssignableFrom(subject.getClass())
     * }</pre>
     *
     * @param type
     *         The type to test the subject against. This evaluation will always
     *         fail if the supplied type is {@code null}.
     *
     * @return This value (fluent interface).
     *
     * @see Class#isAssignableFrom(Class)
     */
    E isInstanceOf(Class<?> type);

    /**
     * Checks if the subject does not exactly match or is not a subtype of the
     * supplied type.
     * <p>
     * Note: This check is equivalent to the following statement, where
     * {@code type} is the supplied type and {@code subject} is the subject of
     * the expression:
     * <pre>{@code
     * !type.isAssignableFrom(subject.getClass())
     * }</pre>
     *
     * @param type
     *         The type to test the subject against. This evaluation will always
     *         fail if the supplied type is {@code null}.
     *
     * @return This value (fluent interface).
     *
     * @see Class#isAssignableFrom(Class)
     */
    E isNotInstanceOf(Class<?> type);

    /**
     * Checks if the subject is the same as the supplied object.
     * <p>
     * This evaluation will pass if the subject and supplied object are both
     * {@code null}.
     * <p>
     * Note: This check is equivalent to the following statement, where
     * {@code other} is the supplied object and {@code subject} is the subject
     * of the expression:
     * <pre>{@code
     * subject == object
     * }</pre>
     *
     * @param other
     *         The object to test against.
     *
     * @return This value (fluent interface).
     */
    E isExactly(Object other);

    /**
     * Checks if the subject is not the same as the supplied object.
     * <p>
     * This evaluation will fail if the subject and supplied object are both
     * {@code null}.
     * <p>
     * Note: This check is equivalent to the following statement, where
     * {@code other} is the supplied object and {@code subject} is the subject
     * of the expression:
     * <pre>{@code
     * subject != object
     * }</pre>
     *
     * @param other
     *         The object to test against.
     *
     * @return This value (fluent interface).
     */
    E isNotExactly(Object other);

    /**
     * Checks if the subject satisfies the supplied condition.
     *
     * @param condition
     *         The condition to check.
     *
     * @return This value (fluent interface).
     */
    E is(Condition<T> condition);

    /**
     * Checks if the subject is equal to the supplied object.
     * <p>
     * This evaluation will pass if the subject and supplied object are both
     * {@code null}.
     * <p>
     * Note: This check is equivalent to the following statement, where
     * {@code other} is the supplied object and {@code subject} is the subject
     * of the expression:
     * <pre>{@code
     * Objects.equals(subject, other);
     * }</pre>
     *
     * @param other
     *         The object to test against.
     *
     * @return This value (fluent interface).
     *
     * @see Objects#equals(Object, Object)
     */
    E isEqualTo(Object other);

    /**
     * Checks if the subject is equal to the supplied object using the supplied
     * comparator.
     * <p>
     * This evaluation will fail if the supplied comparator is {@code null}. If
     * the comparator is not {@code null}, this evaluation will pass if the
     * subject and supplied object are both {@code null}.
     *
     * @param other
     *         The object to test against.
     * @param comparator
     *         The {@link Comparator} to use when checking the equality of the
     *         supplied object and the subject.
     *
     * @return This value (fluent interface).
     *
     * @see Comparator#compare(Object, Object)
     */
    E isEqualTo(T other, Comparator<T> comparator);

    /**
     * Checks if the subject is not equal to the supplied object.
     * <p>
     * This evaluation will fail if the subject and supplied object are both
     * {@code null}.
     * <p>
     * Note: This check is equivalent to the following statement, where
     * {@code other} is the supplied object and {@code subject} is the subject
     * of the expression:
     * <pre>{@code
     * !Objects.equals(subject, other);
     * }</pre>
     *
     * @param other
     *         The object to test against.
     *
     * @return This value (fluent interface).
     *
     * @see Objects#equals(Object, Object)
     */
    E isNotEqualTo(Object other);

    /**
     * Checks if the subject is not equal to the supplied object using the
     * supplied comparator.
     * <p>
     * This evaluation will fail if the supplied comparator is {@code null}. If
     * the comparator is not {@code null}, this evaluation will fail if the
     * subject and supplied object are both {@code null}.
     *
     * @param other
     *         The object to test against.
     * @param comparator
     *         The {@link Comparator} to use when checking the equality of the
     *         supplied object and the subject.
     *
     * @return This value (fluent interface).
     *
     * @see Comparator#compare(Object, Object)
     */
    E isNotEqualTo(T other, Comparator<T> comparator);

    /**
     * Checks if the hash code of the subject is equal to the supplied hash
     * code.
     * <p>
     * Note: This check is equivalent to the following statement, where
     * {@code hashCode} is the supplied hash code and {@code subject} is the
     * subject of the expression:
     * <pre>{@code
     * subject.hashCode() == hashCode
     * }</pre>
     *
     * @param hashCode
     *         The hash code to test against.
     *
     * @return This value (fluent interface).
     */
    E hashCodeIs(int hashCode);

    /**
     * Checks if the hash code of the subject is not equal to the supplied hash
     * code.
     * <p>
     * Note: This check is equivalent to the following statement, where
     * {@code hashCode} is the supplied hash code and {@code subject} is the
     * subject of the expression:
     * <pre>{@code
     * subject.hashCode() != hashCode
     * }</pre>
     *
     * @param hashCode
     *         The hash code to test against.
     *
     * @return This value (fluent interface).
     */
    E hashCodeIsNot(int hashCode);

    /**
     * Checks if the {@code toString()} value of the subject is equal to the
     * supplied value.
     * <p>
     * Note: This check is equivalent to the following statement, where
     * {@code hashCode} is the supplied hash code and {@code subject} is the
     * subject of the expression:
     * <pre>{@code
     * subject.toString().equals(expected)
     * }</pre>
     *
     * @param expected
     *         The string to test against.
     *
     * @return This value (fluent interface).
     */
    E toStringIs(String expected);

    /**
     * Checks if the {@code toString()} value of the subject is not equal to the
     * supplied value.
     * <p>
     * Note: This check is equivalent to the following statement, where
     * {@code hashCode} is the supplied hash code and {@code subject} is the
     * subject of the expression:
     * <pre>{@code
     * !subject.toString().equals(expected)
     * }</pre>
     *
     * @param expected
     *         The string to test against.
     *
     * @return This value (fluent interface).
     */
    E toStringIsNot(String expected);

    /**
     * Checks if the supplied predicate is true.
     *
     * @param predicate
     *         The predicate to test.
     *
     * @return This value (fluent interface).
     */
    E satisfies(Predicate<T> predicate);

    /**
     * Checks if the supplied predicate is false.
     *
     * @param predicate
     *         The predicate to test.
     *
     * @return This value (fluent interface).
     */
    E doesNotSatisfy(Predicate<T> predicate);
}
