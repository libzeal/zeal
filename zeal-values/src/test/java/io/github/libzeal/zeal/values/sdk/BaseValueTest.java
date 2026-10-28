package io.github.libzeal.zeal.values.sdk;

import io.github.libzeal.zeal.logic.evaluation.Evaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Evaluated;
import io.github.libzeal.zeal.logic.evaluation.SkippableEvaluation.Skipped;
import io.github.libzeal.zeal.logic.unary.Condition;
import io.github.libzeal.zeal.values.core.ObjectValue;
import io.github.libzeal.zeal.values.core.test.ValueAssertion;
import io.github.libzeal.zeal.values.core.test.ValueTestCaseBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Comparator;
import java.util.List;

import static io.github.libzeal.zeal.values.sdk.StandardMessages.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseValueTest<T, E extends ObjectValue<T, E>> {

    protected abstract E expression(T value);
    protected abstract T nonNullValueA();
    protected abstract T nonNullValueB();

    public ValueTestCaseBuilder<T, E> newTestCase() {
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
        assertion.assertStateIs(true);
        assertion.assertCompoundExpectedValue("Passed: 0");
        assertion.assertCompoundActualValueIs(0, 0, 0);
    }

    @Test
    final void givenNotNullFollowsAnotherEvaluation_whenEvaluate_thenNotNullPreemptsOtherEvaluation() {

        final E expression = expression(null);

        expression.isNull().isNotNull();

        final List<SkippableEvaluation> children = expression.evaluate().children();

        assertEquals(2, children.size());

        final Evaluated first = assertInstanceOf(Evaluated.class, children.get(0));
        final Skipped second = assertInstanceOf(Skipped.class, children.get(1));
        final ValueAssertion<?> firstAssertion = new ValueAssertion<>(first.evaluation());

        firstAssertion.assertStateIs(false);
        firstAssertion.assertNameIs("isNotNull");
        assertEquals("isNull", second.name());
        assertEquals(List.of(first.evaluation()), second.cause().evaluations());
    }

    // ---- nullity ----

    @Test
    void givenNull_whenIsNull_thenTrue() {
        newTestCase()
            .subject(null)
            .expression(ObjectValue::isNull)
            .result(true)
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
            .result(false)
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
            .result(false)
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
            .result(true)
            .name("isNotNull")
            .expected("not[(null)]")
            .actual(String.valueOf(nonNullValueA()))
            .run();
    }

    // ---- type ----

    @Test
    void givenMatchingClass_whenIsType_thenTrue() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isType(v.getClass()))
            .result(true)
            .name("isType[" + v.getClass() + "]")
            .expected(v.getClass().toString())
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenDifferentClass_whenIsType_thenFalse() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isType(otherClass()))
            .result(false)
            .name("isType[" + otherClass() + "]")
            .expected(otherClass().toString())
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenNullClass_whenIsType_thenFalse() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isType(null))
            .result(false)
            .name("isType[(null)]")
            .expected("Always fail: cannot compare to (null) type")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenMatchingClass_whenIsNotType_thenFalse() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isNotType(v.getClass()))
            .result(false)
            .name("isNotType[" + v.getClass() + "]")
            .expected("not[" + v.getClass() + "]")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenDifferentClass_whenIsNotType_thenTrue() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isNotType(otherClass()))
            .result(true)
            .name("isNotType[" + otherClass() + "]")
            .expected("not[" + otherClass() + "]")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenNullClass_whenIsNotType_thenFalse() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isNotType(null))
            .result(false)
            .name("isNotType[(null)]")
            .expected("Always fail: cannot compare to (null) type")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenMatchingClass_whenIsInstanceOf_thenTrue() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isInstanceOf(v.getClass()))
            .result(true)
            .name("isInstanceOf[" + v.getClass() + "]")
            .expected("instanceof[" + v.getClass() + "]")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenObjectClass_whenIsInstanceOf_thenTrue() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isInstanceOf(Object.class))
            .result(true)
            .name("isInstanceOf[" + Object.class + "]")
            .expected("instanceof[" + Object.class + "]")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenDifferentClass_whenIsInstanceOf_thenFalse() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isInstanceOf(otherClass()))
            .result(false)
            .name("isInstanceOf[" + otherClass() + "]")
            .expected("instanceof[" + otherClass() + "]")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenNullClass_whenIsInstanceOf_thenFalse() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isInstanceOf(null))
            .result(false)
            .name("isInstanceOf[(null)]")
            .expected("Always fail: cannot compare to (null) type")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenMatchingClass_whenIsNotInstanceOf_thenFalse() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isNotInstanceOf(v.getClass()))
            .result(false)
            .name("isNotInstanceOf[" + v.getClass() + "]")
            .expected("not[instanceof[" + v.getClass() + "]]")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenObjectClass_whenIsNotInstanceOf_thenFalse() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isNotInstanceOf(Object.class))
            .result(false)
            .name("isNotInstanceOf[" + Object.class + "]")
            .expected("not[instanceof[" + Object.class + "]]")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenDifferentClass_whenIsNotInstanceOf_thenTrue() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isNotInstanceOf(otherClass()))
            .result(true)
            .name("isNotInstanceOf[" + otherClass() + "]")
            .expected("not[instanceof[" + otherClass() + "]]")
            .actual(v.getClass().toString())
            .run();
    }

    @Test
    void givenNullClass_whenIsNotInstanceOf_thenFalse() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isNotInstanceOf(null))
            .result(false)
            .name("isNotInstanceOf[(null)]")
            .expected("Always fail: cannot compare to (null) type")
            .actual(v.getClass().toString())
            .run();
    }

    // ---- exactly / equal ----

    @Test
    void givenSameValue_whenIsExactly_thenTrue() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isExactly(v))
            .result(true)
            .name("isExactly[" + v + "]")
            .expected(v.toString())
            .actual(v.toString())
            .run();
    }

    @Test
    void givenDifferentValue_whenIsExactly_thenFalse() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isExactly(a))
            .result(false)
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
            .result(true)
            .name("isExactly[(null)]")
            .expected("(null)")
            .actual("(null)")
            .run();
    }

    @Test
    void givenSameValue_whenIsNotExactly_thenFalse() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isNotExactly(v))
            .result(false)
            .name("isNotExactly[" + v + "]")
            .expected("not[" + v + "]")
            .actual(v.toString())
            .run();
    }

    @Test
    void givenDifferentValue_whenIsNotExactly_thenTrue() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isNotExactly(a))
            .result(true)
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
            .result(false)
            .name("isNotExactly[(null)]")
            .expected("not[(null)]")
            .actual("(null)")
            .run();
    }

    @Test
    void givenSameValue_whenIsEqualTo_thenTrue() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.isEqualTo(v))
            .result(true)
            .name("isEqualTo[" + v + "]")
            .expected(v.toString())
            .actual(v.toString())
            .run();
    }

    @Test
    void givenDifferentValue_whenIsEqualTo_thenFalse() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isEqualTo(a))
            .result(false)
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
            .result(true)
            .name("isEqualTo[(null)]")
            .expected("(null)")
            .actual("(null)")
            .run();
    }

    // ---- condition ----

    @Test
    void givenNullCondition_whenIs_thenFalse() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.is(null))
            .result(false)
            .name("is[condition: (null)]")
            .expected(ALWAYS_FAIL_CANNOT_CHECK_TO_NULL_CONDITION)
            .actual(v.toString())
            .hint(CANNOT_CHECK_USING_NULL_CONDITION)
            .run();
    }

    @Test
    void givenTautology_whenIs_thenTrue() {
        final T v = nonNullValueA();
        final Condition<T> cond = Condition.tautology();
        newTestCase()
            .subject(v)
            .expression(value -> value.is(cond))
            .result(true)
            .name(cond.bind(v).name())
            .expected("true")
            .actual("true")
            .run();
    }

    @Test
    void givenContradiction_whenIs_thenFalse() {
        final T v = nonNullValueA();
        final Condition<T> cond = Condition.contradiction();
        newTestCase()
            .subject(v)
            .expression(value -> value.is(cond))
            .result(false)
            .name(cond.bind(v).name())
            .expected("false")
            .actual("false")
            .run();
    }

    // ---- hashCode ----

    @Test
    void givenMatchingHash_whenHashCodeIs_thenTrue() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.hashCodeIs(v.hashCode()))
            .result(true)
            .name("hashCode == " + v.hashCode())
            .expected(String.valueOf(v.hashCode()))
            .actual(String.valueOf(v.hashCode()))
            .run();
    }

    @Test
    void givenDifferentHash_whenHashCodeIs_thenFalse() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.hashCodeIs(a.hashCode()))
            .result(false)
            .name("hashCode == " + a.hashCode())
            .expected(String.valueOf(a.hashCode()))
            .actual(String.valueOf(b.hashCode()))
            .run();
    }

    @Test
    void givenMatchingHash_whenHashCodeIsNot_thenFalse() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.hashCodeIsNot(v.hashCode()))
            .result(false)
            .name("hashCode != " + v.hashCode())
            .expected("not[" + v.hashCode() + "]")
            .actual(String.valueOf(v.hashCode()))
            .run();
    }

    @Test
    void givenDifferentHash_whenHashCodeIsNot_thenTrue() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.hashCodeIsNot(a.hashCode()))
            .result(true)
            .name("hashCode != " + a.hashCode())
            .expected("not[" + a.hashCode() + "]")
            .actual(String.valueOf(b.hashCode()))
            .run();
    }

    // ---- toString ----

    @Test
    void givenMatchingToString_whenToStringIs_thenTrue() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.toStringIs(v.toString()))
            .result(true)
            .name("toString().equals(" + v + ")")
            .expected(v.toString())
            .actual(v.toString())
            .run();
    }

    @Test
    void givenDifferentToString_whenToStringIs_thenFalse() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.toStringIs(a.toString()))
            .result(false)
            .name("toString().equals(" + a + ")")
            .expected(a.toString())
            .actual(b.toString())
            .run();
    }

    @Test
    void givenMatchingToString_whenToStringIsNot_thenFalse() {
        final T v = nonNullValueA();
        newTestCase()
            .subject(v)
            .expression(value -> value.toStringIsNot(v.toString()))
            .result(false)
            .name("not[toString().equals(" + v + ")]")
            .expected("not[" + v + "]")
            .actual(v.toString())
            .run();
    }

    @Test
    void givenDifferentToString_whenToStringIsNot_thenTrue() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.toStringIsNot(a.toString()))
            .result(true)
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
            .result(true)
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
            .result(false)
            .name("predicate")
            .expected("Predicate satisfied")
            .actual("Predicate unsatisfied")
            .run();
    }

    // ---- comparator ----

    @Test
    void givenNullComparator_whenIsEqualTo_thenFalse() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isEqualTo(a, null))
            .result(false)
            .name("isEqualTo[" + a + ", comparator: (null)]")
            .expected(a.toString())
            .actual(b.toString())
            .hint(CANNOT_COMPARE_USING_NULL_COMPARATOR)
            .run();
    }

    @Test
    void givenEqualComparator_whenIsEqualTo_thenTrue() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isEqualTo(a, equalComparator))
            .result(true)
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
        .result(false)
        .name("isNotEqualTo[" + v + "]")
        .expected("not[" + v + "]")
        .actual(v.toString())
        .run();
    }

    @Test
    void givenDifferent_whenIsNotEqualTo_thenTrue() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isNotEqualTo(a))
            .result(true)
            .name("isNotEqualTo[" + a + "]")
            .expected("not[" + a + "]")
            .actual(b.toString())
            .run();
    }

    // ---- comparator not-equal ----

    @Test
    void givenNullComparator_whenIsNotEqualTo_thenFalse() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isNotEqualTo(a, null))
            .result(false)
            .name("isNotEqualTo[" + a + ", comparator: (null)]")
            .expected(a.toString())
            .actual(b.toString())
            .hint(CANNOT_COMPARE_USING_NULL_COMPARATOR)
            .run();
    }

    @Test
    void givenEqualComparator_whenIsNotEqualTo_thenFalse() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isNotEqualTo(a, equalComparator))
            .result(false)
            .name("isNotEqualTo[" + a + ", comparator: " + equalComparator + "]")
            .expected(a.toString())
            .actual(b.toString())
            .run();
    }

    @Test
    void givenLessThanComparator_whenIsNotEqualTo_thenTrue() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isNotEqualTo(a, lessThanComparator))
            .result(true)
            .name("isNotEqualTo[" + a + ", comparator: " + lessThanComparator + "]")
            .expected(a.toString())
            .actual(b.toString())
            .run();
    }

    @Test
    void givenGreaterThanComparator_whenIsNotEqualTo_thenTrue() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isNotEqualTo(a, greaterThanComparator))
            .result(true)
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
            .result(false)
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
            .result(true)
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
            .result(false)
            .name("isNotEqualTo[(null)]")
            .expected("not[(null)]")
            .actual("(null)")
            .run();
    }

    @Test
    void givenLessThanComparator_whenIsEqualTo_thenFalse() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isEqualTo(a, lessThanComparator))
            .result(false)
            .name("isEqualTo[" + a + ", comparator: " + lessThanComparator + "]")
            .expected(a.toString())
            .actual(b.toString())
            .run();
    }

    @Test
    void givenGreaterThanComparator_whenIsEqualTo_thenFalse() {
        final T a = nonNullValueA();
        final T b = nonNullValueB();
        newTestCase()
            .subject(b)
            .expression(value -> value.isEqualTo(a, greaterThanComparator))
            .result(false)
            .name("isEqualTo[" + a + ", comparator: " + greaterThanComparator + "]")
            .expected(a.toString())
            .actual(b.toString())
            .run();
    }
}
