package io.github.libzeal.zeal.values.sdk;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.Result;
import io.github.libzeal.zeal.logic.evaluation.traverse.CollectingTraverserAction;
import io.github.libzeal.zeal.logic.evaluation.traverse.DepthFirstTraverser;
import io.github.libzeal.zeal.logic.evaluation.traverse.Traverser;
import io.github.libzeal.zeal.logic.unary.condition.Condition;
import io.github.libzeal.zeal.values.core.ObjectValue;
import io.github.libzeal.zeal.values.core.test.ExpressionTestCaseBuilder;
import io.github.libzeal.zeal.values.core.test.ValueAssertion;
import io.github.libzeal.zeal.values.core.test.ValueTestCaseBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static io.github.libzeal.zeal.logic.evaluation.Result.FALSE;
import static io.github.libzeal.zeal.logic.evaluation.Result.TRUE;
import static io.github.libzeal.zeal.values.sdk.StandardMessages.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseValueTest<T, E extends ObjectValue<T, E>> {

    protected abstract E expression(T value);
    protected abstract T nonNullValueA();
    protected abstract T nonNullValueB();

    protected ValueTestCaseBuilder<T, E> newTestCase() {
        return ValueTestCaseBuilder.forValue(this::expression);
    }

    private static final class UnrelatedClass {}

    private Class<?> otherClass() {
        return UnrelatedClass.class;
    }

    private final Comparator<T> equalComparator = (a, b) -> 0;
    private final Comparator<T> lessThanComparator = (a, b) -> -1;
    private final Comparator<T> greaterThanComparator = (a, b) -> 1;

    @Test
    final void givenNoEvaluations_whenConstruct_thenCorrectEvaluation() {

        final T value = nonNullValueA();
        final E expression = expression(value);
        final Evaluation evaluation = expression.evaluate();
        final ValueAssertion<?> assertion = new ValueAssertion<>(evaluation);

        assertEquals(value, expression.subject());
        assertion.assertStateIs(TRUE);
        assertion.assertCompoundExpectedValue("Passed: 0");
        assertion.assertCompoundActualValueIs(0, 0, 0);
    }

    @Test
    final void givenNotNullFollowsAnotherEvaluation_whenEvaluate_thenNotNullPreemptsOtherEvaluation() {

        final E expression = expression(null);

        expression.isNull().isNotNull();

        final Evaluation eval = expression.evaluate();
        final Traverser traverser = new DepthFirstTraverser();
        final CollectingTraverserAction collector = new CollectingTraverserAction();

        traverser.traverse(eval, collector);

        final Evaluation first = collector.get(1).orElse(null);
        final Evaluation second = collector.get(2).orElse(null);

        ValueAssertion<?> firstAssertion = new ValueAssertion<>(first);
        ValueAssertion<?> secondAssertion = new ValueAssertion<>(second);

        assertNotNull(first);
        assertNotNull(second);
        firstAssertion.assertStateIs(FALSE);
        firstAssertion.assertNameIs("isNotNull");
        secondAssertion.assertStateIs(Result.SKIPPED);
    }

    // ---- nullity ----

    @Test
    void givenNull_whenIsNull_thenTrue() {
        newTestCase()
            .subject(null)
            .expression(ObjectValue::isNull)
            .result(TRUE)
            .name("isNull")
            .expected("(null)")
            .actual("(null)")
            .run();
    }

    @Test
    void givenNonNull_whenIsNull_thenFalse() {
        newTestCase()
            .subject(nonNullValueA())
            .expression(ObjectValue::isNull)
            .result(FALSE)
            .name("isNull")
            .expected("(null)")
            .actual(String.valueOf(nonNullValueA()))
            .run();
    }

    @Test
    void givenNull_whenIsNotNull_thenFalse() {
        newTestCase()
            .subject(null)
            .expression(ObjectValue::isNotNull)
            .result(FALSE)
            .name("isNotNull")
            .expected("not[(null)]")
            .actual("(null)")
            .run();
    }

    @Test
    void givenNonNull_whenIsNotNull_thenTrue() {
        newTestCase()
            .subject(nonNullValueA())
            .expression(ObjectValue::isNotNull)
            .result(TRUE)
            .name("isNotNull")
            .expected("not[(null)]")
            .actual(String.valueOf(nonNullValueA()))
            .run();
    }

    // ---- type ----

    @Test
    void givenMatchingClass_whenIsType_thenTrue() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isType(v.getClass()))
            .result(TRUE)
            .name("isType[" + v.getClass() + "]")
            .expected(v.getClass().toString())
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenDifferentClass_whenIsType_thenFalse() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isType(otherClass()))
            .result(FALSE)
            .name("isType[" + otherClass() + "]")
            .expected(otherClass().toString())
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenNullClass_whenIsType_thenFalse() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isType(null))
            .result(FALSE)
            .name("isType[(null)]")
            .expected("Always fail: cannot compare to (null) type")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenMatchingClass_whenIsNotType_thenFalse() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isNotType(v.getClass()))
            .result(FALSE)
            .name("isNotType[" + v.getClass() + "]")
            .expected("not[" + v.getClass() + "]")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenDifferentClass_whenIsNotType_thenTrue() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isNotType(otherClass()))
            .result(TRUE)
            .name("isNotType[" + otherClass() + "]")
            .expected("not[" + otherClass() + "]")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenNullClass_whenIsNotType_thenFalse() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isNotType(null))
            .result(FALSE)
            .name("isNotType[(null)]")
            .expected("Always fail: cannot compare to (null) type")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenMatchingClass_whenIsInstanceOf_thenTrue() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isInstanceOf(v.getClass()))
            .result(TRUE)
            .name("isInstanceOf[" + v.getClass() + "]")
            .expected("instanceof[" + v.getClass() + "]")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenObjectClass_whenIsInstanceOf_thenTrue() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isInstanceOf(Object.class))
            .result(TRUE)
            .name("isInstanceOf[" + Object.class + "]")
            .expected("instanceof[" + Object.class + "]")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenDifferentClass_whenIsInstanceOf_thenFalse() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isInstanceOf(otherClass()))
            .result(FALSE)
            .name("isInstanceOf[" + otherClass() + "]")
            .expected("instanceof[" + otherClass() + "]")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenNullClass_whenIsInstanceOf_thenFalse() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isInstanceOf(null))
            .result(FALSE)
            .name("isInstanceOf[(null)]")
            .expected("Always fail: cannot compare to (null) type")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenMatchingClass_whenIsNotInstanceOf_thenFalse() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isNotInstanceOf(v.getClass()))
            .result(FALSE)
            .name("isNotInstanceOf[" + v.getClass() + "]")
            .expected("not[instanceof[" + v.getClass() + "]]")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenObjectClass_whenIsNotInstanceOf_thenFalse() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isNotInstanceOf(Object.class))
            .result(FALSE)
            .name("isNotInstanceOf[" + Object.class + "]")
            .expected("not[instanceof[" + Object.class + "]]")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenDifferentClass_whenIsNotInstanceOf_thenTrue() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isNotInstanceOf(otherClass()))
            .result(TRUE)
            .name("isNotInstanceOf[" + otherClass() + "]")
            .expected("not[instanceof[" + otherClass() + "]]")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenNullClass_whenIsNotInstanceOf_thenFalse() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isNotInstanceOf(null))
            .result(FALSE)
            .name("isNotInstanceOf[(null)]")
            .expected("Always fail: cannot compare to (null) type")
            .actual(v.getClass().toString())
            .run();
    }

    // ---- exactly / equal ----

    @Test
    void givenSameValue_whenIsExactly_thenTrue() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isExactly(v))
            .result(TRUE)
            .name("isExactly[" + v + "]")
            .expected(v.toString())
            .actual(v.toString())
            .run();
    }

    @Test
    void givenDifferentValue_whenIsExactly_thenFalse() {
        T a = nonNullValueA();
        T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isExactly(a))
            .result(FALSE)
            .name("isExactly[" + a + "]")
            .expected(a.toString())
            .actual(b.toString())
            .run();
    }

    @Test
    void givenNull_whenIsExactlyNull_thenTrue() {
        newTestCase()
            .subject(null)
            .expression(value -> value.isExactly(null))
            .result(TRUE)
            .name("isExactly[(null)]")
            .expected("(null)")
            .actual("(null)")
            .run();
    }

    @Test
    void givenSameValue_whenIsNotExactly_thenFalse() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isNotExactly(v))
            .result(FALSE)
            .name("isNotExactly[" + v + "]")
            .expected("not[" + v + "]")
            .actual(v.toString())
            .run();
    }

    @Test
    void givenDifferentValue_whenIsNotExactly_thenTrue() {
        T a = nonNullValueA();
        T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isNotExactly(a))
            .result(TRUE)
            .name("isNotExactly[" + a + "]")
            .expected("not[" + a + "]")
            .actual(b.toString())
            .run();
    }

    @Test
    void givenNull_whenIsNotExactlyNull_thenFalse() {
        newTestCase()
            .subject(null)
            .expression(value -> value.isNotExactly(null))
            .result(FALSE)
            .name("isNotExactly[(null)]")
            .expected("not[(null)]")
            .actual("(null)")
            .run();
    }

    @Test
    void givenSameValue_whenIsEqualTo_thenTrue() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isEqualTo(v))
            .result(TRUE)
            .name("isEqualTo[" + v + "]")
            .expected(v.toString())
            .actual(v.toString())
            .run();
    }

    @Test
    void givenDifferentValue_whenIsEqualTo_thenFalse() {
        T a = nonNullValueA();
        T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isEqualTo(a))
            .result(FALSE)
            .name("isEqualTo[" + a + "]")
            .expected(a.toString())
            .actual(b.toString())
            .run();
    }

    @Test
    void givenNull_whenIsEqualToNull_thenTrue() {
        newTestCase()
            .subject(null)
            .expression(value -> value.isEqualTo(null))
            .result(TRUE)
            .name("isEqualTo[(null)]")
            .expected("(null)")
            .actual("(null)")
            .run();
    }

    // ---- condition ----

    @Test
    void givenNullCondition_whenIs_thenFalse() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.is(null))
            .result(FALSE)
            .name("is[condition: (null)]")
            .expected(ALWAYS_FAIL_CANNOT_CHECK_TO_NULL_CONDITION)
            .actual(v.toString())
            .hint(CANNOT_CHECK_USING_NULL_CONDITION)
            .run();
    }

    @Test
    void givenTautology_whenIs_thenTrue() {
        T v = nonNullValueA();
        Condition<T> cond = Condition.tautology();
        newTestCase()
            .subject(v)
            .expression(value -> value.is(cond))
            .result(TRUE)
            .name(cond.compute(v).name())
            .expected("true")
            .actual("true")
            .run();
    }

    @Test
    void givenContradiction_whenIs_thenFalse() {
        T v = nonNullValueA();
        Condition<T> cond = Condition.contradiction();
        newTestCase()
            .subject(v)
            .expression(value -> value.is(cond))
            .result(FALSE)
            .name(cond.compute(v).name())
            .expected("false")
            .actual("false")
            .run();
    }

    // ---- hashCode ----

    @Test
    void givenMatchingHash_whenHashCodeIs_thenTrue() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.hashCodeIs(v.hashCode()))
            .result(TRUE)
            .name("hashCode == " + v.hashCode())
            .expected(String.valueOf(v.hashCode()))
            .actual(String.valueOf(v.hashCode()))
            .run();
    }

    @Test
    void givenDifferentHash_whenHashCodeIs_thenFalse() {
        T a = nonNullValueA();
        T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.hashCodeIs(a.hashCode()))
            .result(FALSE)
            .name("hashCode == " + a.hashCode())
            .expected(String.valueOf(a.hashCode()))
            .actual(String.valueOf(b.hashCode()))
            .run();
    }

    @Test
    void givenMatchingHash_whenHashCodeIsNot_thenFalse() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.hashCodeIsNot(v.hashCode()))
            .result(FALSE)
            .name("hashCode != " + v.hashCode())
            .expected("not[" + v.hashCode() + "]")
            .actual(String.valueOf(v.hashCode()))
            .run();
    }

    @Test
    void givenDifferentHash_whenHashCodeIsNot_thenTrue() {
        T a = nonNullValueA();
        T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.hashCodeIsNot(a.hashCode()))
            .result(TRUE)
            .name("hashCode != " + a.hashCode())
            .expected("not[" + a.hashCode() + "]")
            .actual(String.valueOf(b.hashCode()))
            .run();
    }

    // ---- toString ----

    @Test
    void givenMatchingToString_whenToStringIs_thenTrue() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.toStringIs(v.toString()))
            .result(TRUE)
            .name("toString().equals(" + v + ")")
            .expected(v.toString())
            .actual(v.toString())
            .run();
    }

    @Test
    void givenDifferentToString_whenToStringIs_thenFalse() {
        T a = nonNullValueA();
        T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.toStringIs(a.toString()))
            .result(FALSE)
            .name("toString().equals(" + a + ")")
            .expected(a.toString())
            .actual(b.toString())
            .run();
    }

    @Test
    void givenMatchingToString_whenToStringIsNot_thenFalse() {
        T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.toStringIsNot(v.toString()))
            .result(FALSE)
            .name("not[toString().equals(" + v + ")]")
            .expected("not[" + v + "]")
            .actual(v.toString())
            .run();
    }

    @Test
    void givenDifferentToString_whenToStringIsNot_thenTrue() {
        T a = nonNullValueA();
        T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.toStringIsNot(a.toString()))
            .result(TRUE)
            .name("not[toString().equals(" + a + ")]")
            .expected("not[" + a + "]")
            .actual(b.toString())
            .run();
    }

    // ---- predicate ----

    @Test
    void givenAlwaysTrue_whenSatisfies_thenTrue() {
        newTestCase()
            .subject(nonNullValueA())
            .expression(value -> value.satisfies(o -> true))
            .result(TRUE)
            .name("predicate")
            .expected("Predicate satisfied")
            .actual("Predicate satisfied")
            .run();
    }

    @Test
    void givenAlwaysFalse_whenSatisfies_thenFalse() {
        newTestCase()
            .subject(nonNullValueA())
            .expression(value -> value.satisfies(o -> false))
            .result(FALSE)
            .name("predicate")
            .expected("Predicate satisfied")
            .actual("Predicate unsatisfied")
            .run();
    }

    // ---- comparator ----

    @Test
    void givenNullComparator_whenIsEqualTo_thenFalse() {
        T a = nonNullValueA();
        T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isEqualTo(a, null))
            .result(FALSE)
            .name("isEqualTo[" + a + ", comparator: (null)]")
            .expected(a.toString())
            .actual(b.toString())
            .hint(CANNOT_COMPARE_USING_NULL_COMPARATOR)
            .run();
    }

    @Test
    void givenEqualComparator_whenIsEqualTo_thenTrue() {
        T a = nonNullValueA();
        T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isEqualTo(a, equalComparator))
            .result(TRUE)
            .name("isEqualTo[" + a + ", comparator: " + equalComparator + "]")
            .expected(a.toString())
            .actual(b.toString())
            .run();
    }

// ---- isNotEqualTo ----

@Test
void givenSame_whenIsNotEqualTo_thenFalse() {
    T v = nonNullValueA();
    newTestCase()
        .subject(v)
        .expression(value -> value.isNotEqualTo(v))
        .result(FALSE)
        .name("isNotEqualTo[" + v + "]")
        .expected("not[" + v + "]")
        .actual(v.toString())
        .run();
    }

    @Test
    void givenDifferent_whenIsNotEqualTo_thenTrue() {
        T a = nonNullValueA();
        T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isNotEqualTo(a))
            .result(TRUE)
            .name("isNotEqualTo[" + a + "]")
            .expected("not[" + a + "]")
            .actual(b.toString())
            .run();
    }

    // ---- comparator not-equal ----

    @Test
    void givenNullComparator_whenIsNotEqualTo_thenFalse() {
        T a = nonNullValueA();
        T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isNotEqualTo(a, null))
            .result(FALSE)
            .name("isNotEqualTo[" + a + ", comparator: (null)]")
            .expected(a.toString())
            .actual(b.toString())
            .hint(CANNOT_COMPARE_USING_NULL_COMPARATOR)
            .run();
    }

    @Test
    void givenEqualComparator_whenIsNotEqualTo_thenFalse() {
        T a = nonNullValueA();
        T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isNotEqualTo(a, equalComparator))
            .result(FALSE)
            .name("isNotEqualTo[" + a + ", comparator: " + equalComparator + "]")
            .expected(a.toString())
            .actual(b.toString())
            .run();
    }

    @Test
    void givenLessThanComparator_whenIsNotEqualTo_thenTrue() {
        T a = nonNullValueA();
        T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isNotEqualTo(a, lessThanComparator))
            .result(TRUE)
            .name("isNotEqualTo[" + a + ", comparator: " + lessThanComparator + "]")
            .expected(a.toString())
            .actual(b.toString())
            .run();
    }

    @Test
    void givenGreaterThanComparator_whenIsNotEqualTo_thenTrue() {
        T a = nonNullValueA();
        T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isNotEqualTo(a, greaterThanComparator))
            .result(TRUE)
            .name("isNotEqualTo[" + a + ", comparator: " + greaterThanComparator + "]")
            .expected(a.toString())
            .actual(b.toString())
            .run();
    }

    // ---- doesNotSatisfy ----

    @Test
    void givenAlwaysTrue_whenDoesNotSatisfy_thenFalse() {
        newTestCase()
            .subject(nonNullValueA())
            .expression(value -> value.doesNotSatisfy(o -> true))
            .result(FALSE)
            .name("not[predicate]")
            .expected("Predicate unsatisfied")
            .actual("Predicate satisfied")
            .run();
    }

    @Test
    void givenAlwaysFalse_whenDoesNotSatisfy_thenTrue() {
        newTestCase()
            .subject(nonNullValueA())
            .expression(value -> value.doesNotSatisfy(o -> false))
            .result(TRUE)
            .name("not[predicate]")
            .expected("Predicate unsatisfied")
            .actual("Predicate unsatisfied")
            .run();
    }

    @Test
    void givenNull_whenIsNotEqualToNull_thenFalse() {
        newTestCase()
            .subject(null)
            .expression(value -> value.isNotEqualTo(null))
            .result(FALSE)
            .name("isNotEqualTo[(null)]")
            .expected("not[(null)]")
            .actual("(null)")
            .run();
    }

    @Test
    void givenLessThanComparator_whenIsEqualTo_thenFalse() {
        T a = nonNullValueA();
        T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isEqualTo(a, lessThanComparator))
            .result(FALSE)
            .name("isEqualTo[" + a + ", comparator: " + lessThanComparator + "]")
            .expected(a.toString())
            .actual(b.toString())
            .run();
    }

    @Test
    void givenGreaterThanComparator_whenIsEqualTo_thenFalse() {
        T a = nonNullValueA();
        T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isEqualTo(a, greaterThanComparator))
            .result(FALSE)
            .name("isEqualTo[" + a + ", comparator: " + greaterThanComparator + "]")
            .expected(a.toString())
            .actual(b.toString())
            .run();
    }

    @Test
    void givenAlwaysTrue_whenDoesNotSatisfy_thenFalse_nameCheck() {
        newTestCase()
            .subject(nonNullValueA())
            .expression(value -> value.doesNotSatisfy(o -> true))
            .result(FALSE)
            .name("not[predicate]")
            .expected("Predicate unsatisfied")
            .actual("Predicate satisfied")
            .run();
    }



    @ParameterizedTest(name = "{0}")
    @MethodSource("testCaseStream")
    final void whenEvaluate_thenCorrectEvaluation(
        String testCaseName,
        BiFunction<E, T, E> modifier,
        T value,
        Result expectedState,
        Function<T, String> expectedName,
        BiFunction<E, T, String> expectedExpectedValue,
        BiFunction<E, T, String> expectedActualValue,
        BiFunction<E, T, String> expectedHint
    ) {

        final E expression = expression(value);
        final E returnedExpression = modifier.apply(expression, value);

        assertNotNull(returnedExpression);
        assertEquals(expression.getClass(), returnedExpression.getClass());

        final Evaluation eval = expression.evaluate();
        final Traverser traverser = new DepthFirstTraverser();
        final CollectingTraverserAction collector = new CollectingTraverserAction();

        traverser.traverse(eval, collector);

        final Evaluation first = collector.get(1).orElse(null);
        final ValueAssertion<?> firstEvaluation = new ValueAssertion<>(first);

        assertNotNull(first);
        firstEvaluation.assertStateIs(expectedState);
        firstEvaluation.assertNameIs(expectedName.apply(value));
        firstEvaluation.assertExpectedIs(expectedExpectedValue.apply(expression, value));
        firstEvaluation.assertActualIs(expectedActualValue.apply(expression, value));

        if (expectedHint != null) {
            firstEvaluation.assertHintIs(expectedHint.apply(expression, value));
        }
    }

    private Stream<Arguments> testCaseStream() {

        ExpressionTestCaseBuilder<T, E> builder = ExpressionTestCaseBuilder.newBuilder();

        extendTestCases(builder);

        return builder.stream();
    }

    protected void extendTestCases(ExpressionTestCaseBuilder<T, E> builder) {}
}
